package com.gts.collector.domain.feed.service;

import com.gts.collector.domain.feed.dto.RssSourceRequest;
import com.gts.collector.domain.feed.dto.RssSourceResponse;
import com.gts.collector.domain.feed.dto.RssSourceStatsResponse;

import java.util.List;

/**
 * RSS 출처 관리 서비스 인터페이스
 */
public interface RssSourceService {

    /** 전체 RSS 출처 목록을 조회합니다. */
    List<RssSourceResponse> getAllSources();

    /** 출처별 콘텐츠 수, 조회수, 최신 발행일 통계를 포함한 목록을 조회합니다. */
    List<RssSourceStatsResponse> getAllSourcesWithStats();

    /** 특정 RSS 출처를 조회합니다. */
    RssSourceResponse getSource(Long id);

    /** 새로운 RSS 출처를 등록합니다. */
    RssSourceResponse createSource(RssSourceRequest request);

    /** 기존 RSS 출처 정보를 수정합니다. */
    RssSourceResponse updateSource(Long id, RssSourceRequest request);

    /** RSS 출처를 삭제합니다. */
    void deleteSource(Long id);
}
