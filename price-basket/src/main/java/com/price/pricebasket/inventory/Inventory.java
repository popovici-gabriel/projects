package com.price.pricebasket.inventory;

import com.price.pricebasket.domain.Item;

public interface Inventory {
    void addItem(Item item);

    Item getItem(String id);

    void loadDefaultInventory();

    int size();

    void clear();
}
