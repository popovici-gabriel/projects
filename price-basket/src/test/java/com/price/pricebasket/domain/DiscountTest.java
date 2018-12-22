package com.price.pricebasket.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.price.pricebasket.domain.Discount.apply;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountTest {


    @Test
    @DisplayName("should apply correct discount")
    public void shouldApplyDiscount() {
        assertEquals(new BigDecimal("47.20"), apply(1, new BigDecimal("59"), valueOf(0.2)));
        assertEquals(new BigDecimal("0.90"), apply(1, BigDecimal.ONE, valueOf(0.1)));
        assertEquals(new BigDecimal("1.80"), apply(2, BigDecimal.ONE, valueOf(0.1)));
    }
}