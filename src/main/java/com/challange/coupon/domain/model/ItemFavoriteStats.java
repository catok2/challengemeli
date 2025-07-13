package com.challange.coupon.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ItemFavoriteStats {
    private String itemId;
    private int favoriteCount;
    public void incrementCount() {
        this.favoriteCount++;
    }
}
