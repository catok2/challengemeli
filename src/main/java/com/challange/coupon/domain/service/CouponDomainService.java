package com.challange.coupon.domain.service;

import com.challange.coupon.domain.exception.CouponException;
import com.challange.coupon.domain.exception.DomainException;
import com.challange.coupon.domain.model.CouponResult;
import com.challange.coupon.domain.model.Item;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CouponDomainService {
    private final Cache<String, CouponResult> couponCache;

    public CouponDomainService(Cache<String, CouponResult> couponCache) {
        this.couponCache = couponCache;
    }
    // valida el monto primero.
    // despues valida los items.
    // despues de la validacion optiene la mejor combinacion
    public CouponResult calculateBestCouponCombination(List<Item> items, BigDecimal maxAmount) {
       try{
           validateInput( maxAmount);
           List<Item> validItems = filterValidItems(items);
           return findOptimalCombination(validItems, maxAmount);
       }catch(DomainException e){
           throw e;
       }catch (Exception e) {
           throw new CouponException("Error inesperado en cálculo de cupón :" + e ,
                                    CouponException.ErrorCode.CALCULATION_ERROR , e);
       }
    }

    // Si la api de meli falla y no esta el precio en cache , seteando el item en 0
    // aca lo filtro para no tenerlo encuenta en la combinacion
    private List<Item> filterValidItems(List<Item> items) {
        List<Item> validItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getPrice() == null) {
                throw new CouponException(
                        "El ítem " + item.getIdItem() + " no tiene precio",
                        CouponException.ErrorCode.ITEM_NOT_FOUND
                );
            }
            if (item.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                validItems.add(item);
            }
        }
        validItems.sort(Comparator.comparing(Item::getPrice));
        return validItems;
    }

    private CouponResult findOptimalCombination(List<Item> items, BigDecimal maxAmount) {
        List<String> itemsSeleccionados = new ArrayList<>();
        BigDecimal totalAcumulado = BigDecimal.ZERO;
        for (Item item : items) {
            BigDecimal precioItem = item.getPrice();
            BigDecimal posibleTotal = totalAcumulado.add(precioItem);
            if (posibleTotal.compareTo(maxAmount) <= 0) {
                itemsSeleccionados.add(item.getIdItem());
                totalAcumulado = posibleTotal;
            } else {
                break;
            }
        }
        if (itemsSeleccionados.isEmpty()) {
            throw new CouponException(
                    "No hay combinación válida para el monto especificado",
                    CouponException.ErrorCode.NO_VALID_COMBINATION
            );
        }
        return new CouponResult(itemsSeleccionados, totalAcumulado);
    }


    private void validateInput( BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CouponException(
                    "El monto debe ser positivo",
                    CouponException.ErrorCode.INVALID_AMOUNT
            );
        }
    }
}