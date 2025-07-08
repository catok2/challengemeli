package com.challange.coupon.application.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CouponRequest {
    private List<String> item_ids;
    private BigDecimal amount;
}
