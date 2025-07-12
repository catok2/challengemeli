package com.challange.coupon.domain.service;

import com.challange.coupon.domain.model.CouponResult;
import com.challange.coupon.domain.model.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponDomainService {

    public CouponResult calculateOptimalCoupon(List<Item> items, BigDecimal maxAmount) {
        List<Item> validItems = filterAndSortItems(items);
        return selectItemsWithinBudget(validItems, maxAmount);
    }

    private List<Item> filterAndSortItems(List<Item> items) {
        return items.stream()
                .filter(item -> item.getPrice().compareTo(BigDecimal.ZERO) > 0)
                .sorted(Comparator.comparing(Item::getPrice))
                .collect(Collectors.toList());
    }

    private CouponResult selectItemsWithinBudget(List<Item> items, BigDecimal maxAmount) {
        List<String> selectedIds = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : items) {
            BigDecimal newTotal = total.add(item.getPrice());
            if (newTotal.compareTo(maxAmount) <= 0) {
                selectedIds.add(item.getIdItem());
                total = newTotal;
            }
        }
        return new CouponResult(selectedIds, total);
    }
}