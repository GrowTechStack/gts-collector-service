package com.gts.collector.domain.feed.dto;

import com.gts.collector.domain.feed.entity.RssSource;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * RSS 출처 정보 응답 DTO
 */
@Schema(description = "RSS 출처 정보 응답")
public record RssSourceResponse(
    @Schema(description = "ID") Long id,
    @Schema(description = "사이트 이름") String siteName,
    @Schema(description = "RSS URL") String rssUrl,
    @Schema(description = "사이트 로고 URL") String logoUrl,
    @Schema(description = "사이트 접속 URL") String siteUrl,
    @Schema(description = "활성화 여부") boolean active
) {
    public static RssSourceResponse from(RssSource source) {
        return new RssSourceResponse(
            source.getId(),
            source.getSiteName(),
            source.getRssUrl(),
            source.getLogoUrl(),
            source.getSiteUrl(),
            source.isActive()
        );
    }
}
