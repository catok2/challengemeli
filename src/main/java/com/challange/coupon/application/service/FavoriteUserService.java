package com.challange.coupon.application.service;


import com.challange.coupon.application.dto.response.FavoriteResponse;
import com.challange.coupon.domain.port.out.FavoriteItemRepositoryPort;
import com.challange.coupon.domain.port.out.UserFavoriteRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteUserService {

    private final FavoriteItemRepositoryPort itemFavoriteRepository;
    private final UserFavoriteRepositoryPort  userFavoriteRepository;

    public FavoriteUserService(FavoriteItemRepositoryPort itemFavoriteRepository, UserFavoriteRepositoryPort  userFavoriteRepository) {
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