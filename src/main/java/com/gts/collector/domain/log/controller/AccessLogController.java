package com.gts.collector.domain.log.controller;

import com.gts.collector.domain.log.dto.AccessStatsResponse;
import com.gts.collector.domain.log.service.AccessLogService;
import com.gts.collector.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AccessLog", description = "접속 통계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/access-logs")
public class AccessLogController {

    private final AccessLogService accessLogService;

    @Operation(summary = "접속 통계 조회", description = "현재 접속자, 오늘/이번달 방문자(UV), 전체 페이지뷰(PV)를 반환합니다.")
    @GetMapping("/stats")
    public ApiResult<AccessStatsResponse> getStats() {
        return ApiResult.success(accessLogService.getStats());
    }
}
