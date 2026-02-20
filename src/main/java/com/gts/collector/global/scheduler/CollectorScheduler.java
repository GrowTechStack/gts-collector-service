package com.gts.collector.global.scheduler;

import com.gts.collector.domain.content.service.CollectorService;
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

    /**
     * 매시간 정각마다 DB에 등록된 활성화된 모든 RSS 출처에서 수집 로직 실행
     */
    @Scheduled(cron = "0 0 * * * *")
    public void collectRss() {
        log.info("Starting scheduled RSS collection...");
        int collectedCount = collectorService.collectAll();
        log.info("Scheduled RSS collection completed. Total collected: {}", collectedCount);
    }
}
