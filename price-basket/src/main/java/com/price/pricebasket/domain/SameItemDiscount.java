package com.price.pricebasket.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class SameItemDiscount implements Discount {

    private Item item;

    private Double percentage;

    @Override
    public boolean isApplicable(Item item) {
        return this.item != null
                && item != null
                && this.item.getProduct().equals(item.getProduct());
    }

    public Double getPercentage() {
        return percentage;
    }

    @Override
    public Optional<Item> getItem() {
        return Optional.of(item);
    }

}
