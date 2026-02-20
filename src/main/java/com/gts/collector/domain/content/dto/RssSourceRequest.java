package com.gts.collector.domain.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * RSS 출처 등록/수정 요청 DTO
 */
@Schema(description = "RSS 출처 관리 요청")
public record RssSourceRequest(
    @NotBlank(message = "사이트명은 필수입니다.")
    @Schema(description = "사이트 이름", example = "토스 테크")
    String siteName,

    @NotBlank(message = "RSS URL은 필수입니다.")
    @Schema(description = "RSS 피드 URL", example = "https://toss.tech/rss.xml")
    String rssUrl,

    @Schema(description = "활성화 여부", defaultValue = "true")
    boolean active
) {}
