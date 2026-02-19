package com.jun0x2dev.devfeed.global.common.response;

/**
 * 모든 API 응답의 표준 포맷
 * @param success 성공 여부
 * @param data 반환 데이터 (실패 시 null)
 * @param error 에러 정보 (성공 시 null)
 */
public record ApiResult<T>(
    boolean success,
    T data,
    ApiError error
) {
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, data, null);
    }

    public static ApiResult<?> fail(String code, String message) {
        return new ApiResult<>(false, null, new ApiError(code, message));
    }

    public record ApiError(String code, String message) {}
}
