package com.challange.coupon.application.service;

import com.challange.coupon.application.dto.FavoriteStatsResponse;
import com.challange.coupon.domain.model.CouponResult;
import com.challange.coupon.domain.model.Item;
import com.challange.coupon.domain.model.ItemFavorite;
import com.challange.coupon.domain.port.in.CouponUseCase;
import com.challange.coupon.domain.port.out.FavoriteItemRepositoryPort;
import com.challange.coupon.domain.port.out.PriceClientPort;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class FavoriteStatsService  {
    private final FavoriteItemRepositoryPort repository;

    public FavoriteStatsService(FavoriteItemRepositoryPort repository) {
        this.repository = repository;
    }

    public List<ItemFavorite> getTop5MostFavoritedItems() {
        // Obtiene todos los elementos favoritos de la base de datos
        List<ItemFavorite> allFavorites = repository.findAll();

        // Ordena la lista por cantidad de forma descendente y toma los primeros 5
        List<ItemFavorite> top5Favorites = allFavorites.stream()
                .sorted(Comparator.comparing(ItemFavorite::getQuantity).reversed())
                .limit(5)
                .collect(Collectors.toList()); // <-- Aquí recogemos los objetos ItemFavorite completos

        return top5Favorites;

    }

}
