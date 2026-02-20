package com.gts.collector.domain.content.dto;

import com.gts.collector.domain.content.entity.Content;
import java.time.LocalDateTime;

/**
 * 콘텐츠 목록 조회를 위한 요약 정보 응답 DTO
 */
public record ContentSummaryResponse(
    Long id,
    String title,
    String summary,
    String siteName,
    String thumbnailUrl,
    String tags,
    int viewCount,
    LocalDateTime publishedAt
) {
    public static ContentSummaryResponse from(Content content) {
        return new ContentSummaryResponse(
            content.getId(),
            content.getTitle(),
            content.getSummary(),
            content.getSiteName(),
            content.getThumbnailUrl(),
            content.getTags(),
            content.getViewCount(),
            content.getPublishedAt()
        );
    }
}
