package com.challange.coupon.infrastructure.repository.mongo;

import com.challange.coupon.domain.model.ItemFavoriteStats;
import com.challange.coupon.domain.port.out.FavoriteItemRepositoryPort;
import com.challange.coupon.infrastructure.repository.mongo.document.ItemFavoriteDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

// En infrastructure/repository/mongodb/MongoFavoriteRepository.java
@Repository
public class MongoFavoriteRepository implements FavoriteItemRepositoryPort {


    private final MongoTemplate mongoTemplate;

    public MongoFavoriteRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ItemFavoriteStats> findAll() {
        return mongoTemplate.findAll(ItemFavoriteDocument.class)
                .stream()
                .map(doc -> new ItemFavoriteStats(doc.getItemId(), doc.getFavoriteCount()))
                .collect(toList());
    }

    @Override
    public List<ItemFavoriteStats> findTop5Favorites() {
        Query query = new Query()
                .with(Sort.by(Sort.Direction.DESC, "favoriteCount"))
                .limit(5);

        return mongoTemplate.find(query, ItemFavoriteDocument.class)
                .stream()
                .map(doc -> new ItemFavoriteStats(doc.getItemId(), doc.getFavoriteCount()))
                .collect(toList());
    }

    @Override
    public void save(ItemFavoriteStats stats) {
        Query query = new Query(Criteria.where("itemId").is(stats.getItemId()));
        Update update = new Update()
                .set("favoriteCount", stats.getFavoriteCount())
                .set("lastUpdated", LocalDateTime.now());
        mongoTemplate.upsert(query, update, ItemFavoriteDocument.class);
    }

    @Override
    public void incrementFavoriteCount(String itemId) {
        Query query = new Query(Criteria.where("itemId").is(itemId));
        Update update = new Update()
                .inc("favoriteCount", 1)
                .set("lastUpdated", LocalDateTime.now());

        mongoTemplate.upsert(query, update, ItemFavoriteDocument.class);
    }

    @Override
    public int incrementFavoriteCounts(String itemId) {
        Query query = new Query(Criteria.where("_id").is(itemId));
        Update update = new Update()
                .inc("quantity", 1)
                .set("lastUpdated", LocalDateTime.now());

        FindAndModifyOptions options = new FindAndModifyOptions()
                .returnNew(true)
                .upsert(true);

        ItemFavoriteDocument updated = mongoTemplate.findAndModify(
                query,
                update,
                options,
                ItemFavoriteDocument.class
        );

        return updated != null ? updated.getFavoriteCount() : 1;
    }

}