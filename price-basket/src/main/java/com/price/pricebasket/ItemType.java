package com.price.pricebasket;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public enum ItemType {

    APPLES("apples"),
    SOUP("soup"),
    MILK("milk"),
    BREAD("bread");

    private String name;

    private static final Map<String, ItemType> ITEM_TYPE_LOOKUP = Arrays
            .stream(values())
            .collect(toMap(ItemType::getName, Function.identity()));

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ItemType itemType(String name) {
        return Optional
                .ofNullable(ITEM_TYPE_LOOKUP.get(name))
                .orElseThrow(() -> new IllegalArgumentException("Item not available in model"));
    }
}
