package com.gts.collector.domain.content.service;

import com.gts.collector.domain.content.dto.RssSourceRequest;
import com.gts.collector.domain.content.dto.RssSourceResponse;
import java.util.List;

/**
 * RSS 출처 관리 서비스 인터페이스
 */
public interface RssSourceService {
    List<RssSourceResponse> getAllSources();
    RssSourceResponse getSource(Long id);
    RssSourceResponse createSource(RssSourceRequest request);
    RssSourceResponse updateSource(Long id, RssSourceRequest request);
    void deleteSource(Long id);
}
