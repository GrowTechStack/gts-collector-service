package com.jun0x2dev.devfeed.domain.content.dto;

import com.jun0x2dev.devfeed.domain.content.entity.Content;
import com.jun0x2dev.devfeed.domain.content.entity.ContentType;

import java.time.LocalDateTime;

/**
 * 콘텐츠 상세 조회를 위한 상세 응답 DTO
 */
public record ContentResponse(
    Long id,
    ContentType type,
    String title,
    String body,
    String summary,
    String originalUrl,
    String siteName,
    String tags,
    long viewCount,
    boolean commentEnabled,
    LocalDateTime publishedAt
) {
    public static ContentResponse from(Content content) {
        return new ContentResponse(
            content.getId(),
            content.getType(),
            content.getTitle(),
            content.getBody(),
            content.getSummary(),
            content.getOriginalUrl(),
            content.getSiteName(),
            content.getTags(),
            content.getViewCount(),
            content.isCommentEnabled(),
            content.getPublishedAt()
        );
    }
}
