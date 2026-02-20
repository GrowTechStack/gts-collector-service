package com.gts.collector.domain.content.service;

import com.gts.collector.domain.content.dto.ContentResponse;
import com.gts.collector.domain.content.dto.ContentSummaryResponse;
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
     * @return 콘텐츠 요약 정보 목록
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
     * 특정 콘텐츠의 조회수를 1 증가시킵니다.
     */
    void incrementViewCount(Long id);
}
