package com.challange.coupon.domain.service;

import com.challange.coupon.domain.model.ItemFavoriteStats;
import com.challange.coupon.domain.port.out.FavoriteItemRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteStatsDomainService {
    private final FavoriteItemRepositoryPort repository;

    public FavoriteStatsDomainService(FavoriteItemRepositoryPort repository) {
        this.repository = repository;
    }

    public List<ItemFavoriteStats> getTop5ItemsByFavorites() {
       /* List<ItemFavoriteStats> allStats = repository.findAll();
        return allStats.stream()
                .sorted(Comparator.comparingInt(ItemFavoriteStats::getFavoriteCount).reversed())
                .limit(5)
                .collect(Collectors.toList());*/
      return  repository.findTop5Favorites();
    }

    public int addFavoriteByUser(String itemId) {
        return repository.incrementFavoriteCounts(itemId);
    }
    public void saveItemStats(ItemFavoriteStats stats) {
        repository.save(stats);
    }
}
