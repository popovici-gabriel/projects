package com.price.pricebasket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuantityDiscount implements Discount {

    private Integer quantity;

    @Override
    public boolean isApplicable(Item item) {
        return quantity != 0 && item != null && quantity == item.getQuantity();
    }
}
