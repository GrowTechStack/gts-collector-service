package com.gts.collector.domain.content.controller;

import com.gts.collector.domain.content.dto.ContentResponse;
import com.gts.collector.domain.content.dto.ContentSummaryResponse;
import com.gts.collector.domain.content.service.ContentService;
import com.gts.collector.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 콘텐츠 관련 API 요청을 처리하는 컨트롤러
 */
@Tag(name = "Content API", description = "콘텐츠 조회 및 관리 API")
@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    /**
     * 기술 블로그 및 사용자 콘텐츠 목록을 최신순으로 조회합니다. 태그로 필터링할 수 있습니다.
     */
    @Operation(summary = "콘텐츠 목록 조회", description = "기술 블로그 및 사용자 콘텐츠 목록을 최신순으로 조회합니다.")
    @Parameters({
            @Parameter(name = "tag", description = "필터링할 태그 (null이면 전체)", in = ParameterIn.QUERY),
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "한 페이지당 개수", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
            @Parameter(name = "sort", description = "정렬 방식 (필드명,ASC|DESC)", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "publishedAt,desc"))
    })
    @GetMapping
    public ApiResult<Page<ContentSummaryResponse>> getContents(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) java.util.List<String> sites,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResult.success(contentService.getContentsBySites(tag, sites, pageable));
    }

    /**
     * 제목 및 요약 내용을 기준으로 키워드 검색합니다.
     * TODO: MySQL 전환 후 FULLTEXT INDEX + MATCH AGAINST 방식으로 교체 권장
     */
    @Operation(summary = "콘텐츠 검색", description = "제목 및 요약 내용을 기준으로 키워드 검색합니다.")
    @Parameters({
            @Parameter(name = "q", description = "검색 키워드", in = ParameterIn.QUERY),
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "한 페이지당 개수", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    @GetMapping("/search")
    public ApiResult<Page<ContentSummaryResponse>> searchContents(
            @RequestParam String q,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResult.success(contentService.searchContents(q, pageable));
    }

    /**
     * 특정 콘텐츠의 상세 내용을 조회합니다.
     * 쿠키를 통해 중복 조회를 방지하며, 최초 조회 시에만 조회수를 증가시킵니다.
     */
    @Operation(summary = "콘텐츠 상세 조회", description = "특정 콘텐츠의 상세 내용을 조회하고 조회수를 증가시킵니다.")
    @GetMapping("/{id}")
    public ApiResult<ContentResponse> getContent(
            @Parameter(description = "콘텐츠 ID", example = "1") @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response) {
        contentService.incrementViewCountIfNotViewed(id, "viewed_contents_api", request, response);
        return ApiResult.success(contentService.getContent(id));
    }
}
