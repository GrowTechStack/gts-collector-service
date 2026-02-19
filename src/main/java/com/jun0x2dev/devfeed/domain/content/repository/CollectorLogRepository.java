package com.jun0x2dev.devfeed.domain.content.repository;

import com.jun0x2dev.devfeed.domain.content.entity.CollectorLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 수집 로그 데이터 접근을 위한 리포지토리
 */
public interface CollectorLogRepository extends JpaRepository<CollectorLog, Long> {
}
