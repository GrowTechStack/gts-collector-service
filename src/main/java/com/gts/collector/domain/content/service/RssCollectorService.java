package com.gts.collector.domain.content.service;

/**
 * 외부 RSS 피드로부터 콘텐츠를 수집하는 서비스 인터페이스
 */
public interface RssCollectorService {

    /**
     * 지정된 RSS URL에서 새로운 글을 수집하여 저장합니다.
     */
    int collect(String rssUrl, String siteName);
}
