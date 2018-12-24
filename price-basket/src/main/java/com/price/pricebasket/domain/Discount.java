package com.price.pricebasket.domain;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_EVEN;


public interface Discount {


    Function<Integer, Function<BigDecimal, Function<BigDecimal, BigDecimal>>> TOTAL_PRICE_FUNCTION =
            quantity -> price -> percentage -> totalAmount(valueOf(quantity), price, percentage);

    static BigDecimal apply(Integer quantity, BigDecimal price, BigDecimal percentage) {
        return defaultScale(TOTAL_PRICE_FUNCTION.apply(quantity).apply(price).apply(percentage));
    }

    static BigDecimal totalDiscount(Integer quantity, BigDecimal price, BigDecimal percentage) {
        return defaultScale(valueOf(quantity).multiply(price).multiply(percentage));
    }

    static BigDecimal totalAmount(BigDecimal quantity, BigDecimal unitPrice, BigDecimal discountRate) {
        BigDecimal amount = quantity.multiply(unitPrice);
        BigDecimal discount = amount.multiply(discountRate);
        BigDecimal discountedAmount = amount.subtract(discount);
        return defaultScale(discountedAmount);
    }

    static BigDecimal defaultScale(BigDecimal price) {
        return price.setScale(2, HALF_EVEN);
    }

    boolean isApplicable(Item item);

    Double getPercentage();

    default boolean hasPromotions() {
        return false;
    }

    Optional<Item> getItem();
}
