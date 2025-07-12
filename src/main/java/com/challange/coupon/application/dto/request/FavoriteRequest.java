package com.challange.coupon.application.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FavoriteRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String itemId;
}