package com.challange.coupon.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CacheStatsLogger {

    private final Cache<String, BigDecimal> priceCache;

    @Scheduled(fixedRate = 60_000) // Cada minuto
    public void logStats() {
        System.out.println("\n=== Cache Statistics ===");
        System.out.printf("Hit Rate: %.2f%%\n", priceCache.stats().hitRate() * 100);
        System.out.println("Requests: " + priceCache.stats().requestCount());
        System.out.println("Size: " + priceCache.estimatedSize() + " items");
        System.out.println("Evictions: " + priceCache.stats().evictionCount());
    }
}