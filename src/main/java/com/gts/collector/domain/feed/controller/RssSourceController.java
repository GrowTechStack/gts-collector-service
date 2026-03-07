package com.gts.collector.domain.feed.controller;

import com.gts.collector.domain.feed.dto.RssSourceRequest;
import com.gts.collector.domain.feed.dto.RssSourceResponse;
import com.gts.collector.domain.feed.dto.RssSourceStatsResponse;
import com.gts.collector.domain.feed.service.RssSourceService;
import com.gts.collector.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RSS 출처 관리를 위한 API 컨트롤러
 */
@Tag(name = "RSS Source", description = "RSS 출처 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rss-sources")
public class RssSourceController {

    private final RssSourceService rssSourceService;

    @Operation(summary = "모든 RSS 출처 조회", description = "등록된 모든 RSS 출처 목록을 통계(포스트 수, 조회수, 최신 발행일) 포함하여 조회합니다.")
    @GetMapping
    public ApiResult<List<RssSourceStatsResponse>> getAllSources() {
        return ApiResult.success(rssSourceService.getAllSourcesWithStats());
    }

    @Operation(summary = "RSS 출처 상세 조회", description = "특정 ID의 RSS 출처 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResult<RssSourceResponse> getSource(@PathVariable Long id) {
        return ApiResult.success(rssSourceService.getSource(id));
    }

    @Operation(summary = "RSS 출처 등록", description = "새로운 RSS 출처를 등록합니다.")
    @PostMapping
    public ApiResult<RssSourceResponse> createSource(@Valid @RequestBody RssSourceRequest request) {
        return ApiResult.success(rssSourceService.createSource(request));
    }

    @Operation(summary = "RSS 출처 수정", description = "기존 RSS 출처 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ApiResult<RssSourceResponse> updateSource(@PathVariable Long id, @Valid @RequestBody RssSourceRequest request) {
        return ApiResult.success(rssSourceService.updateSource(id, request));
    }

    @Operation(summary = "RSS 출처 삭제", description = "특정 RSS 출처를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteSource(@PathVariable Long id) {
        rssSourceService.deleteSource(id);
        return ApiResult.success(null);
    }
}
