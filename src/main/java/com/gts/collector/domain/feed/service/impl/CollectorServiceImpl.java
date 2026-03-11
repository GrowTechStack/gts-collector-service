package com.gts.collector.domain.feed.service.impl;

import com.gts.collector.domain.content.entity.Content;
import com.gts.collector.domain.content.repository.ContentRepository;
import com.gts.collector.domain.feed.entity.CollectorLog;
import com.gts.collector.domain.feed.entity.RssSource;
import com.gts.collector.domain.feed.repository.CollectorLogRepository;
import com.gts.collector.domain.feed.repository.RssSourceRepository;
import com.gts.collector.domain.feed.service.CollectorService;
import com.gts.collector.domain.feed.service.RssCollectorService;
import com.gts.collector.global.component.CollectionStatus;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import com.gts.collector.global.kafka.SummaryRequestProducer;
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
    private final CollectionStatus collectionStatus;
    private final ContentRepository contentRepository;
    private final SummaryRequestProducer summaryRequestProducer;

    /**
     * 활성화된 모든 RSS 출처에서 콘텐츠를 수집합니다.
     * 각 출처의 수집 결과(성공/실패, 수집 수, 시간)를 CollectorLog에 저장합니다.
     */
    @Override
    @Transactional
    public int collectAll(boolean full) {
        if (!collectionStatus.isRunning()) {
            log.info("RSS 수집이 중지 상태입니다. collectAll 실행 생략.");
            return 0;
        }
        log.info("===== RSS 수집 시작 | 모드={} =====", full ? "전체" : "최근 2일");

        List<RssSource> activeSources = rssSourceRepository.findAllByActiveTrue();
        log.info("활성 RSS 출처 {}개 처리 예정", activeSources.size());

        int totalCollectedCount = 0;
        int successCount = 0;
        int failCount = 0;

        for (RssSource source : activeSources) {
            if (!collectionStatus.isRunning()) {
                log.info("수집 중지 요청 감지. 나머지 출처 수집을 중단합니다.");
                break;
            }

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
    public int collectOne(Long sourceId, boolean full) {
        if (!collectionStatus.isRunning()) {
            log.info("RSS 수집이 중지 상태입니다. collectOne 실행 생략.");
            return 0;
        }
        RssSource source = rssSourceRepository.findById(sourceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RSS_SOURCE_NOT_FOUND));

        log.info("===== 단건 RSS 수집 시작 | 사이트={}, 모드={} =====", source.getSiteName(), full ? "전체" : "최근 2일");

        LocalDateTime startTime = LocalDateTime.now();
        int collectedCount = 0;
        boolean success = false;
        String errorMessage = null;

        try {
            LocalDateTime since = full ? null : LocalDateTime.now().minusDays(2);
            log.info("[수집 시작] 사이트={}, URL={}, since={}", source.getSiteName(), source.getRssUrl(), since);
            collectedCount = rssCollectorService.collect(source.getRssUrl(), source.getSiteName(), since);
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
     * 요약이 없는 기존 콘텐츠에 대해 AI 요약 요청을 재전송합니다.
     */
    @Override
    @Transactional
    public int resummary() {
        List<Content> targets = contentRepository.findAllBySummaryIsNullAndBodyIsNotNull();
        log.info("===== 미요약 콘텐츠 재요약 시작 | 대상={}건 =====", targets.size());
        int count = 0;
        for (Content content : targets) {
            try {
                String body = content.getBody().length() > 3000
                        ? content.getBody().substring(0, 3000) : content.getBody();
                summaryRequestProducer.send(content.getId(), content.getTitle(), body);
                count++;
            } catch (Exception e) {
                log.error("[재요약 실패] contentId={}, 원인={}", content.getId(), e.getMessage());
            }
        }
        log.info("===== 미요약 콘텐츠 재요약 완료 | 요청={}건 =====", count);
        return count;
    }

    @Override
    @Transactional
    public int reCollectAllMissingBodies() {
        return rssCollectorService.reCollectAllMissingBodies();
    }

    @Override
    @Transactional
    public int reCollectMissingBodiesBySource(Long sourceId) {
        return rssCollectorService.reCollectMissingBodiesBySource(sourceId);
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
