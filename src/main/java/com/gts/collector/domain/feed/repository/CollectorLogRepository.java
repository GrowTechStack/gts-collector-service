package com.gts.collector.domain.feed.repository;

import com.gts.collector.domain.feed.entity.CollectorLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 수집 로그 데이터 접근을 위한 리포지토리
 */
public interface CollectorLogRepository extends JpaRepository<CollectorLog, Long> {

    /** 최신순으로 수집 로그를 조회합니다. */
    List<CollectorLog> findAllByOrderByStartTimeDesc(Pageable pageable);

    /** 실패한 수집 로그만 최신순으로 조회합니다. */
    List<CollectorLog> findAllBySuccessFalseOrderByStartTimeDesc(Pageable pageable);
}
