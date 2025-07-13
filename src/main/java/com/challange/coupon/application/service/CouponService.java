package com.challange.coupon.application.service;

import com.challange.coupon.domain.model.CouponResult;
import com.challange.coupon.domain.model.Item;
import com.challange.coupon.domain.port.in.CouponUseCase;
import com.challange.coupon.domain.port.out.PriceClientPort;
import com.challange.coupon.domain.service.CouponDomainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService implements CouponUseCase {
    private final PriceClientPort priceClientPort;
    private final CouponDomainService CouponDomainService;

    public CouponService(PriceClientPort priceClientPort, CouponDomainService couponDomainService) {
        this.priceClientPort = priceClientPort;
        CouponDomainService = couponDomainService;
    }

    // Optiene el precio de los items con el endpoint de mercadolibre https://api.mercadolibre.com/items .
    // Utiliza el servicio de dominio para calcular y devolver los items optimos para el cupon.
    @Override
    public CouponResult calculateBestItems(List<String> itemIds, BigDecimal amount, String token) {
        List<Item> items = priceClientPort.getPrices(itemIds, token);
        return CouponDomainService.calculateBestCouponCombination(items, amount);
    }
}