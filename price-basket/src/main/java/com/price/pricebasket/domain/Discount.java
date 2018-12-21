package com.price.pricebasket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;


public interface Discount {
    
    BiFunction<BigDecimal, BigDecimal, BigDecimal> DISCOUNT_PERCENTAGE_FUNCTION = (price, percentage) -> price.subtract(price.multiply(percentage));

    static BigDecimal applyPercentage(BigDecimal price, BigDecimal percentage) {
        return defaultScale(DISCOUNT_PERCENTAGE_FUNCTION.apply(price, percentage));
    }

    static BigDecimal defaultScale(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

    boolean isApplicable(Item item);
}
