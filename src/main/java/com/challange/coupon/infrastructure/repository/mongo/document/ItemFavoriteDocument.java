package com.challange.coupon.infrastructure.repository.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "item_favorites")
public class ItemFavoriteDocument {
    @Id
    private String id;
    @Field("item_id")
    private String itemId;
    @Field("quantity")
    private int favoriteCount;

    private LocalDateTime lastUpdated;


}