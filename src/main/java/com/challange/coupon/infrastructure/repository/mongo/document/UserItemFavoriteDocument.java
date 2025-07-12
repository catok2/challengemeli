package com.challange.coupon.infrastructure.repository.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

// En infrastructure/repository/mongodb/document/FavoriteDocument.java
@AllArgsConstructor
@Data
@Document(collection = "user_favorites")
@CompoundIndex(name = "user_item_unique_idx", def = "{'user_id': 1, 'item_id': 1}", unique = true)
public class UserItemFavoriteDocument {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("item_id")
    private String itemId;

    private LocalDateTime addedAt = LocalDateTime.now();

    public UserItemFavoriteDocument(String userId, String itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

}