package com.gts.collector.domain.content.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 수집 대상 RSS 출처 정보를 관리하는 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rss_sources")
public class RssSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String siteName; // 사이트 이름

    @Column(nullable = false, unique = true)
    private String rssUrl;   // RSS 피드 URL

    private boolean active;  // 활성화 여부

    @Builder
    public RssSource(String siteName, String rssUrl, boolean active) {
        this.siteName = siteName;
        this.rssUrl = rssUrl;
        this.active = active;
    }

    public void update(String siteName, String rssUrl, boolean active) {
        this.siteName = siteName;
        this.rssUrl = rssUrl;
        this.active = active;
    }
}
