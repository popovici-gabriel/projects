package com.price.pricebasket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalDate.now;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@Data
@AllArgsConstructor
public class WeeklyPercentileDiscount implements Discount {

    private Double percentage;

    private LocalDate monday;

    private LocalDate sunday;

    @Override
    public boolean isApplicable(Item item) {
        return null != percentage
                && null != item
                && 0 != item.getQuantity()
                && now().isAfter(monday)
                && now().isBefore(sunday);
    }


    public static LocalDate currentMonday() {
        return now().with(previousOrSame(MONDAY));
    }

    public static LocalDate currentSunday() {
        return now().with(nextOrSame(SUNDAY));
    }

    public Double getPercentage() {
        return percentage;
    }
}
