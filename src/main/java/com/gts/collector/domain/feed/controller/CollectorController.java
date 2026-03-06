package com.gts.collector.domain.feed.controller;

import com.gts.collector.domain.feed.service.CollectorService;
import com.gts.collector.global.common.response.ApiResult;
import com.gts.collector.global.component.CollectionStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 콘텐츠 수집 제어를 위한 API 컨트롤러
 */
@Tag(name = "Collector", description = "콘텐츠 수집 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collector")
public class CollectorController {

    private final CollectorService collectorService;
    private final CollectionStatus collectionStatus;

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

    @Operation(summary = "수집 상태 조회", description = "현재 RSS 수집 활성화 여부를 반환합니다.")
    @GetMapping("/status")
    public ApiResult<Map<String, Boolean>> getStatus() {
        return ApiResult.success(Map.of("running", collectionStatus.isRunning()));
    }

    @Operation(summary = "수집 중지", description = "RSS 수집 스케줄러 및 수동 수집을 중지합니다.")
    @PostMapping("/stop")
    public ApiResult<Map<String, Boolean>> stop() {
        collectionStatus.stop();
        return ApiResult.success(Map.of("running", false));
    }

    @Operation(summary = "수집 시작", description = "중지된 RSS 수집을 재개합니다.")
    @PostMapping("/start")
    public ApiResult<Map<String, Boolean>> start() {
        collectionStatus.start();
        return ApiResult.success(Map.of("running", true));
    }

    @Operation(summary = "미요약 콘텐츠 재요약", description = "요약이 없는 기존 콘텐츠에 대해 AI 요약 요청을 재전송합니다.")
    @PostMapping("/resummary")
    public ApiResult<Integer> resummary() {
        return ApiResult.success(collectorService.resummary());
    }
}
