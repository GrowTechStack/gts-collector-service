package com.gts.collector.domain.content.service;

import com.gts.collector.domain.content.dto.ContentResponse;
import com.gts.collector.domain.content.dto.ContentSummaryResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 콘텐츠 비즈니스 로직을 담당하는 서비스 인터페이스
 */
public interface ContentService {

    /**
     * 콘텐츠 목록을 페이징하여 조회합니다. (태그 필터링 포함)
     * @param tag 필터링할 태그 (null이면 전체 조회)
     * @param pageable 페이징 및 정렬 정보
     */
    Page<ContentSummaryResponse> getContents(String tag, Pageable pageable);

    /**
     * 키워드로 콘텐츠를 검색합니다. (title, summary 대상)
     */
    Page<ContentSummaryResponse> searchContents(String keyword, Pageable pageable);

    /**
     * 특정 콘텐츠를 상세 조회합니다.
     */
    ContentResponse getContent(Long id);

    /**
     * 쿠키를 이용해 중복 조회를 방지하며 조회수를 증가시킵니다.
     * 동일 브라우저에서 24시간 내 재방문 시 조회수를 증가시키지 않습니다.
     * @param cookieName 조회 이력을 저장할 쿠키명
     */
    void incrementViewCountIfNotViewed(Long id, String cookieName,
                                       HttpServletRequest request, HttpServletResponse response);

    /**
     * 특정 사이트명의 총 콘텐츠 수를 반환합니다.
     */
    long countBySiteName(String siteName);

    /**
     * 특정 사이트명의 총 조회수 합계를 반환합니다.
     */
    long sumViewCountBySiteName(String siteName);

    /**
     * 특정 사이트명의 가장 최근 발행일을 반환합니다.
     */
    java.util.Optional<java.time.LocalDateTime> findLatestPublishedAtBySiteName(String siteName);
}
