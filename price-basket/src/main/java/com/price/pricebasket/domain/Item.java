package com.price.pricebasket.domain;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Item {

    private Product product;

    private Integer quantity;

    private BigDecimal price;

    private Discount discount;
}
