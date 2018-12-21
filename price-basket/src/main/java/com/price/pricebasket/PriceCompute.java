package com.price.pricebasket;

import com.price.pricebasket.domain.Basket;
import com.price.pricebasket.domain.Item;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static com.price.pricebasket.domain.Discount.defaultScale;
import static java.lang.String.format;
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
        log.debug(format("Basket create at %s contains %d item", basket.getCreated(), basket.getItemList().size()));
        startTiming();
        BigDecimal total = basket
                .getItemList()
                .stream()
                .filter(item -> null != item.getPrice())
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stopTiming();

        log.info("Total: {}{}", currencySymbol(), defaultScale(total));
        log.info("Took {} seconds to compute", executionTime);
    }

    static String currencySymbol() {
        return getInstance(UK).getSymbol();
    }
}
