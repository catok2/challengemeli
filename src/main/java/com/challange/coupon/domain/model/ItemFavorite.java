package com.challange.coupon.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Data
@NoArgsConstructor
@Document(collection = "item_favorites")
public class ItemFavorite {
    @Id
    private String id; // Este mapea automáticamente al campo `_id` de MongoDB

    @Field("item_id")
    private String itemId;

    private Integer quantity;
}
