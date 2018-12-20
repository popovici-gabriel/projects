package com.price.pricebasket.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferenceItemDiscount implements Discount {
    private Item item;

    @Override
    public boolean isApplicable(Item item) {
        return this.item != null
                && item != null
                && this.item.getProduct().equals(item.getProduct());
    }
}
