package com.challange.coupon.domain.port.out;

import com.challange.coupon.domain.model.ItemFavorite;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface FavoriteItemRepositoryPort {
    List<ItemFavorite> findAll();


}
