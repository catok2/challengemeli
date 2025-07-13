package com.challange.coupon.infrastructure.repository.mongo;

import com.challange.coupon.domain.exception.StatsException;
import com.challange.coupon.domain.port.out.UserFavoriteRepositoryPort;
import com.challange.coupon.infrastructure.repository.mongo.document.ItemFavoriteDocument;
import com.challange.coupon.infrastructure.repository.mongo.document.UserItemFavoriteDocument;
import com.github.benmanes.caffeine.cache.Cache;
import com.mongodb.DuplicateKeyException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoUserFavoriteRepository implements UserFavoriteRepositoryPort {
    private final MongoTemplate mongoTemplate;
    private final Cache<String, List<String>> userFavoritesCache;
    public MongoUserFavoriteRepository(MongoTemplate mongoTemplate, Cache<String, List<String>> userFavoritesCache) {
        this.mongoTemplate = mongoTemplate;
        this.userFavoritesCache = userFavoritesCache;
    }



    @Override
    public void addFavoriteByUser(String userId, String itemId) {
        try {
            UserItemFavoriteDocument doc = new UserItemFavoriteDocument(userId, itemId);
            mongoTemplate.insert(doc);
            List<String> favoritosActuales = userFavoritesCache.getIfPresent(userId);
            if (favoritosActuales != null) {
                if (!favoritosActuales.contains(itemId)) {
                    List<String> nuevosFavoritos = new ArrayList<>(favoritosActuales);
                    nuevosFavoritos.add(itemId);
                    userFavoritesCache.put(userId, nuevosFavoritos);
                }
            }// QUEDA ASI POR QUE SPRING NO INTERPRETA EL DUPLICATEKEY COMO EXCEPCION
        } catch (DataAccessException e) {
            if (e.getMessage().contains("E11000")) {
                throw new StatsException("El ítem "+itemId +" ya está en favoritos", StatsException.ErrorCode.DUPLICATE_ITEM, e);
            }
            throw new StatsException("Error de base de datos", StatsException.ErrorCode.DATABASE_ERROR, e);
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