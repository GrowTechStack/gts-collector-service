package com.gts.collector.domain.log.service;

import com.gts.collector.domain.log.dto.AccessLogResponse;
import com.gts.collector.domain.log.dto.AccessStatsResponse;

import java.util.List;

public interface AccessLogService {

    void record(String hashedIp, String rawIp, String path);

    AccessStatsResponse getStats();

    List<AccessLogResponse> getRecentLogs();

    void deleteOldLogs();
}
