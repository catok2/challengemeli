package com.challange.coupon.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public class Item {
    @Id
    private String idItem;
    private BigDecimal price;
}
