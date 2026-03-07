package com.gts.collector.domain.log.service.impl;

import com.gts.collector.domain.log.dto.AccessStatsResponse;
import com.gts.collector.domain.log.entity.AccessLog;
import com.gts.collector.domain.log.repository.AccessLogRepository;
import com.gts.collector.domain.log.service.AccessLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;

    @Async
    @Override
    @Transactional
    public void record(String hashedIp, String path) {
        accessLogRepository.save(AccessLog.builder()
                .hashedIp(hashedIp)
                .path(path)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public AccessStatsResponse getStats() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = now.minusMinutes(5);
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime monthStart = YearMonth.now().atDay(1).atStartOfDay();

        long activeNow = accessLogRepository.countDistinctIpSince(fiveMinutesAgo);
        long todayUv = accessLogRepository.countDistinctIpSince(todayStart);
        long monthUv = accessLogRepository.countDistinctIpSince(monthStart);

        return new AccessStatsResponse(activeNow, todayUv, monthUv);
    }

    @Override
    @Transactional
    public void deleteOldLogs() {
        accessLogRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(30));
        log.info("[AccessLog] 30일 이상 오래된 접속 로그 삭제 완료");
    }
}
