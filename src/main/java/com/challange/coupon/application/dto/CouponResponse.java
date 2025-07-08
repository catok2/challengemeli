package com.challange.coupon.application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CouponResponse {
    private List<String> item_ids;
    private BigDecimal total;
}
