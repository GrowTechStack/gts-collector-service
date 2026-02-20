package com.gts.collector.domain.feed.controller;

import com.gts.collector.domain.feed.dto.CollectorLogResponse;
import com.gts.collector.domain.feed.service.CollectorLogService;
import com.gts.collector.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 수집 로그 조회 API 컨트롤러
 */
@Tag(name = "Collector Log", description = "RSS 수집 로그 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collector/logs")
public class CollectorLogController {

    private final CollectorLogService collectorLogService;

    /**
     * 최신 수집 로그 목록을 조회합니다. (최대 50건)
     */
    @Operation(summary = "수집 로그 조회", description = "최신 수집 로그를 최대 50건 조회합니다.")
    @GetMapping
    public ApiResult<List<CollectorLogResponse>> getRecentLogs() {
        return ApiResult.success(collectorLogService.getRecentLogs());
    }

    /**
     * 실패한 수집 로그만 조회합니다. (최대 50건)
     */
    @Operation(summary = "실패 로그 조회", description = "실패한 수집 로그를 최대 50건 조회합니다.")
    @GetMapping("/failures")
    public ApiResult<List<CollectorLogResponse>> getFailedLogs() {
        return ApiResult.success(collectorLogService.getFailedLogs());
    }
}
