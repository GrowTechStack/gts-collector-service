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
    RSS_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CON002", "RSS 정보를 읽어오는 중 오류가 발생했습니다."),
    RSS_SOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "CON003", "RSS 출처를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
