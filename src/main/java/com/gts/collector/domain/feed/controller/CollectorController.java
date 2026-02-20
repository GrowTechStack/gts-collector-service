package com.gts.collector.domain.feed.controller;

import com.gts.collector.domain.feed.service.CollectorService;
import com.gts.collector.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
     */
    @Operation(summary = "즉시 수집 실행", description = "등록된 모든 활성 RSS 피드에서 새로운 콘텐츠를 수집합니다.")
    @PostMapping("/collect")
    public ApiResult<Integer> collect() {
        int collectedCount = collectorService.collectAll();
        return ApiResult.success(collectedCount);
    }
}
