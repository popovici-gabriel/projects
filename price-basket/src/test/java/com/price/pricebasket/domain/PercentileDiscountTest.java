package com.price.pricebasket.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentileDiscountTest {

    @Test
    @DisplayName("Should not be applicable for empty item")
    public void shouldNotBeApplicable() {
        Assertions.assertFalse(((Discount) new PercentileDiscount(0.25d)).isApplicable(null));
    }


    @Test
    @DisplayName("Should be applicable for item")
    public void shouldBeApplicable() {
        Assertions.assertTrue(new PercentileDiscount(0.25d).isApplicable(Item.builder().quantity(1).build()));
    }
}