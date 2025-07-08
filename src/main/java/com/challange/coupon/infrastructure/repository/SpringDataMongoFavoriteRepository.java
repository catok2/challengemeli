package com.challange.coupon.infrastructure.repository;

import com.challange.coupon.domain.model.ItemFavorite;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface SpringDataMongoFavoriteRepository extends MongoRepository<ItemFavorite, String> {

   Optional<ItemFavorite> findByItemId(String itemId);
}