package com.gts.collector.domain.feed.service;

import java.time.LocalDateTime;

/**
 * 외부 RSS 피드로부터 콘텐츠를 수집하는 서비스 인터페이스
 */
public interface RssCollectorService {

    /**
     * 지정된 RSS URL에서 새로운 글을 수집하여 저장합니다.
     * @param rssUrl   수집할 RSS 피드 URL
     * @param siteName 출처 사이트명
     * @param since    이 시점 이후에 발행된 글만 수집. null이면 전체 수집.
     * @return 새로 저장된 콘텐츠 개수
     */
    int collect(String rssUrl, String siteName, LocalDateTime since);

    /**
     * 본문이 누락된 전체 콘텐츠들의 본문을 재수집합니다.
     * @return 재수집 성공 개수
     */
    int reCollectAllMissingBodies();

    /**
     * 특정 사이트의 본문이 누락된 콘텐츠들의 본문을 재수집합니다.
     * @param siteId RSS 출처 ID
     * @return 재수집 성공 개수
     */
    int reCollectMissingBodiesBySource(Long siteId);
}
