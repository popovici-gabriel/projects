package com.price.pricebasket.inventory;

import com.price.pricebasket.domain.Item;
import com.price.pricebasket.domain.Product;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.price.pricebasket.inventory.ItemIdentifier.*;

public class ProductInventory implements Inventory {

    private Map<String, Item> itemInventory = new LinkedHashMap<>();

    @Override
    public void addItem(Item item) {
        itemInventory.putIfAbsent(item.getProduct().getId(), item);
    }

    @Override
    public Item getItem(String id) {
        return Optional
                .ofNullable(itemInventory.get(id))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s not yet associated", id)));
    }

    @Override
    public void loadDefaultInventory() {
        addItem(soupItem());
        addItem(breadItem());
        addItem(milkItem());
        addItem(applesItem());
    }

    @Override
    public void clear() {
        itemInventory.clear();
    }

    @Override
    public int size() {
        return itemInventory.size();
    }

    static Item applesItem() {
        return Item
                .builder()
                .price(BigDecimal.ONE)
                .quantity(1)
                .product(Product
                        .builder()
                        .id(APPLES.identifier())
                        .name(APPLES.identifier())
                        .build())
                .build();
    }

    static Item milkItem() {
        return Item
                .builder()
                .price(BigDecimal.valueOf(1.30d))
                .quantity(1)
                .product(Product
                        .builder()
                        .id(MILK.identifier())
                        .name(MILK.identifier())
                        .build())
                .build();
    }

    static Item breadItem() {
        return Item
                .builder()
                .price(BigDecimal.valueOf(0.80d))
                .quantity(1)
                .product(Product
                        .builder()
                        .id(BREAD.identifier())
                        .name(BREAD.identifier())
                        .build())
                .build();
    }

    static Item soupItem() {
        return Item
                .builder()
                .price(BigDecimal.valueOf(0.65d))
                .quantity(1)
                .product(Product
                        .builder()
                        .id(SOUP.identifier())
                        .name(SOUP.identifier())
                        .build())
                .build();
    }

}
