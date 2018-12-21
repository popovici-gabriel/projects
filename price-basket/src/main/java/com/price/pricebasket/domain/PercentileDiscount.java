package com.price.pricebasket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PercentileDiscount implements Discount {
    private BigDecimal percentage;

    @Override
    public boolean isApplicable(Item item) {
        return percentage != null 
                && item != null 
                && item.getQuantity() != 0;
    }
}
