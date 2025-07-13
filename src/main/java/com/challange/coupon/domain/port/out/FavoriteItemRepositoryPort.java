package com.challange.coupon.domain.port.out;

import com.challange.coupon.domain.model.ItemFavoriteStats;

import java.util.List;

public interface FavoriteItemRepositoryPort {
    List<ItemFavoriteStats> findAll(); // optiene todos los items para filtrarlos los top 5
    int incrementFavoriteCounts(String itemId);
    void incrementFavoriteCount(String itemId);// Incrementa y devuelve nuevo valor
    void save(ItemFavoriteStats stats);
    List<ItemFavoriteStats> findTop5Favorites(); // Opcional (puede hacerse en dominio)
}
