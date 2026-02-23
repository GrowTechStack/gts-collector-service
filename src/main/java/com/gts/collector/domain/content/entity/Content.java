package com.gts.collector.domain.content.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 콘텐츠 정보를 관리하는 핵심 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType type; // EXTERNAL, USER

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String body; // USER 타입일 경우 사용

    @Column(length = 500)
    private String originalUrl; // EXTERNAL 타입일 경우 원문 링크

    private String siteName; // 출처 사이트명 (예: 토스 테크)

    private String thumbnailUrl; // 썸네일 이미지 URL

    private String tags; // 쉼표로 구분된 태그 목록

    private int viewCount;

    private LocalDateTime publishedAt; // 원문 발행일 또는 작성일

    private LocalDateTime createdAt;

    private boolean commentEnabled;

    @Builder
    public Content(ContentType type, String title, String summary, String body, 
                   String originalUrl, String siteName, String thumbnailUrl, String tags, 
                   LocalDateTime publishedAt, boolean commentEnabled) {
        this.type = type;
        this.title = title;
        this.summary = summary;
        this.body = body;
        this.originalUrl = originalUrl;
        this.siteName = siteName;
        this.thumbnailUrl = thumbnailUrl;
        this.tags = tags;
        this.publishedAt = publishedAt;
        this.commentEnabled = commentEnabled;
        this.viewCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 조회수를 1 증가시킵니다.
     */
    public void incrementViewCount() {
        this.viewCount++;
    }

    /**
     * AI 생성 요약문으로 summary를 업데이트합니다.
     */
    public void updateSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 메타데이터(사이트명, 태그, 썸네일)를 업데이트합니다.
     */
    public void updateMetadata(String siteName, String tags, String thumbnailUrl) {
        this.siteName = siteName;
        this.tags = tags;
        if (thumbnailUrl != null) this.thumbnailUrl = thumbnailUrl;
    }
}
