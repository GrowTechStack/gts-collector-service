package com.gts.collector.domain.log.dto;

import com.gts.collector.domain.log.entity.AccessLog;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public record AccessLogResponse(
        String hashedIp,
        String rawIp,
        String path,
        OffsetDateTime createdAt
) {
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    private static final ZoneId UTC = ZoneId.of("UTC");

    public static AccessLogResponse from(AccessLog log) {
        return new AccessLogResponse(
                log.getHashedIp(),
                log.getRawIp(),
                log.getPath(),
                log.getCreatedAt().atZone(UTC).withZoneSameInstant(KST).toOffsetDateTime()
        );
    }
}
