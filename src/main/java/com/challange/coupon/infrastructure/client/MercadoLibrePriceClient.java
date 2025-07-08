package com.challange.coupon.infrastructure.client;

import com.challange.coupon.domain.model.Item;
import com.challange.coupon.domain.port.out.PriceClientPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
// Infraestructura - implementación concreta
public class MercadoLibrePriceClient implements PriceClientPort {
    private static final Map<String, BigDecimal> MOCK_PRICES = new HashMap<>();

    static {
        MOCK_PRICES.put("MLA1", new BigDecimal("501"));
        MOCK_PRICES.put("MLA2", new BigDecimal("501"));
        MOCK_PRICES.put("MLA3", new BigDecimal("501"));
        MOCK_PRICES.put("MLA4", new BigDecimal("501"));
        MOCK_PRICES.put("MLA5", new BigDecimal("501"));
    }

    @Override
    public List<Item>  getPrices(List<String> itemIds) {
        return itemIds.stream()
                .filter(MOCK_PRICES::containsKey)
                .map(id -> new Item(id, MOCK_PRICES.get(id)))
                .collect(Collectors.toList());
    }
}
