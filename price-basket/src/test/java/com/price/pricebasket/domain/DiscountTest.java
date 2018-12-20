package com.price.pricebasket.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountTest {


    @Test
    @DisplayName("should apply correct discount")
    public void shouldApplyDiscount() {
        assertTrue(47.2d == Discount.applyPercentage(59.0d, 0.2));
        assertEquals(0.9d, Discount.applyPercentage(1.0d, 0.1).doubleValue());
    }
}