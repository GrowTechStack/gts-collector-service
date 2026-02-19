package com.jun0x2dev.devfeed.domain.content.controller;

import com.jun0x2dev.devfeed.domain.content.dto.ContentResponse;
import com.jun0x2dev.devfeed.domain.content.dto.ContentSummaryResponse;
import com.jun0x2dev.devfeed.domain.content.service.ContentService;
import com.jun0x2dev.devfeed.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 콘텐츠 관련 API 요청을 처리하는 컨트롤러
 */
@Tag(name = "Content API", description = "콘텐츠 조회 및 관리 API")
@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

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
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResult.success(contentService.getContents(tag, pageable));
    }

    @Operation(summary = "콘텐츠 상세 조회", description = "특정 콘텐츠의 상세 내용을 조회하고 조회수를 증가시킵니다.")
    @GetMapping("/{id}")
    public ApiResult<ContentResponse> getContent(
            @Parameter(description = "콘텐츠 ID", example = "1") @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        Cookie[] cookies = request.getCookies();
        String cookieName = "viewed_contents_api";
        String cookieValue = "";
        boolean isAlreadyViewed = false;

        if (cookies != null) {
            Cookie viewCookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(cookieName))
                    .findFirst()
                    .orElse(null);

            if (viewCookie != null) {
                cookieValue = viewCookie.getValue();
                if (cookieValue.contains("[" + id + "]")) {
                    isAlreadyViewed = true;
                }
            }
        }

        if (!isAlreadyViewed) {
            contentService.incrementViewCount(id);
            Cookie newCookie = new Cookie(cookieName, cookieValue + "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }

        return ApiResult.success(contentService.getContent(id));
    }
}
