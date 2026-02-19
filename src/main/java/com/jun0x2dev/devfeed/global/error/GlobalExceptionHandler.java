package com.jun0x2dev.devfeed.global.error;

import com.jun0x2dev.devfeed.global.common.response.ApiResult;
import com.jun0x2dev.devfeed.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 전역 예외 처리 핸들러
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid 또는 @Validated로 에러가 발생했을 때 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResult<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.fail(ErrorCode.INVALID_INPUT_VALUE.getCode(), ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /**
     * @ModelAttribute로 바인딩 에러가 발생했을 때 처리
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ApiResult<?>> handleBindException(BindException e) {
        log.error("handleBindException", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.fail(ErrorCode.INVALID_INPUT_VALUE.getCode(), ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 바인딩 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ApiResult<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.fail(ErrorCode.INVALID_INPUT_VALUE.getCode(), ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    /**
     * 비즈니스 로직 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResult<?>> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException: {}", e.getErrorCode());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResult.fail(errorCode.getCode(), errorCode.getMessage()));
    }

    /**
     * 그 외 예상치 못한 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResult<?>> handleException(Exception e) {
        log.error("handleException", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.fail(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
