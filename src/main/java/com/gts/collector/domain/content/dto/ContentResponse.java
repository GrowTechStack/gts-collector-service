package com.gts.collector.domain.content.dto;

import com.gts.collector.domain.content.entity.Content;
import java.time.LocalDateTime;

/**
 * 콘텐츠 상세 정보 응답 DTO
 */
public record ContentResponse(
    Long id,
    String type,
    String title,
    String summary,
    String body,
    String originalUrl,
    String siteName,
    String tags,
    int viewCount,
    LocalDateTime publishedAt
) {
    public static ContentResponse from(Content content) {
        return new ContentResponse(
            content.getId(),
            content.getType().name(),
            content.getTitle(),
            content.getSummary(),
            content.getBody(),
            content.getOriginalUrl(),
            content.getSiteName(),
            content.getTags(),
            content.getViewCount(),
            content.getPublishedAt()
        );
    }
}
