package com.jun0x2dev.devfeed.domain.content.service;

import com.jun0x2dev.devfeed.domain.content.dto.RssSourceRequest;
import com.jun0x2dev.devfeed.domain.content.dto.RssSourceResponse;

import java.util.List;

/**
 * RSS 출처 관리 서비스 인터페이스
 */
public interface RssSourceService {

    /**
     * 모든 RSS 출처 목록을 조회합니다.
     */
    List<RssSourceResponse> getAllSources();

    /**
     * 특정 RSS 출처를 조회합니다.
     */
    RssSourceResponse getSource(Long id);

    /**
     * 새로운 RSS 출처를 등록합니다.
     */
    RssSourceResponse createSource(RssSourceRequest request);

    /**
     * RSS 출처 정보를 수정합니다.
     */
    RssSourceResponse updateSource(Long id, RssSourceRequest request);

    /**
     * RSS 출처를 삭제합니다.
     */
    void deleteSource(Long id);
}
