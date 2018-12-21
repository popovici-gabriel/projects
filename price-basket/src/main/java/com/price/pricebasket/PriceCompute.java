package com.price.pricebasket;

import com.price.pricebasket.domain.Basket;
import com.price.pricebasket.domain.Discount;
import com.price.pricebasket.domain.Item;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static com.price.pricebasket.domain.Discount.defaultScale;
import static com.price.pricebasket.domain.Discount.discount;
import static java.math.BigDecimal.ZERO;
import static java.util.Currency.getInstance;
import static java.util.Locale.UK;

@Slf4j
class PriceCompute {

    /**
     * Keeps track of how long the test has run.
     */
    private static long startTime;

    /**
     * Keeps track of the execution time.
     */
    private static long executionTime;

    /**
     * Start timing the test run.
     */
    private static void startTiming() {
        // Note the start time.
        startTime = System.nanoTime();
    }

    /**
     * Stop timing the test run.
     */
    private static void stopTiming() {
        executionTime = (System.nanoTime() - startTime) / 1_000_000;
    }

    void compute(Basket basket) {
        log.debug("Basket created at {}", basket.getCreated());
        startTiming();

        BigDecimal subTotal = basket
                .getItemList()
                .stream()
                .filter(item -> null != item.getPrice())
                .map(Item::getPrice)
                .reduce(ZERO, BigDecimal::add);

        log.info("SubTotal: {}{}", currencySymbol(), defaultScale(subTotal));

        BigDecimal total = basket
                .getItemList()
                .stream()
                .map(item -> {
                    if (null != item.getDiscount() && item.getDiscount().isApplicable(item)) {
                        log.info("{} {}% off: -{}", 
                                item.getProduct().getName(), 
                                toInt(item.getDiscount().getPercentage()),
                                discount(item.getPrice(),BigDecimal.valueOf(item.getDiscount().getPercentage())));
                        return Discount.applyPercentage(item.getPrice(), BigDecimal.valueOf(item.getDiscount().getPercentage()));
                    }
                    return item.getPrice();
                })
                .reduce(ZERO, BigDecimal::add);

        log.info("Total: {}{}", currencySymbol(), defaultScale(total));

        stopTiming();

        log.info("Took {} seconds to compute", executionTime);
    }

    static String currencySymbol() {
        return getInstance(UK).getSymbol();
    }

    static Integer toInt(Double percentage) {
        return ((Double) (percentage * 100)).intValue();
    }
}
