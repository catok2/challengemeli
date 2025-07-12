package com.challange.coupon.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ItemFavoriteStats {
    private String itemId;
    private int favoriteCount; // Cantidad de usuarios que lo marcaron como favorito

    public void incrementCount() {
        this.favoriteCount++;
    }
}
