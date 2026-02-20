package com.gts.collector.domain.content.dto;

import java.time.LocalDateTime;

/**
 * 사이트별 콘텐츠 통계 내부 전달 객체.
 * ContentService.findAllSiteStatsMap()에서 N+1 방지용으로 사용됩니다.
 */
public record SiteStatsDto(
    long postCount,
    long totalViewCount,
    LocalDateTime latestPublishedAt
) {}
