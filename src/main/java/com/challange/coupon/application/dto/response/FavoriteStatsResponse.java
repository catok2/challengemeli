package com.challange.coupon.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteStatsResponse {

    private String userId;
    private List<String> mostUsedItems;
}
