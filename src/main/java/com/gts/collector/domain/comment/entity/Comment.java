package com.gts.collector.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/** 
 * 콘텐츠의 댓글 정보를 관리하는 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private Long userId;

    @Lob
    @Column(nullable = false)
    private String body;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Comment(Long contentId, Long userId, String body) {
        this.contentId = contentId;
        this.userId = userId;
        this.body = body;
    }
}
