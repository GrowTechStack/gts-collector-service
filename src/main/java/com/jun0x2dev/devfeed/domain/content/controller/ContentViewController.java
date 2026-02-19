package com.jun0x2dev.devfeed.domain.content.controller;

import com.jun0x2dev.devfeed.domain.content.service.ContentService;
import com.jun0x2dev.devfeed.domain.content.service.RssSourceService;
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

    @GetMapping("/")
    public String index(Model model, 
                        @RequestParam(required = false) String tag,
                        @PageableDefault(size = 12, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("contents", contentService.getContents(tag, pageable));
        model.addAttribute("currentTag", tag);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("sources", rssSourceService.getAllSources());
        return "admin";
    }

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
