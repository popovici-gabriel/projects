
package com.price.pricebasket.domain;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class WeeklyPercentileDiscount implements Discount {

    private Double percentage;

    private LocalDate monday;

    private LocalDate sunday;


    public static LocalDate previousMonday() {
        return now().with(previousOrSame(MONDAY));
    }

    public static LocalDate previousSunday() {
        return now().with(previousOrSame(SUNDAY));
    }

    public static LocalDate currentSunday() {
        return now().with(nextOrSame(SUNDAY));
    }

    @Override
    public boolean isApplicable(Item item) {
        return (null != percentage) 
                && (null != item) 
                && (0 != item.getQuantity()) 
                && (now().isAfter(monday) || now().isBefore(sunday));
    }

    public Double getPercentage() {
        return percentage;
    }
}
