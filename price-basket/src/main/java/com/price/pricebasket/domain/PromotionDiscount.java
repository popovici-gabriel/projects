package com.price.pricebasket.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class PromotionDiscount implements Discount {

    private Item item;

    private Double percentage;

    @Override
    public boolean isApplicable(Item item) {
        return this.item != null
                && item != null;
    }

    public Double getPercentage() {
        return percentage;
    }

    @Override
    public Optional<Item> getItem() {
        return Optional.of(item);
    }

    @Override
    public boolean hasPromotions() {
        return true;
    }
}
