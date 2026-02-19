package com.jun0x2dev.devfeed.global.error.exception;

import com.jun0x2dev.devfeed.global.error.ErrorCode;
import lombok.Getter;

/**
 * 프로젝트 전역에서 사용하는 비즈니스 예외 클래스
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
