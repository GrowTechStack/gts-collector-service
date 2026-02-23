package com.gts.collector.global.kafka;

import com.gts.collector.global.kafka.dto.SummaryRequestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SummaryRequestProducer {

    static final String TOPIC = "content-summary-request";

    private final KafkaTemplate<String, SummaryRequestMessage> kafkaTemplate;

    public void send(Long contentId, String title, String content) {
        kafkaTemplate.send(TOPIC, String.valueOf(contentId), new SummaryRequestMessage(contentId, title, content));
        log.debug("[SummaryRequestProducer] 요약 요청 produce: contentId={}, title={}", contentId, title);
    }
}
