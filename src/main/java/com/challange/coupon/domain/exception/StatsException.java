package com.challange.coupon.domain.exception;


public class StatsException extends RuntimeException {
    public enum ErrorCode {
        DATABASE_ERROR,
        DUPLICATE_ITEM,
        ITEM_NOT_FOUND
    }

    private final ErrorCode errorCode;

    public StatsException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public StatsException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}