package com.challange.coupon.infrastructure.repository.mongo;

import com.challange.coupon.domain.port.out.UserFavoriteRepositoryPort;
import com.challange.coupon.infrastructure.repository.mongo.document.ItemFavoriteDocument;
import com.challange.coupon.infrastructure.repository.mongo.document.UserItemFavoriteDocument;
import com.mongodb.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MongoUserFavoriteRepository implements UserFavoriteRepositoryPort {
    private final MongoTemplate mongoTemplate;

    public MongoUserFavoriteRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void addFavoriteByUser(String userId, String itemId) {
        //mongoTemplate.save(new user_favorites(userId, itemId));
        try {
            UserItemFavoriteDocument doc = new UserItemFavoriteDocument(userId, itemId);
            mongoTemplate.insert(doc); // Usa insert() para asegurar que es nuevo
        } catch (DuplicateKeyException e){
             System.out.println(e);
            //throw new DuplicateFavoriteException("El ítem ya está en favoritos del usuario");
        }
    }

    @Override
    public boolean existsByUserAndItem(String userId, String itemId) {
        return false;
    }

    @Override
    public List<String> findFavoritesByUser(String userId) {
        return List.of();
    }
}