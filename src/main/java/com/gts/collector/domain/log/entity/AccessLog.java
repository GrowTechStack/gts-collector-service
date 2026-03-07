package com.gts.collector.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "access_logs", indexes = {
        @Index(name = "idx_access_logs_created_at", columnList = "created_at")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hashed_ip", nullable = false, length = 64)
    private String hashedIp;

    @Column(name = "raw_ip", length = 45)
    private String rawIp;

    @Column(name = "path", length = 255)
    private String path;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private AccessLog(String hashedIp, String rawIp, String path, LocalDateTime createdAt) {
        this.hashedIp = hashedIp;
        this.rawIp = rawIp;
        this.path = path;
        this.createdAt = createdAt;
    }
}
