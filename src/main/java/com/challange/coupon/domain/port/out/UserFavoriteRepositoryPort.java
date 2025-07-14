package com.challange.coupon.domain.port.out;

import com.challange.coupon.domain.model.ItemFavoriteStats;

import java.util.List;

public interface UserFavoriteRepositoryPort {
    void addFavoriteByUser(String userId, String itemId); //agrego el documento de usuario y items.

}
