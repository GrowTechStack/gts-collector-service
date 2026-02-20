package com.gts.collector.domain.feed.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * RSS 수집 실행 결과를 기록하는 로그 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "collector_logs")
public class CollectorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String siteName;

    @Column(nullable = false)
    private String rssUrl;

    private boolean success;

    private int collectedCount;

    @Column(length = 1000)
    private String errorMessage;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    public CollectorLog(String siteName, String rssUrl, boolean success,
                        int collectedCount, String errorMessage,
                        LocalDateTime startTime, LocalDateTime endTime) {
        this.siteName = siteName;
        this.rssUrl = rssUrl;
        this.success = success;
        this.collectedCount = collectedCount;
        this.errorMessage = errorMessage;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
