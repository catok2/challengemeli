package com.challange.coupon.domain.exception;


public class ItemException extends DomainException {
    private final ErrorCode enumErrorCode;
    private final String itemId;

    public enum ErrorCode {
        NOT_FOUND("ITEM-001"),
        INVALID_PRICE("ITEM-002"),
        API_FETCH_ERROR("ITEM-003");
        private final String code;
        ErrorCode(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }
    }


    public ItemException(String itemId, String message, ErrorCode errorCode) {
        super(message, errorCode.getCode());
        this.enumErrorCode = errorCode;
        this.itemId = itemId;
    }



    public String getItemId() {
        return itemId;
    }

    public ErrorCode getEnumErrorCode() {
        return enumErrorCode;
    }
}