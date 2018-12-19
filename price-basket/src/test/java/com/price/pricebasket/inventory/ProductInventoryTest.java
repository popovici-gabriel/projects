package com.price.pricebasket.inventory;

import com.price.pricebasket.domain.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.price.pricebasket.inventory.ItemIdentifier.APPLES;
import static com.price.pricebasket.inventory.ItemIdentifier.NA;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductInventoryTest {


    ProductInventory productInventory;

    @BeforeEach
    public void setUp() {
        productInventory = new ProductInventory();
    }

    @AfterEach
    public void cleanUp() {
        productInventory.clear();
        productInventory = null;
    }


    @Test
    @DisplayName("Should verify default inventory size")
    public void testDefaultInventorySize() {
        // act
        productInventory.loadDefaultInventory();
        // assert
        assertSame(4, productInventory.size());
    }

    @Test
    @DisplayName("Should test Bad Argument Error test case ")
    public void testIllegalArgumentException() {
        // act
        productInventory.loadDefaultInventory();
        // assert
        assertThrows(IllegalArgumentException.class, () -> productInventory.getItem(NA.identifier()));
    }

    @Test
    @DisplayName("Should test adding new items")
    public void testAddItem() {
        //arrange
        Item apples = ProductInventory.applesItem();
        // act
        productInventory.addItem(apples);
        // assert
        assertSame(apples, productInventory.getItem(APPLES.identifier()));
    }

}