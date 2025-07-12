package com.challange.coupon.application.service;


import com.challange.coupon.application.dto.response.FavoriteResponse;
import com.challange.coupon.domain.port.out.FavoriteItemRepositoryPort;
import com.challange.coupon.domain.port.out.UserFavoriteRepositoryPort;
import com.challange.coupon.infrastructure.repository.mongo.MongoUserFavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteItemRepositoryPort itemFavoriteRepository;
    private final UserFavoriteRepositoryPort  userFavoriteRepository;

    public FavoriteService(FavoriteItemRepositoryPort itemFavoriteRepository, UserFavoriteRepositoryPort  userFavoriteRepository) {
        this.itemFavoriteRepository = itemFavoriteRepository;
        this.userFavoriteRepository = userFavoriteRepository;
    }

    @Transactional
    public FavoriteResponse addFavorite(String userId, String itemId) {
        userFavoriteRepository.addFavoriteByUser(userId, itemId);
        int newCount = itemFavoriteRepository.incrementFavoriteCounts(itemId);

        return new FavoriteResponse(
                "Ítem agregado a favoritos",
                newCount
        );
    }
}