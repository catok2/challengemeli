package com.challange.coupon.web.controller;

import com.challange.coupon.domain.port.in.CouponUseCase;
import com.challange.coupon.application.dto.CouponRequest;
import com.challange.coupon.application.dto.CouponResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponUseCase couponUseCase;
    public CouponController(CouponUseCase couponUseCase) {
        this.couponUseCase = couponUseCase;
    }

    @PostMapping
    public CouponResponse applyCoupon(@RequestBody CouponRequest request) {
        var result = couponUseCase.calculateBestItems(request.getItem_ids(), request.getAmount());
        return new CouponResponse(result.getItemIds(), result.getTotal());
    }
}
