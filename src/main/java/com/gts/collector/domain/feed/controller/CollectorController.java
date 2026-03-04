package com.gts.collector.domain.feed.controller;

import com.gts.collector.domain.feed.service.CollectorService;
import com.gts.collector.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 콘텐츠 수집 제어를 위한 API 컨트롤러
 */
@Tag(name = "Collector", description = "콘텐츠 수집 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collector")
public class CollectorController {

    private final CollectorService collectorService;

    /**
     * 모든 활성화된 RSS 출처로부터 콘텐츠를 즉시 수집합니다.
     * full=true 시 전체 수집, 기본값은 최근 2일치만 수집합니다.
     */
    @Operation(summary = "전체 수집 실행", description = "등록된 모든 활성 RSS 피드에서 콘텐츠를 수집합니다. full=true 시 전체 수집, 기본값은 최근 2일치만 수집합니다.")
    @PostMapping("/collect")
    public ApiResult<Integer> collectAll(
            @Parameter(description = "true면 전체 수집, false(기본값)면 최근 2일치만 수집")
            @RequestParam(defaultValue = "false") boolean full) {
        return ApiResult.success(collectorService.collectAll(full));
    }

    /**
     * 특정 RSS 출처 하나로부터 콘텐츠를 즉시 수집합니다.
     */
    @Operation(summary = "단건 수집 실행", description = "특정 RSS 출처 ID로부터 콘텐츠를 즉시 수집합니다.")
    @PostMapping("/collect/{sourceId}")
    public ApiResult<Integer> collectOne(@PathVariable Long sourceId) {
        return ApiResult.success(collectorService.collectOne(sourceId));
    }
}
