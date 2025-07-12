package com.challange.coupon.infrastructure.client;

import com.challange.coupon.domain.model.Item;
import com.challange.coupon.domain.port.out.PriceClientPort;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MercadoLibrePriceClient implements PriceClientPort {

    private final WebClient webClient;
    private final Cache<String, BigDecimal> priceCache; // Inyectamos el caché

    @Override
    public List<Item> getPrices(List<String> itemIds, String token) {
        return itemIds.stream()
                .map(itemId -> getPriceWithCache(itemId, token))
                .collect(Collectors.toList());
    }

    private Item getPriceWithCache(String itemId, String token) {
        BigDecimal price = priceCache.get(itemId, id -> fetchFromApi(id, token));
        return new Item(itemId, price != null ? price : BigDecimal.ZERO);
    }

    private BigDecimal fetchFromApi(String itemId, String token) {
        try {
            return webClient.get()
                    .uri("/items/{id}", itemId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(ItemResponse.class)
                    .map(response -> BigDecimal.valueOf(response.getPrice()))
                    .block();
        } catch (Exception e) {
            return null; // Será convertido a ZERO por getPriceWithCache
        }
    }
    @Data
    private static class ItemResponse {
        private double price;
    }
}

