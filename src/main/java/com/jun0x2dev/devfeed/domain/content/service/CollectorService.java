package com.jun0x2dev.devfeed.domain.content.service;

/**
 * 콘텐츠 수집 프로세스를 총괄하는 서비스 인터페이스
 */
public interface CollectorService {

    /**
     * 모든 활성화된 RSS 출처로부터 콘텐츠를 즉시 수집합니다.
     * @return 수집된 전체 콘텐츠 개수
     */
    int collectAll();
}
