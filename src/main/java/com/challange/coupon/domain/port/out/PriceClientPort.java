package com.challange.coupon.domain.port.out;

import com.challange.coupon.domain.model.Item;

import java.math.BigDecimal;
import java.util.List;

public interface PriceClientPort {
    List<Item>  getPrices(List<String> itemIds);
}
