package com.challange.coupon.application.service;

import com.challange.coupon.domain.model.CouponResult;
import com.challange.coupon.domain.model.Item;
import com.challange.coupon.domain.port.in.CouponUseCase;
import com.challange.coupon.domain.port.out.PriceClientPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService implements CouponUseCase {
    private final PriceClientPort priceClientPort;

    public CouponService(PriceClientPort priceClientPort) {
        this.priceClientPort = priceClientPort;
    }

    // dado una lista de item_id y el monto total pueda darle la lista de ítems
    //que maximice el total gastado.
    @Override
    public CouponResult calculateBestItems(List<String> itemIds, BigDecimal amount) {
        //Obtengo el precio de todos los items
        List<Item> allItems = priceClientPort.getPrices(itemIds);

        List<Item> sortedItems = allItems.stream()
                .sorted(Comparator.comparing(Item::getPrice))
                .collect(Collectors.toList());

        //Array de items recomendados
        List<String> selectedItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        //voy agregando precio al total
        for (Item item : sortedItems) {
            if (total.add(item.getPrice()).compareTo(amount) <= 0) {
                selectedItems.add(item.getIdItem());
                total = total.add(item.getPrice());
            }
        }
        List<String> orderedSelectedItems = itemIds.stream()
                .filter(selectedItems::contains)
                .collect(Collectors.toList());
        // Devuelvo el array de items con el monto para el descuento.
        return new CouponResult(orderedSelectedItems, total);
    }
}