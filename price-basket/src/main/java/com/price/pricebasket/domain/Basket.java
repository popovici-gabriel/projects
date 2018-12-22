package com.price.pricebasket.domain;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Basket {

    private final Set<Item> items;
    private final Date created;

    public Basket(Set<Item> items) {
        this.items = items;
        this.created = new Date();
    }

    public Basket() {
        this(new HashSet<>());
    }

    public Set<Item> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }
}
