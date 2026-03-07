package com.gts.collector.domain.log.service;

import com.gts.collector.domain.log.dto.AccessStatsResponse;

public interface AccessLogService {

    void record(String hashedIp, String path);

    AccessStatsResponse getStats();

    void deleteOldLogs();
}
