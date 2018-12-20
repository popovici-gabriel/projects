package com.price.pricebasket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PercentileDiscount implements Discount {
    private Double percentage;

    @Override
    public boolean isApplicable(Item item) {
        return percentage != 0 && item != null && item.getQuantity() != 0;
    }
}
