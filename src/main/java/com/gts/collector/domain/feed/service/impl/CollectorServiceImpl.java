package com.gts.collector.domain.feed.service.impl;

import com.gts.collector.domain.feed.entity.CollectorLog;
import com.gts.collector.domain.feed.entity.RssSource;
import com.gts.collector.domain.feed.repository.CollectorLogRepository;
import com.gts.collector.domain.feed.repository.RssSourceRepository;
import com.gts.collector.domain.feed.service.CollectorService;
import com.gts.collector.domain.feed.service.RssCollectorService;
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
    public int collectAll() {
        log.info("Starting manual/scheduled RSS collection...");

        List<RssSource> activeSources = rssSourceRepository.findAllByActiveTrue();
        log.info("Found {} active RSS sources to process.", activeSources.size());

        int totalCollectedCount = 0;

        for (RssSource source : activeSources) {
            LocalDateTime startTime = LocalDateTime.now();
            int collectedCount = 0;
            boolean success = false;
            String errorMessage = null;

            try {
                log.info("Collecting from site: {} ({})", source.getSiteName(), source.getRssUrl());
                collectedCount = rssCollectorService.collect(source.getRssUrl(), source.getSiteName());
                success = true;
                totalCollectedCount += collectedCount;
            } catch (Exception e) {
                log.error("Failed to collect from site: {}", source.getSiteName(), e);
                errorMessage = e.getMessage();
            } finally {
                saveLog(source, success, collectedCount, errorMessage, startTime, LocalDateTime.now());
            }
        }

        log.info("RSS collection completed. Total collected: {}", totalCollectedCount);
        return totalCollectedCount;
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
