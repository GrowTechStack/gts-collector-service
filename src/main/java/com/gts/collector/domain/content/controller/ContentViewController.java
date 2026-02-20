package com.gts.collector.domain.content.controller;

import com.gts.collector.domain.content.service.ContentService;
import com.gts.collector.domain.content.service.RssSourceService;
import jakarta.servlet.http.Cookie;
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

import java.util.Arrays;

/**
 * Thymeleaf 뷰 렌더링을 담당하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class ContentViewController {

    private final ContentService contentService;
    private final RssSourceService rssSourceService;

    /**
     * 메인 피드 페이지
     */
    @GetMapping("/")
    public String index(Model model, 
                        @RequestParam(required = false) String tag,
                        @PageableDefault(size = 12, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("contents", contentService.getContents(tag, pageable));
        model.addAttribute("currentTag", tag);
        
        // 사이트별 로고 URL 맵 추가
        java.util.Map<String, String> siteLogos = rssSourceService.getAllSources().stream()
                .filter(s -> s.logoUrl() != null)
                .collect(java.util.stream.Collectors.toMap(
                        com.gts.collector.domain.content.dto.RssSourceResponse::siteName,
                        com.gts.collector.domain.content.dto.RssSourceResponse::logoUrl,
                        (existing, replacement) -> existing
                ));
        model.addAttribute("siteLogos", siteLogos);
        
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
     * 콘텐츠 상세 요약 페이지
     * 쿠키를 사용하여 동일한 브라우저 세션에서 중복 조회수 증가 방지
     */
    @GetMapping("/contents/{id}")
    public String contentSummary(@PathVariable Long id, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response, 
                                 Model model) {
        
        Cookie[] cookies = request.getCookies();
        boolean isAlreadyViewed = false;
        String cookieName = "viewed_contents";
        String cookieValue = "";

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

        model.addAttribute("content", contentService.getContent(id));
        return "summary";
    }
}
