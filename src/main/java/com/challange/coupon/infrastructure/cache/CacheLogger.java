package com.challange.coupon.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.List;

public class CacheLogger {
    private final Cache<String, BigDecimal> priceCache;
    private final Cache<String, List<String>> userFavoritesCache;

    public CacheLogger(Cache<String, BigDecimal> priceCache, Cache<String, List<String>> userFavoritesCache) {
        this.priceCache = priceCache;
        this.userFavoritesCache = userFavoritesCache;
    }

    @Scheduled(fixedRate = 60_000) // Cada minuto
    public void logCacheStats() {
        logCacheStatus("Price Cache", priceCache);
        logCacheStatus("User Favorites Cache", userFavoritesCache);
    }

    private <K, V> void logCacheStatus(String cacheName, Cache<K, V> cache) {
        System.out.println("\n=== " + cacheName + " ===");
        System.out.println("Tamaño actual: " + cache.estimatedSize());
        System.out.printf("Hit Rate: %.2f%%\n", cache.stats().hitRate() * 100);
        System.out.printf("Miss Rate: %.2f%%\n", cache.stats().missRate() * 100);
        System.out.println("Total requests: " + cache.stats().requestCount());
    }
}
