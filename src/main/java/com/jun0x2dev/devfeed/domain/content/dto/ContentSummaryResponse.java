package com.jun0x2dev.devfeed.domain.content.dto;

import com.jun0x2dev.devfeed.domain.content.entity.Content;
import com.jun0x2dev.devfeed.domain.content.entity.ContentType;

import java.time.LocalDateTime;

/**
 * 콘텐츠 목록 조회를 위한 요약 응답 DTO
 */
public record ContentSummaryResponse(
    Long id,
    ContentType type,
    String title,
    String summary,
    String siteName,
    String tags,
    long viewCount,
    LocalDateTime publishedAt
) {
    public static ContentSummaryResponse from(Content content) {
        return new ContentSummaryResponse(
            content.getId(),
            content.getType(),
            content.getTitle(),
            content.getSummary(),
            content.getSiteName(),
            content.getTags(),
            content.getViewCount(),
            content.getPublishedAt()
        );
    }
}
