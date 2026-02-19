package com.jun0x2dev.devfeed.domain.content.service.impl;

import com.jun0x2dev.devfeed.domain.content.entity.CollectorLog;
import com.jun0x2dev.devfeed.domain.content.entity.RssSource;
import com.jun0x2dev.devfeed.domain.content.repository.CollectorLogRepository;
import com.jun0x2dev.devfeed.domain.content.repository.RssSourceRepository;
import com.jun0x2dev.devfeed.domain.content.service.CollectorService;
import com.jun0x2dev.devfeed.domain.content.service.RssCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 콘텐츠 수집 프로세스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectorServiceImpl implements CollectorService {

    private final RssCollectorService rssCollectorService;
    private final RssSourceRepository rssSourceRepository;
    private final CollectorLogRepository collectorLogRepository;

    @Override
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
                LocalDateTime endTime = LocalDateTime.now();
                saveLog(source, success, collectedCount, errorMessage, startTime, endTime);
            }
        }
        
        log.info("RSS collection completed. Total collected: {}", totalCollectedCount);
        return totalCollectedCount;
    }

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
