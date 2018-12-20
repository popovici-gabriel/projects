package com.price.pricebasket.domain;

import com.price.pricebasket.inventory.ItemIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReferenceItemDiscountTest {

    Item referenceItem;

    @BeforeEach
    public void setUp() {
        referenceItem = Item
                .builder()
                .quantity(1)
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
        Assertions.assertFalse(new ReferenceItemDiscount(referenceItem).isApplicable(null));
    }


    @Test
    @DisplayName("Should be applicable for referenceItem")
    public void shouldBeApplicable() {
        Assertions.assertTrue(new ReferenceItemDiscount(referenceItem).isApplicable(referenceItem));
    }

}