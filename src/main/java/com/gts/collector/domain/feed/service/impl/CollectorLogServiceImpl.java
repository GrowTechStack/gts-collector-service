package com.gts.collector.domain.feed.service.impl;

import com.gts.collector.domain.feed.dto.CollectorLogResponse;
import com.gts.collector.domain.feed.repository.CollectorLogRepository;
import com.gts.collector.domain.feed.service.CollectorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 수집 로그 조회 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectorLogServiceImpl implements CollectorLogService {

    private static final int MAX_LOG_SIZE = 50;

    private final CollectorLogRepository collectorLogRepository;

    /** 최신 수집 로그 목록을 최대 50건 조회합니다. */
    @Override
    public List<CollectorLogResponse> getRecentLogs() {
        return collectorLogRepository
                .findAllByOrderByStartTimeDesc(PageRequest.of(0, MAX_LOG_SIZE))
                .stream()
                .map(CollectorLogResponse::from)
                .toList();
    }

    /** 실패한 수집 로그 목록을 최대 50건 조회합니다. */
    @Override
    public List<CollectorLogResponse> getFailedLogs() {
        return collectorLogRepository
                .findAllBySuccessFalseOrderByStartTimeDesc(PageRequest.of(0, MAX_LOG_SIZE))
                .stream()
                .map(CollectorLogResponse::from)
                .toList();
    }
}
