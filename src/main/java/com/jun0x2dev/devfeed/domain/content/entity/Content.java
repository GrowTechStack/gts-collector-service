package com.jun0x2dev.devfeed.domain.content.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 외부 및 사용자 콘텐츠 정보를 관리하는 통합 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contents")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType type;

    @Column(nullable = false)
    private String title;

    @Lob
    private String body;

    @Lob
    private String summary;

    @Column(unique = true)
    private String originalUrl;

    private String siteName;

    private String tags;

    private Long authorId;

    private long viewCount;

    private boolean commentEnabled;

    private LocalDateTime publishedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Content(ContentType type, String title, String body, String summary, 
                   String originalUrl, String siteName, String tags, Long authorId, 
                   boolean commentEnabled, LocalDateTime publishedAt) {
        this.type = type;
        this.title = title;
        this.body = body;
        this.summary = summary;
        this.originalUrl = originalUrl;
        this.siteName = siteName;
        this.tags = tags;
        this.authorId = authorId;
        this.commentEnabled = commentEnabled;
        this.publishedAt = publishedAt;
        this.viewCount = 0;
    }

    /**
     * 조회수 증가 로직
     */
    public void incrementViewCount() {
        this.viewCount++;
    }

    /**
     * 콘텐츠의 출처 및 태그 정보를 업데이트합니다.
     */
    public void updateMetadata(String siteName, String tags) {
        this.siteName = siteName;
        this.tags = tags;
    }
}
