package com.challange.coupon.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteStatsResponse {

    private String userId;
    private List<String> mostUsedItems;
}
