package com.challange.coupon.application.exception;

import com.challange.coupon.domain.exception.CouponException;
import com.challange.coupon.domain.exception.ItemException;
import com.challange.coupon.domain.exception.StatsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ErrorResponse> handleItemException(ItemException ex) {
        HttpStatus status = switch(ex.getEnumErrorCode()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND; // 404
            case INVALID_PRICE -> HttpStatus.UNPROCESSABLE_ENTITY; // 422
            case API_FETCH_ERROR -> HttpStatus.BAD_GATEWAY; // 502
        };
        return new ResponseEntity<>(
                new ErrorResponse(ex.getErrorCode(), "Error con ítem: " + ex.getMessage(),
                        ex.getItemId()),
                status
        );
    }

    @ExceptionHandler(StatsException.class)
    public ResponseEntity<ErrorResponse> handleStatsException(StatsException e) {
        HttpStatus status = switch(e.getErrorCode()) {
            case DUPLICATE_ITEM -> HttpStatus.CONFLICT;
            case DATABASE_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };

        return ResponseEntity.status(status)
                .body(new ErrorResponse(e.getErrorCode().name(), e.getMessage()));
    }


    @ExceptionHandler(CouponException.class)
    public ResponseEntity<ErrorResponse> handleCouponException(CouponException ex) {
        HttpStatus status = switch(ex.getEnumErrorCode()) {
            case ITEM_NOT_FOUND -> HttpStatus.BAD_REQUEST;
            case NO_VALID_COMBINATION -> HttpStatus.UNPROCESSABLE_ENTITY;
            case CALCULATION_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };
        return new ResponseEntity<>(
                new ErrorResponse(ex.getErrorCode(), ex.getMessage()),
                status
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse("GENERIC-ERROR", "Error interno del servidor"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public record ErrorResponse(String code, String message, String itemId) {
        public ErrorResponse(String code, String message) {
            this(code, message, null);
        }
    }

}