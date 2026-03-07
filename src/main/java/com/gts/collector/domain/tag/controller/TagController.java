package com.gts.collector.domain.tag.controller;

import com.gts.collector.domain.tag.service.TagService;
import com.gts.collector.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Tags", description = "태그 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "태그 목록 조회", description = "콘텐츠 필터링에 사용 가능한 태그 목록을 반환합니다.")
    @GetMapping
    public ApiResult<List<String>> getTags() {
        return ApiResult.success(tagService.getAllTagNames());
    }
}
