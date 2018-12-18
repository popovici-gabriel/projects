package com.price.pricebasket;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.price.pricebasket.ItemType.*;

public class SupplierProduct {

    private Map<ItemType, Supplier<Item>> itemInventory = new LinkedHashMap<>();

    public void addItem(ItemType itemType, Supplier<Item> supplier) {
        itemInventory.putIfAbsent(itemType, supplier);
    }

    Supplier<Item> getSupplier(ItemType itemType) {
        return Optional
                .ofNullable(itemInventory.get(itemType))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s not yet associated", itemType.getName())));
    }

    void loadInventory() {
        itemInventory.putIfAbsent(SOUP, () -> Item
                .builder()
                .price(BigDecimal.valueOf(0.65d))
                .quantity(1)
                .product(Product
                        .builder()
                        .id(SOUP.getName())
                        .name(SOUP.getName())
                        .build())
                .build());

        itemInventory.putIfAbsent(BREAD, () -> Item
                .builder()
                .price(BigDecimal.valueOf(0.80d))
                .quantity(1)
                .product(Product
                        .builder()
                        .id(BREAD.getName())
                        .name(BREAD.getName())
                        .build())
                .build());

        itemInventory.putIfAbsent(MILK, () -> Item
                .builder()
                .price(BigDecimal.valueOf(1.30d))
                .quantity(1)
                .product(Product
                        .builder()
                        .id(MILK.getName())
                        .name(MILK.getName())
                        .build())
                .build());

        itemInventory.putIfAbsent(APPLES, () -> Item
                .builder()
                .price(BigDecimal.ONE)
                .quantity(1)
                .product(Product
                        .builder()
                        .id(APPLES.getName())
                        .name(APPLES.getName())
                        .build())
                .build());

    }

}
