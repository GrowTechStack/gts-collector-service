package com.gts.collector.domain.log.dto;

import com.gts.collector.domain.log.entity.AccessLog;

import java.time.LocalDateTime;

public record AccessLogResponse(
        String hashedIp,
        String rawIp,
        String path,
        LocalDateTime createdAt
) {
    public static AccessLogResponse from(AccessLog log) {
        return new AccessLogResponse(log.getHashedIp(), log.getRawIp(), log.getPath(), log.getCreatedAt());
    }
}
