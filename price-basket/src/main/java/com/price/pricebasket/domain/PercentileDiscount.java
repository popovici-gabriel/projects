package com.price.pricebasket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

import static java.util.Optional.empty;

@Data
@AllArgsConstructor
public class PercentileDiscount implements Discount {

    private Double percentage;

    @Override
    public boolean isApplicable(Item item) {
        return percentage != null
                && item != null
                && item.getQuantity() != 0;
    }


    public Double getPercentage() {
        return percentage;
    }

    @Override
    public Optional<Item> getItem() {
        return empty();
    }
}
