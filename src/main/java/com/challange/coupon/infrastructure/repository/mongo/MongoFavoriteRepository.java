package com.challange.coupon.infrastructure.repository.mongo;

import com.challange.coupon.domain.exception.StatsException;
import com.challange.coupon.domain.model.ItemFavoriteStats;
import com.challange.coupon.domain.port.out.FavoriteItemRepositoryPort;
import com.challange.coupon.infrastructure.repository.mongo.document.ItemFavoriteDocument;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoFavoriteRepository implements FavoriteItemRepositoryPort {
    private final MongoTemplate mongoTemplate;
    private List<ItemFavoriteStats> top5Cache = null;
    public MongoFavoriteRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<ItemFavoriteStats> findAll() {
        try{
            List<ItemFavoriteStats> statsList = new ArrayList<>();
            List<ItemFavoriteDocument> documents = mongoTemplate.findAll(ItemFavoriteDocument.class);

            for (ItemFavoriteDocument doc : documents) {
                ItemFavoriteStats stats = new ItemFavoriteStats(doc.getItemId(), doc.getFavoriteCount());
                statsList.add(stats);
            }
            return statsList;
            } catch (Exception e) {
            throw new StatsException("Error al obtener todos los favoritos",
                    StatsException.ErrorCode.DATABASE_ERROR, e);
        }
    }

    @Override
    public List<ItemFavoriteStats> findTop5Favorites() {
        if (top5Cache != null) {
            return top5Cache;
        }

        try {
            List<ItemFavoriteStats> topFavorites = new ArrayList<>();
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.DESC, "favoriteCount"));
            query.limit(5);

            List<ItemFavoriteDocument> documents = mongoTemplate.find(query, ItemFavoriteDocument.class);

            for (ItemFavoriteDocument doc : documents) {
                ItemFavoriteStats stats = new ItemFavoriteStats(doc.getItemId(), doc.getFavoriteCount());
                topFavorites.add(stats);
            }
            this.top5Cache = topFavorites;
            return topFavorites;
        } catch (DataAccessException e) {
            throw new StatsException("Error al obtener los top 5 favoritos",
                    StatsException.ErrorCode.DATABASE_ERROR, e);
        }
    }

    @Override
    public void save(ItemFavoriteStats stats) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("itemId").is(stats.getItemId()));
            Update update = new Update();
            update.set("favoriteCount", stats.getFavoriteCount());
            update.set("lastUpdated", LocalDateTime.now());

            mongoTemplate.upsert(query, update, ItemFavoriteDocument.class);
            this.top5Cache = null;
        } catch (DataAccessException e) {
            throw new StatsException("Error al guardar estadísticas para item: " + stats.getItemId(),
                    StatsException.ErrorCode.DATABASE_ERROR, e);
        }
    }

    @Override
    public void incrementFavoriteCount(String itemId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("itemId").is(itemId));
            Update update = new Update();
            update.inc("favoriteCount", 1);
            update.set("lastUpdated", LocalDateTime.now());

            mongoTemplate.upsert(query, update, ItemFavoriteDocument.class);
            this.top5Cache = null;
        } catch (DataAccessException e) {
            throw new StatsException("Error al incrementar favoritos para item: " + itemId,
                    StatsException.ErrorCode.DATABASE_ERROR, e);
        }
    }

    @Override
    public int incrementFavoriteCounts(String itemId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(itemId));

            Update update = new Update();
            update.inc("quantity", 1);
            update.set("lastUpdated", LocalDateTime.now());

            FindAndModifyOptions options = new FindAndModifyOptions();
            options.returnNew(true);
            options.upsert(true);

            ItemFavoriteDocument updated = mongoTemplate.findAndModify(
                    query,
                    update,
                    options,
                    ItemFavoriteDocument.class
            );
            this.top5Cache = null;
            if (updated != null) {
                return updated.getFavoriteCount();
            } else {
                return 1;
            }
        } catch (DataAccessException e) {
            throw new StatsException("Error al incrementar contador de favoritos para item: " + itemId,
                    StatsException.ErrorCode.DATABASE_ERROR, e);
        }
    }

}