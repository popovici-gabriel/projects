package com.price.pricebasket.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.price.pricebasket.inventory.ProductInventory.applesItem;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InvoiceTest {

    Invoice invoice;

    @BeforeEach
    public void setUp() {
        invoice = new Invoice();
    }

    @Test
    @DisplayName("Has deals for Basket containing Apples")
    public void hasDealsForApples() {
        assertFalse(invoice.hasNoDeals(createBasket()));
    }

    @Test
    @DisplayName("Just apples in basket has given sub total")
    public void hasSubtotalApples() {
        Assertions.assertEquals(new BigDecimal("1.00"), invoice.subTotal(createBasket()));
    }

    @Test
    @DisplayName("Just apples in basket has given total")
    public void hasTotalApples() {
        Assertions.assertEquals(new BigDecimal("0.90"), invoice.total(createBasket()));
    }

    @Test
    public void shouldGenerateReportWithNoErrors() {
        invoice.generate(createBasket());
    }

    public static Basket createBasket() {
        Set<Item> itemSet = new HashSet<>();
        itemSet.add(applesItem());
        return new Basket(itemSet);
    }
}