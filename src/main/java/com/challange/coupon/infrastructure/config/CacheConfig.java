package com.challange.coupon.infrastructure.config;

import com.challange.coupon.domain.model.CouponResult;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
public class CacheConfig {

    // Cache para precios (usado en MercadoLibrePriceClient)
    @Bean
    public Cache<String, BigDecimal> priceCache() {
        return Caffeine.newBuilder()
                .maximumSize(100_000)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    // Cache para relaciones usuario-favoritos
    @Bean
    public Cache<String, List<String>> userFavoritesCache() {
        return Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterAccess(2, TimeUnit.HOURS)
                .softValues()
                .build();
    }

    // Cache para estadísticas de items populares
    @Bean
    public Cache<String, Integer> itemStatsCache() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Top 10,000 items más populares
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }


    @Bean
    public Cache<String, CouponResult> couponCache() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Capacidad para 10k combinaciones
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }

}