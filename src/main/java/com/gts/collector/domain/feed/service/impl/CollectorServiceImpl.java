package com.gts.collector.domain.feed.service.impl;

import com.gts.collector.domain.feed.entity.CollectorLog;
import com.gts.collector.domain.feed.entity.RssSource;
import com.gts.collector.domain.feed.repository.CollectorLogRepository;
import com.gts.collector.domain.feed.repository.RssSourceRepository;
import com.gts.collector.domain.feed.service.CollectorService;
import com.gts.collector.domain.feed.service.RssCollectorService;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 콘텐츠 수집 프로세스 구현체.
 * 활성화된 모든 RSS 출처를 순회하며 수집하고 결과를 로그로 기록합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectorServiceImpl implements CollectorService {

    private final RssCollectorService rssCollectorService;
    private final RssSourceRepository rssSourceRepository;
    private final CollectorLogRepository collectorLogRepository;

    /**
     * 활성화된 모든 RSS 출처에서 콘텐츠를 수집합니다.
     * 각 출처의 수집 결과(성공/실패, 수집 수, 시간)를 CollectorLog에 저장합니다.
     */
    @Override
    @Transactional
    public int collectAll(boolean full) {
        log.info("===== RSS 수집 시작 | 모드={} =====", full ? "전체" : "최근 2일");

        List<RssSource> activeSources = rssSourceRepository.findAllByActiveTrue();
        log.info("활성 RSS 출처 {}개 처리 예정", activeSources.size());

        int totalCollectedCount = 0;
        int successCount = 0;
        int failCount = 0;

        for (RssSource source : activeSources) {
            LocalDateTime startTime = LocalDateTime.now();
            int collectedCount = 0;
            boolean success = false;
            String errorMessage = null;

            try {
                LocalDateTime since = full ? null : LocalDateTime.now().minusDays(2);
                log.info("[수집 시작] 사이트={}, URL={}, since={}", source.getSiteName(), source.getRssUrl(), since);
                collectedCount = rssCollectorService.collect(source.getRssUrl(), source.getSiteName(), since);
                success = true;
                successCount++;
                totalCollectedCount += collectedCount;
                log.info("[수집 성공] 사이트={}, 수집건수={}", source.getSiteName(), collectedCount);
            } catch (BusinessException e) {
                failCount++;
                errorMessage = "[" + e.getErrorCode().getCode() + "] " + e.getErrorCode().getMessage();
                log.error("[수집 실패] 사이트={}, URL={}, 오류코드={}, 원인={}",
                        source.getSiteName(), source.getRssUrl(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());
            } catch (Exception e) {
                failCount++;
                errorMessage = e.getMessage();
                log.error("[수집 실패] 사이트={}, URL={}, 원인={}", source.getSiteName(), source.getRssUrl(), errorMessage, e);
            } finally {
                saveLog(source, success, collectedCount, errorMessage, startTime, LocalDateTime.now());
            }
        }

        log.info("===== RSS 수집 완료 | 전체={}, 성공={}, 실패={}, 총 수집건수={} =====",
                activeSources.size(), successCount, failCount, totalCollectedCount);
        return totalCollectedCount;
    }

    /**
     * 특정 RSS 출처 하나에서 콘텐츠를 수집합니다.
     */
    @Override
    @Transactional
    public int collectOne(Long sourceId) {
        RssSource source = rssSourceRepository.findById(sourceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RSS_SOURCE_NOT_FOUND));

        log.info("===== 단건 RSS 수집 시작 | 사이트={} =====", source.getSiteName());

        LocalDateTime startTime = LocalDateTime.now();
        int collectedCount = 0;
        boolean success = false;
        String errorMessage = null;

        try {
            log.info("[수집 시작] 사이트={}, URL={} (전체 수집)", source.getSiteName(), source.getRssUrl());
            collectedCount = rssCollectorService.collect(source.getRssUrl(), source.getSiteName(), null);
            success = true;
            log.info("[수집 성공] 사이트={}, 수집건수={}", source.getSiteName(), collectedCount);
        } catch (BusinessException e) {
            errorMessage = "[" + e.getErrorCode().getCode() + "] " + e.getErrorCode().getMessage();
            log.error("[수집 실패] 사이트={}, 오류코드={}, 원인={}",
                    source.getSiteName(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        } catch (Exception e) {
            errorMessage = e.getMessage();
            log.error("[수집 실패] 사이트={}, 원인={}", source.getSiteName(), errorMessage, e);
        } finally {
            saveLog(source, success, collectedCount, errorMessage, startTime, LocalDateTime.now());
        }

        return collectedCount;
    }

    /**
     * 수집 실행 결과를 CollectorLog 엔티티로 저장합니다.
     * errorMessage는 1000자로 잘라 저장합니다.
     */
    private void saveLog(RssSource source, boolean success, int collectedCount,
                         String errorMessage, LocalDateTime startTime, LocalDateTime endTime) {
        CollectorLog logEntity = CollectorLog.builder()
                .siteName(source.getSiteName())
                .rssUrl(source.getRssUrl())
                .success(success)
                .collectedCount(collectedCount)
                .errorMessage(errorMessage != null && errorMessage.length() > 1000 ?
                        errorMessage.substring(0, 1000) : errorMessage)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        collectorLogRepository.save(logEntity);
    }
}
