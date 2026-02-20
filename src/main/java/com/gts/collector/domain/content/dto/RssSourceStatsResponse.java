package com.gts.collector.domain.content.dto;

import java.time.LocalDateTime;

/**
 * RSS 소스별 통계 정보 응답 DTO
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
