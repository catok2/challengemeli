package com.challange.coupon.web.controller;

import com.challange.coupon.application.dto.FavoriteStatsResponse;
import com.challange.coupon.application.service.FavoriteStatsService;
import com.challange.coupon.domain.model.ItemFavorite;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class StatsController {

    private final FavoriteStatsService favoriteStatsService;

    public StatsController(FavoriteStatsService favoriteStatsService) {
        this.favoriteStatsService = favoriteStatsService;
    }

    @GetMapping("stats/favorites")
    public List<ItemFavorite> getFavoriteStats() {
      return favoriteStatsService.getTop5MostFavoritedItems();
    }
}
