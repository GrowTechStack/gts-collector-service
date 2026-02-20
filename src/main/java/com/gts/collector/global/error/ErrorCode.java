package com.gts.collector.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 도메인 전용 에러 코드 정의
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부 오류가 발생했습니다."),

    // Content
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CON001", "콘텐츠를 찾을 수 없습니다."),
    RSS_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CON002", "RSS 피드 파싱 중 오류가 발생했습니다."),
    RSS_SOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "CON003", "RSS 출처를 찾을 수 없습니다."),

    // Collector
    RSS_CONNECTION_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "COL001", "RSS 서버 연결 시간이 초과되었습니다."),
    RSS_UNKNOWN_HOST(HttpStatus.BAD_GATEWAY, "COL002", "RSS 서버 주소를 찾을 수 없습니다."),
    RSS_HTTP_ERROR(HttpStatus.BAD_GATEWAY, "COL003", "RSS 서버가 비정상 응답을 반환했습니다."),
    RSS_INVALID_FEED(HttpStatus.UNPROCESSABLE_ENTITY, "COL004", "유효하지 않은 RSS 피드 형식입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
