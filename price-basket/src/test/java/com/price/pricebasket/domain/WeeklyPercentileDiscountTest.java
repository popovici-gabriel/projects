package com.price.pricebasket.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.price.pricebasket.domain.WeeklyPercentileDiscount.currentMonday;
import static com.price.pricebasket.domain.WeeklyPercentileDiscount.currentSunday;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeeklyPercentileDiscountTest {

    @Test
    @DisplayName("Should not be applicable for empty item")
    public void shouldNotBeApplicable() {
        assertFalse(new WeeklyPercentileDiscount(0.1, currentMonday(), currentSunday()).isApplicable(null));
    }


    @Test
    @DisplayName("Should be applicable for item")
    public void shouldBeApplicable() {
        assertTrue(new WeeklyPercentileDiscount(0.1, currentMonday(), currentSunday()).isApplicable(Item.builder().quantity(1).build()));
    }

}