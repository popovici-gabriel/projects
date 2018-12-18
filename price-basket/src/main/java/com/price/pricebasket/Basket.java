package com.price.pricebasket;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Basket {

    private final List<Item> itemList;
    private final Date created;

    public Basket(List<Item> itemList) {
        this.itemList = itemList;
        this.created = new Date();
    }

    public Basket() {
        this(new LinkedList<>());
    }

    public List<Item> getItemList() {
        return Collections.unmodifiableList(itemList);
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }
}
