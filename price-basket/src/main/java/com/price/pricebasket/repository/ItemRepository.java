package com.price.pricebasket.repository;

import com.price.pricebasket.domain.Item;

public interface ItemRepository {
    void save(Item item);

    Item findByProductId(String id);

    void removeAll();
}
