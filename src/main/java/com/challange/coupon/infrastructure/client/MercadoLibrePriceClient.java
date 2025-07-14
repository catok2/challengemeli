package com.challange.coupon.infrastructure.client;


import com.challange.coupon.domain.exception.ItemException;
import com.challange.coupon.domain.model.Item;
import com.challange.coupon.domain.port.out.PriceClientPort;
import com.github.benmanes.caffeine.cache.Cache;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MercadoLibrePriceClient implements PriceClientPort {

    private final WebClient webClient;
    private final Cache<String, BigDecimal> priceCache;

    @Override
    @CircuitBreaker(name = "mercadoLibreApi", fallbackMethod = "getPricesFallback")
    @Retry(name = "mercadoLibreRetry", fallbackMethod = "getPricesFallback")
    public List<Item> getPrices(List<String> itemIds, String token) {
        List<Item> items = new ArrayList<>();
        for (String itemId : itemIds) {
            items.add(getItemPrice(itemId, token));
        }
        return items;
    }
    //Encargada de optener el precio de los items
    // Primero verifica si el precio esta en cache si no esta le pega a la api
    // se utiliza Resilience4j por si falla la api de meli en caso de fallar
    // setea el precio en 0 , despues es filtrado
    @CircuitBreaker(name = "mercadoLibreApi", fallbackMethod = "getItemPriceFallback")
    @Retry(name = "mercadoLibreRetry", fallbackMethod = "getItemPriceFallback")
    private Item getItemPrice(String itemId, String token) {
        BigDecimal price = priceCache.getIfPresent(itemId);
        if (price == null) {
            price = fetchPriceFromAPI(itemId, token);
            priceCache.put(itemId, price != null ? price : BigDecimal.ZERO);
        }
        return new Item(itemId, price != null ? price : BigDecimal.ZERO);
    }

    private BigDecimal fetchPriceFromAPI(String itemId, String token) {
        try {
            ItemResponse response = webClient.get()
                    .uri("/items/{id}", itemId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals,
                            clientResponse -> {
                                throw new ItemException(itemId, "Ítem no existe", ItemException.ErrorCode.NOT_FOUND);
                            })
                    .bodyToMono(ItemResponse.class)
                    .block();

            return response != null ? BigDecimal.valueOf(response.getPrice()) : null;

        } catch (WebClientResponseException.NotFound e) {
            throw new ItemException(
                    itemId, "Ítem no encontrado en API", ItemException.ErrorCode.NOT_FOUND
            );
        } catch (WebClientResponseException e) {
            throw new ItemException(itemId, "Error en API externa", ItemException.ErrorCode.API_FETCH_ERROR
            );
        }
    }

    private List<Item> getPricesFallback(List<String> itemIds, String token, Exception ex) {
        List<Item> fallbackItems = new ArrayList<>();
        for (String itemId : itemIds) {
            fallbackItems.add(new Item(itemId, BigDecimal.ZERO));
        }
        return fallbackItems;
    }

    private Item getItemPriceFallback(String itemId, String token, Exception ex) {
        return new Item(itemId, BigDecimal.ZERO);
    }

    @Data
    private static class ItemResponse {
        private double price;
    }
}