package com.price.pricebasket.domain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.BiFunction;

public interface Discount {

    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.UK));

    BiFunction<Double, Double, Double> PERCENTILE_FUNCTION =
            (price, percentage) -> price - (price * percentage);

    static double format(double number) {
        synchronized (DECIMAL_FORMAT) {
            return new Double(DECIMAL_FORMAT.format(number));
        }
    }

    static Double applyPercentage(Double price, Double percentage) {
        return format(PERCENTILE_FUNCTION.apply(price, percentage));
    }

    boolean isApplicable(Item item);

}
