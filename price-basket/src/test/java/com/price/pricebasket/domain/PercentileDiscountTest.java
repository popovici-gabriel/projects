package com.price.pricebasket.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PercentileDiscountTest {

    @Test
    @DisplayName("Should not be applicable for empty item")
    public void shouldNotBeApplicable() {
        Assertions.assertFalse(new PercentileDiscount(new BigDecimal("0.25")).isApplicable(null));
    }


    @Test
    @DisplayName("Should be applicable for item")
    public void shouldBeApplicable() {
        Assertions.assertTrue(new PercentileDiscount(new BigDecimal("0.25")).isApplicable(Item.builder().quantity(1).build()));
    }
}