package com.gts.collector.global.scheduler;

import com.gts.collector.domain.feed.service.CollectorService;
import com.gts.collector.global.component.CollectionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 정기적인 외부 콘텐츠 수집을 담당하는 스케줄러
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CollectorScheduler {

    private final CollectorService collectorService;
    private final CollectionStatus collectionStatus;

    /**
     * 매일 자정(00:00)에 DB에 등록된 활성화된 모든 RSS 출처에서 최근 2일치 글 수집
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void collectRss() {
        if (!collectionStatus.isRunning()) {
            log.info("RSS collection is stopped. Skipping scheduled collection.");
            return;
        }
        log.info("Starting scheduled RSS collection...");
        int collectedCount = collectorService.collectAll(false);
        log.info("Scheduled RSS collection completed. Total collected: {}", collectedCount);
    }
}
