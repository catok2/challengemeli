package com.challange.coupon.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Configuration
public class PriceCacheConfig {

    @Bean
    public Cache<String, BigDecimal> priceCache() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Capacidad máxima
                .expireAfterWrite(30, TimeUnit.MINUTES) // Tiempo de vida
                .recordStats() // Para monitoreo
                .build();
    }
}