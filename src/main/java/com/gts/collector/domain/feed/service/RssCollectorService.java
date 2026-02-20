package com.gts.collector.domain.feed.service;

/**
 * 외부 RSS 피드로부터 콘텐츠를 수집하는 서비스 인터페이스
 */
public interface RssCollectorService {

    /**
     * 지정된 RSS URL에서 새로운 글을 수집하여 저장합니다.
     * @param rssUrl  수집할 RSS 피드 URL
     * @param siteName 출처 사이트명
     * @return 새로 저장된 콘텐츠 개수
     */
    int collect(String rssUrl, String siteName);
}
