package com.challange.coupon.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteResponse {
    private String message;
    private int newFavoriteCount;
}