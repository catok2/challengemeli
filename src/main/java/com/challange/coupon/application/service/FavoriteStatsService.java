package com.challange.coupon.application.service;

import com.challange.coupon.domain.model.ItemFavoriteStats;
import com.challange.coupon.domain.service.FavoriteStatsDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteStatsService  {
    private final FavoriteStatsDomainService statsDomainService;

    public FavoriteStatsService(FavoriteStatsDomainService statsDomainService) {
        this.statsDomainService = statsDomainService;
    }
   // ENVIO LA LOGICA AL DOMAIN PARA OPTENER LOS 5 FAVORITOS
    public List<ItemFavoriteStats> getTop5Favorites() {
        return statsDomainService.getTop5ItemsByFavorites();
    }



}
