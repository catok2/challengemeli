package com.challange.coupon.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Value;
import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Value
public class CouponResult {
    List<String> itemIds;
    BigDecimal total;
}