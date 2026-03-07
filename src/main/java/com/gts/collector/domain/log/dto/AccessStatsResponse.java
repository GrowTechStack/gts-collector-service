package com.gts.collector.domain.log.dto;

public record AccessStatsResponse(
        long activeNow,
        long todayUv,
        long monthUv
) {}
