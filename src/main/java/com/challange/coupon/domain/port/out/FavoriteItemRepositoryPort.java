package com.challange.coupon.domain.port.out;

import com.challange.coupon.domain.model.ItemFavoriteStats;

import java.util.List;

public interface FavoriteItemRepositoryPort {
    List<ItemFavoriteStats> findAll(); // optiene todos los items para filtrarlos los top 5
    void incrementFavoriteCount(String itemId); // Solo incrementa uno
    int incrementFavoriteCounts(String itemId); //Incrementa 1 y devuelve la cantidad
    void save(ItemFavoriteStats stats);
    List<ItemFavoriteStats> findTop5Favorites();
}
