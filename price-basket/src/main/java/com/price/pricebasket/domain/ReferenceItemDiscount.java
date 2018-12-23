package com.price.pricebasket.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferenceItemDiscount implements Discount {

    private Item item;

    private Double percentage;

    @Override
    public boolean isApplicable(Item item) {
        return this.item != null && item != null;
    }

    public Double getPercentage() {
        return percentage;
    }

}
