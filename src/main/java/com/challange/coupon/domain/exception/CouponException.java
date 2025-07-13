package com.challange.coupon.domain.exception;
public class CouponException extends DomainException {
    private final ErrorCode enumErrorCode;

    public enum ErrorCode {
        INVALID_AMOUNT("CUPON-001"),
        NO_VALID_COMBINATION("CUPON-002"),
        EXCEEDED_MAX_ITEMS("CUPON-003"),
        CALCULATION_ERROR("CUPON-004"),
        ITEM_NOT_FOUND("CUPON-005");

        private final String code;
        ErrorCode(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }
    }
    public CouponException(String message, ErrorCode errorCode) {
        super(message, errorCode.getCode());
        this.enumErrorCode = errorCode;
    }

    public CouponException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode.getCode(), cause);
        this.enumErrorCode = errorCode;
    }

    public ErrorCode getEnumErrorCode() {
        return enumErrorCode;
    }
}