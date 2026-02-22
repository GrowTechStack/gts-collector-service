package com.gts.collector.domain.content.controller;

import com.gts.collector.domain.content.service.ContentService;
import com.gts.collector.domain.feed.service.RssSourceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Thymeleaf 뷰 렌더링을 담당하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class ContentViewController {

    private final ContentService contentService;
    private final RssSourceService rssSourceService;

    /**
     * 메인 피드 페이지.
     * 콘텐츠 목록, 현재 태그 필터, 검색어, 사이트별 로고 맵, RSS 출처 통계를 모델에 담아 반환합니다.
     */
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false) String tag,
                        @RequestParam(required = false) String q,
                        @PageableDefault(size = 12, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("contents", contentService.getContents(tag, pageable));
        model.addAttribute("currentTag", tag);
        model.addAttribute("q", q);

        // 사이트별 로고 URL 맵 (siteName → logoUrl)
        java.util.Map<String, String> siteLogos = rssSourceService.getAllSources().stream()
                .filter(s -> s.logoUrl() != null)
                .collect(java.util.stream.Collectors.toMap(
                        com.gts.collector.domain.feed.dto.RssSourceResponse::siteName,
                        com.gts.collector.domain.feed.dto.RssSourceResponse::logoUrl,
                        (existing, replacement) -> existing
                ));
        model.addAttribute("siteLogos", siteLogos);

        // 출처별 통계 (총 조회수 기준 내림차순)
        model.addAttribute("rssSources", rssSourceService.getAllSourcesWithStats());

        return "index";
    }

    /**
     * 관리자 페이지 (RSS 출처 관리)
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("sources", rssSourceService.getAllSources());
        return "admin";
    }

    /**
     * 콘텐츠 상세 요약 페이지.
     * 쿠키를 사용하여 동일한 브라우저에서 24시간 내 중복 조회수 증가를 방지합니다.
     */
    @GetMapping("/contents/{id}")
    public String contentSummary(@PathVariable Long id,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model) {
        contentService.incrementViewCountIfNotViewed(id, "viewed_contents", request, response);
        model.addAttribute("content", contentService.getContent(id));

        java.util.Map<String, String> siteLogos = rssSourceService.getAllSources().stream()
                .filter(s -> s.logoUrl() != null)
                .collect(java.util.stream.Collectors.toMap(
                        com.gts.collector.domain.feed.dto.RssSourceResponse::siteName,
                        com.gts.collector.domain.feed.dto.RssSourceResponse::logoUrl,
                        (existing, replacement) -> existing
                ));
        model.addAttribute("siteLogos", siteLogos);

        return "summary";
    }
}
