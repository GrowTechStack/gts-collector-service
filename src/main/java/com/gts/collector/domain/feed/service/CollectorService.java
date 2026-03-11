package com.gts.collector.domain.feed.service;

/**
 * 콘텐츠 수집 프로세스를 총괄하는 서비스 인터페이스
 */
public interface CollectorService {

    /**
     * 모든 활성화된 RSS 출처로부터 콘텐츠를 즉시 수집합니다.
     * @param full true면 전체 수집, false면 최근 2일치만 수집
     * @return 수집된 전체 콘텐츠 개수
     */
    int collectAll(boolean full);

    /**
     * 특정 RSS 출처 하나로부터 콘텐츠를 즉시 수집합니다.
     * @param sourceId 수집할 RSS 출처 ID
     * @param full true면 전체 수집, false면 최근 2일치만 수집
     * @return 수집된 콘텐츠 개수
     */
    int collectOne(Long sourceId, boolean full);

    /**
     * 요약이 없는 기존 콘텐츠에 대해 AI 요약 요청을 재전송합니다.
     * @return 요약 요청을 보낸 콘텐츠 개수
     */
    int resummary();

    /**
     * 본문이 누락된 전체 콘텐츠들의 본문을 재수집하고 AI 요약을 요청합니다.
     */
    int reCollectAllMissingBodies();

    /**
     * 특정 사이트의 본문이 누락된 콘텐츠들의 본문을 재수집하고 AI 요약을 요청합니다.
     */
    int reCollectMissingBodiesBySource(Long sourceId);
}
