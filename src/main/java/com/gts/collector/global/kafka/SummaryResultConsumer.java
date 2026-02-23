package com.gts.collector.global.kafka;

import com.gts.collector.domain.content.service.ContentService;
import com.gts.collector.global.kafka.dto.SummaryResultMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SummaryResultConsumer {

    private final ContentService contentService;

    @KafkaListener(topics = "content-summary-result", groupId = "devfeed-summary-result")
    public void consume(SummaryResultMessage message) {
        log.debug("[SummaryResultConsumer] 요약 결과 수신: contentId={}", message.contentId());
        try {
            contentService.updateSummary(message.contentId(), message.summary());
            log.debug("[SummaryResultConsumer] 요약 업데이트 완료: contentId={}", message.contentId());
        } catch (Exception e) {
            log.error("[SummaryResultConsumer] 요약 업데이트 실패: contentId={}, 원인={}", message.contentId(), e.getMessage());
        }
    }
}
