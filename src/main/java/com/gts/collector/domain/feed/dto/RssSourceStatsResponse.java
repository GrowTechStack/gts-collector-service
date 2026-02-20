package com.gts.collector.domain.feed.dto;

import java.time.LocalDateTime;

/**
 * RSS 소스별 통계 정보 응답 DTO (사이드바 표시용)
 */
public record RssSourceStatsResponse(
    Long id,
    String siteName,
    String rssUrl,
    String siteUrl,
    String logoUrl,
    boolean active,
    long postCount,
    long totalViewCount,
    LocalDateTime latestPublishedAt
) {}
