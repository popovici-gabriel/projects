package com.price.pricebasket;

import com.price.pricebasket.domain.Basket;
import com.price.pricebasket.domain.Item;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static java.lang.String.format;

@Slf4j
class PriceCompute {

    private static final char POUND = 163;

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
        Double total = basket
                .getItemList()
                .stream()
                .map(Item::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        stopTiming();

        log.info("Total: {}{}", POUND, Numeric.format(total));
        log.info("Took {} seconds to compute", executionTime);
    }
}
