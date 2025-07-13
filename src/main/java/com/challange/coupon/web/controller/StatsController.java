package com.challange.coupon.web.controller;

import com.challange.coupon.application.dto.request.FavoriteRequest;
import com.challange.coupon.application.dto.response.FavoriteResponse;
import com.challange.coupon.application.service.FavoriteUserService;
import com.challange.coupon.application.service.FavoriteStatsService;
import com.challange.coupon.domain.model.ItemFavoriteStats;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class StatsController {

    private final FavoriteStatsService favoriteStatsService;
    private final FavoriteUserService favoriteUserService;

    public StatsController(FavoriteStatsService favoriteStatsService, FavoriteUserService favoriteUserService) {
        this.favoriteStatsService = favoriteStatsService;
        this.favoriteUserService = favoriteUserService;
    }

    @GetMapping("stats/favorites")
    public List<ItemFavoriteStats> getFavoriteStats() {
      return favoriteStatsService.getTop5Favorites();
    }

    @PostMapping("user/favorite")
    public ResponseEntity<FavoriteResponse> addFavorite(
            @RequestBody FavoriteRequest request
    ) {
        FavoriteResponse response = favoriteUserService.addFavorite(
                request.getUserId(),
                request.getItemId()
        );
        return ResponseEntity.ok(response);
    }
}
