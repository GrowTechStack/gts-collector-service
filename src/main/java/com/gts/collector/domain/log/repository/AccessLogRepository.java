package com.gts.collector.domain.log.repository;

import com.gts.collector.domain.log.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    @Query("SELECT COUNT(DISTINCT a.hashedIp) FROM AccessLog a WHERE a.createdAt >= :since")
    long countDistinctIpSince(@Param("since") LocalDateTime since);

    List<AccessLog> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying
    @Query("DELETE FROM AccessLog a WHERE a.createdAt < :before")
    void deleteByCreatedAtBefore(@Param("before") LocalDateTime before);
}
