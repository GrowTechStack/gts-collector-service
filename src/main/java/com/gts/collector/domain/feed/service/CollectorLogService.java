package com.gts.collector.domain.feed.service;

import com.gts.collector.domain.feed.dto.CollectorLogResponse;
import java.util.List;

/**
 * 수집 로그 조회 서비스 인터페이스
 */
public interface CollectorLogService {

    /** 최신 수집 로그 목록을 조회합니다. (최대 50건) */
    List<CollectorLogResponse> getRecentLogs();

    /** 실패한 수집 로그 목록을 조회합니다. (최대 50건) */
    List<CollectorLogResponse> getFailedLogs();
}
