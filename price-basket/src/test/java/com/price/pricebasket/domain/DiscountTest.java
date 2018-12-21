package com.price.pricebasket.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountTest {


    @Test
    @DisplayName("should apply correct discount")
    public void shouldApplyDiscount() {
        assertEquals(new BigDecimal("47.20"), Discount.applyPercentage(new BigDecimal("59"), new BigDecimal("0.2")));
        assertEquals(new BigDecimal("0.90"), Discount.applyPercentage(BigDecimal.ONE, new BigDecimal("0.1")));
    }
}