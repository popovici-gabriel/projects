package com.price.pricebasket.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuantityDiscountTest {


    @Test
    @DisplayName("Should not be applicable for empty item")
    public void shouldNotBeApplicable() {
        Assertions.assertFalse(new QuantityDiscount(1).isApplicable(null));
    }


    @Test
    @DisplayName("Should be applicable for item")
    public void shouldBeApplicable() {
        Assertions.assertTrue(new QuantityDiscount(1).isApplicable(Item.builder().quantity(1).build()));
    }


}