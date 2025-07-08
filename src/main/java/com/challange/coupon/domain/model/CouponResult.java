package com.challange.coupon.domain.model;

import lombok.Value;
import java.math.BigDecimal;
import java.util.List;

@Value
public class CouponResult {
    List<String> itemIds;
    BigDecimal total;
}