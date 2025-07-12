package com.challange.coupon.domain.port.in;

import com.challange.coupon.domain.model.CouponResult;

import java.math.BigDecimal;
import java.util.List;

public interface CouponUseCase {
    CouponResult calculateBestItems(List<String> itemIds, BigDecimal amount, String token);
}
