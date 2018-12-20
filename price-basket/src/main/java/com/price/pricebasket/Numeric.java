package com.price.pricebasket;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class Numeric {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.UK));

    private Numeric() {
        super();
    }

    public static double format(double number) {
        synchronized (DECIMAL_FORMAT) {
            return new Double(DECIMAL_FORMAT.format(number));
        }
    }
}
