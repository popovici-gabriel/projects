package com.price.pricebasket.domain;

import com.price.pricebasket.inventory.ItemIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SameItemDiscountTest {

    Item referenceItem;

    @BeforeEach
    public void setUp() {
        referenceItem = Item
                .builder()
                .quantity(2)
                .product(Product
                        .builder()
                        .id(ItemIdentifier.APPLES.identifier())
                        .name(ItemIdentifier.APPLES.identifier())
                        .build())
                .build();
    }

    @Test
    @DisplayName("Should not be applicable for empty item")
    public void shouldNotBeApplicable() {
        Assertions.assertFalse(new SameItemDiscount(referenceItem, 0.5).isApplicable(null));
    }


    @Test
    @DisplayName("Should be applicable for referenceItem")
    public void shouldBeApplicable() {
        Assertions.assertTrue(new SameItemDiscount(referenceItem, 0.5).isApplicable(referenceItem));
    }

    @Test
    public void shouldNotReferenceOtherItems() {
        Assertions.assertFalse(new SameItemDiscount(referenceItem, 0.5).hasPromotions());
    }

}