package com.price.pricebasket.domain;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static com.price.pricebasket.domain.Discount.apply;
import static com.price.pricebasket.domain.Discount.defaultScale;
import static com.price.pricebasket.domain.Discount.totalDiscount;
import static java.lang.Character.toUpperCase;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Currency.getInstance;
import static java.util.Locale.UK;

@Slf4j
public class Invoice {

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
        startTime = System.nanoTime();
    }

    /**
     * Stop timing the test run.
     */
    private static void stopTiming() {
        executionTime = (System.nanoTime() - startTime) / 1_000_000;
    }

    public void generate(Basket basket) {
        log.debug("Basket created at {}", basket.getCreated());
        startTiming();
        generateReport(basket);
        stopTiming();
        log.debug("Took {} seconds to generate", executionTime);
    }

    private void generateReport(Basket basket) {
        BigDecimal subTotal = subTotal(basket);
        log.info("SubTotal: {}{}", currencySymbol(), defaultScale(subTotal));

        BigDecimal total = total(basket);
        if (hasNoDeals(basket)) {
            log.info("(no offers available)");
        }
        log.info("Total: {}{}", currencySymbol(), defaultScale(total));
    }

    BigDecimal total(Basket basket) {
        return basket
                .getItems()
                .stream()
                .map(item -> {
                    if (hasPromotionDiscount(item)) {
                        return item
                                .getDiscount()
                                .getItem()
                                .map(discountItem ->
                                        findByProductId(basket, discountItem.getProduct().getId())
                                                .map(referencedItem -> {
                                                    if (item.getQuantity().equals(referencedItem.getQuantity())) {
                                                        log.info("{} {}% off: -{}{}",
                                                                capitalize(item.getProduct().getName()),
                                                                toInt(item.getDiscount().getPercentage()),
                                                                currencySymbol(),
                                                                totalDiscount(item.getQuantity(), item.getPrice(), valueOf(item.getDiscount().getPercentage())));
                                                        return apply(item.getQuantity(), item.getPrice(), valueOf(item.getDiscount().getPercentage()));
                                                    }
                                                    return priceItem(item);
                                                })
                                                .orElseGet(() -> priceItem(item))
                                )
                                .orElseGet(() -> priceItem(item));
                    }

                    if (hasDiscountAndApplicableForItem(item)) {
                        log.info("{} {}% off: -{}{}",
                                capitalize(item.getProduct().getName()),
                                toInt(item.getDiscount().getPercentage()),
                                currencySymbol(),
                                totalDiscount(item.getQuantity(), item.getPrice(), valueOf(item.getDiscount().getPercentage())));
                        return apply(item.getQuantity(), item.getPrice(), valueOf(item.getDiscount().getPercentage()));
                    }

                    return priceItem(item);
                })
                .peek(total -> log.debug("Collected value:{}", total))
                .reduce(ZERO, BigDecimal::add);
    }

    BigDecimal subTotal(Basket basket) {
        return basket
                .getItems()
                .stream()
                .filter(item -> null != item.getPrice())
                .filter(item -> null != item.getQuantity())
                .map(Invoice::priceItem)
                .reduce(ZERO, BigDecimal::add);
    }

    boolean hasNoDeals(Basket basket) {
        return basket
                .getItems()
                .stream()
                .map(Item::getDiscount)
                .allMatch(Objects::isNull);
    }

    Optional<Item> findByProductId(Basket basket, String id) {
        return basket
                .getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(id))
                .findFirst();
    }


    private boolean hasPromotionDiscount(Item item) {
        return null != item.getDiscount() && item.getDiscount().hasPromotions();
    }

    private boolean hasDiscountAndApplicableForItem(Item item) {
        return null != item.getDiscount() && item.getDiscount().isApplicable(item);
    }

    private static String currencySymbol() {
        return getInstance(UK).getSymbol();
    }

    private static Integer toInt(Double percentage) {
        return ((Double) (percentage * 100)).intValue();
    }

    private static String capitalize(String word) {
        return toUpperCase(word.charAt(0)) + word.substring(1);
    }

    private static BigDecimal priceItem(Item item) {
        return defaultScale(item.getPrice().multiply(valueOf(item.getQuantity())));
    }
}
