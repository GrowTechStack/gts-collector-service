package com.gts.collector.domain.content.service.impl;

import com.gts.collector.domain.content.dto.ContentResponse;
import com.gts.collector.domain.content.dto.ContentSummaryResponse;
import com.gts.collector.domain.content.entity.Content;
import com.gts.collector.domain.content.repository.ContentRepository;
import com.gts.collector.domain.content.service.ContentService;
import com.gts.collector.global.error.ErrorCode;
import com.gts.collector.global.error.exception.BusinessException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gts.collector.domain.content.dto.SiteStatsDto;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 콘텐츠 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    @Override
    public Page<ContentSummaryResponse> getContents(String tag, Pageable pageable) {
        if (StringUtils.hasText(tag)) {
            return contentRepository.findAllByTagsContaining(tag, pageable)
                    .map(ContentSummaryResponse::from);
        }
        return contentRepository.findAll(pageable)
                .map(ContentSummaryResponse::from);
    }

    @Override
    public Page<ContentSummaryResponse> getContentsBySites(String tag, java.util.List<String> sites, Pageable pageable) {
        if (sites == null || sites.isEmpty()) {
            return getContents(tag, pageable);
        }
        if (StringUtils.hasText(tag)) {
            return contentRepository.findAllBySiteNameInAndTagsContaining(sites, tag, pageable)
                    .map(ContentSummaryResponse::from);
        }
        return contentRepository.findAllBySiteNameIn(sites, pageable)
                .map(ContentSummaryResponse::from);
    }

    @Override
    public Page<ContentSummaryResponse> searchContents(String keyword, Pageable pageable) {
        return contentRepository.searchByKeyword(keyword, pageable)
                .map(ContentSummaryResponse::from);
    }

    @Override
    public ContentResponse getContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        return ContentResponse.from(content);
    }

    /**
     * 쿠키에 저장된 조회 이력을 확인하여 중복 조회를 방지합니다.
     * 최초 조회 시 조회수를 증가시키고 쿠키에 해당 콘텐츠 ID를 기록합니다.
     * 쿠키 만료 기간은 24시간입니다.
     */
    @Override
    @Transactional
    public void incrementViewCountIfNotViewed(Long id, String cookieName,
                                              HttpServletRequest request, HttpServletResponse response) {
        String cookieValue = "";
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Cookie viewCookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(cookieName))
                    .findFirst()
                    .orElse(null);

            if (viewCookie != null) {
                cookieValue = viewCookie.getValue();
                if (cookieValue.contains("[" + id + "]")) {
                    return; // 이미 조회한 콘텐츠
                }
            }
        }

        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        content.incrementViewCount();

        Cookie newCookie = new Cookie(cookieName, cookieValue + "[" + id + "]");
        newCookie.setPath("/");
        newCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(newCookie);
    }

    @Override
    public long countBySiteName(String siteName) {
        return contentRepository.countBySiteName(siteName);
    }

    @Override
    public long sumViewCountBySiteName(String siteName) {
        return contentRepository.sumViewCountBySiteName(siteName);
    }

    @Override
    public Optional<LocalDateTime> findLatestPublishedAtBySiteName(String siteName) {
        return contentRepository.findLatestPublishedAtBySiteName(siteName);
    }

    @Override
    @Transactional
    public void updateSummary(Long id, String summary) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
        content.updateSummary(summary);
    }

    /**
     * 모든 사이트 통계를 단일 GROUP BY 쿼리로 조회하여 Map으로 변환합니다.
     * N+1 방지 목적으로 사용됩니다.
     */
    @Override
    public Map<String, SiteStatsDto> findAllSiteStatsMap() {
        Map<String, SiteStatsDto> statsMap = new HashMap<>();
        for (Object[] row : contentRepository.findAllSiteStats()) {
            String siteName = (String) row[0];
            long postCount = ((Number) row[1]).longValue();
            long totalViewCount = ((Number) row[2]).longValue();
            LocalDateTime latestPublishedAt = (LocalDateTime) row[3];
            statsMap.put(siteName, new SiteStatsDto(postCount, totalViewCount, latestPublishedAt));
        }
        return statsMap;
    }
}
