package com.price.pricebasket.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;


public interface Discount {


    Function<Integer, Function<BigDecimal, Function<BigDecimal, BigDecimal>>> TOTAL_PRICE_FUNCTION =
            quantity -> price -> percentage -> price.multiply(new BigDecimal(quantity)).subtract(discount(price, percentage));

    static BigDecimal applyPercentage(Integer quantity, BigDecimal price, BigDecimal percentage) {
        return defaultScale(TOTAL_PRICE_FUNCTION.apply(quantity).apply(price).apply(percentage));
    }

    static BigDecimal discount(BigDecimal price, BigDecimal percentage) {
        return defaultScale(price.multiply(percentage));
    }

    static BigDecimal defaultScale(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

    boolean isApplicable(Item item);

    Double getPercentage();
}
