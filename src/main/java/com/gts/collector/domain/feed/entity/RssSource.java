package com.gts.collector.domain.feed.entity;

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
    private String siteName;

    @Column(nullable = false, unique = true)
    private String rssUrl;

    private String logoUrl;

    private String siteUrl;

    private boolean active;

    @Builder
    public RssSource(String siteName, String rssUrl, String logoUrl, String siteUrl, boolean active) {
        this.siteName = siteName;
        this.rssUrl = rssUrl;
        this.logoUrl = logoUrl;
        this.siteUrl = siteUrl;
        this.active = active;
    }

    /** 출처 정보를 전체 업데이트합니다. */
    public void update(String siteName, String rssUrl, String logoUrl, String siteUrl, boolean active) {
        this.siteName = siteName;
        this.rssUrl = rssUrl;
        this.logoUrl = logoUrl;
        this.siteUrl = siteUrl;
        this.active = active;
    }
}
