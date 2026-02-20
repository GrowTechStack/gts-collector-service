package com.gts.collector.domain.feed.dto;

import com.gts.collector.domain.feed.entity.CollectorLog;
import java.time.LocalDateTime;

/**
 * 수집 로그 응답 DTO
 */
public record CollectorLogResponse(
    Long id,
    String siteName,
    String rssUrl,
    boolean success,
    int collectedCount,
    String errorMessage,
    LocalDateTime startTime,
    LocalDateTime endTime
) {
    public static CollectorLogResponse from(CollectorLog log) {
        return new CollectorLogResponse(
            log.getId(),
            log.getSiteName(),
            log.getRssUrl(),
            log.isSuccess(),
            log.getCollectedCount(),
            log.getErrorMessage(),
            log.getStartTime(),
            log.getEndTime()
        );
    }
}
