package com.price.pricebasket.domain;

import com.price.pricebasket.Numeric;

import java.util.function.BiFunction;

public interface Discount {

    BiFunction<Double, Double, Double> PERCENTILE_FUNCTION =
            (price, percentage) -> price - (price * percentage);

    static Double applyPercentage(Double price, Double percentage) {
        return Numeric.format(PERCENTILE_FUNCTION.apply(price, percentage));
    }

    boolean isApplicable(Item item);
}
