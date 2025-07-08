package com.challange.coupon.infrastructure.repository;

import com.challange.coupon.domain.model.ItemFavorite;
import com.challange.coupon.domain.port.out.FavoriteItemRepositoryPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class InMemoryFavoriteItemRepository implements FavoriteItemRepositoryPort {
    private final SpringDataMongoFavoriteRepository repository;

    public InMemoryFavoriteItemRepository(SpringDataMongoFavoriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ItemFavorite> findAll() {
         return repository.findAll();
    }
}
