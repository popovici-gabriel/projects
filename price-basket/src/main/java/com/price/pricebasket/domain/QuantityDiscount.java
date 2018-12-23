package com.price.pricebasket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

import static java.util.Optional.empty;

@Data
@AllArgsConstructor
public class QuantityDiscount implements Discount {

    private Integer quantity;

    @Override
    public boolean isApplicable(Item item) {
        return quantity != 0 && item != null && quantity == item.getQuantity();
    }

    @Override
    public Double getPercentage() {
        return null;
    }

    @Override
    public Optional<Item> getItem() {
        return empty();
    }
}
