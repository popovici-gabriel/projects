package com.price.pricebasket.inventory;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public enum ItemIdentifier {

    NA("Not Available"),
    APPLES("apples"),
    SOUP("soup"),
    MILK("milk"),
    BREAD("bread");

    private String identifier;

    private static final Map<String, ItemIdentifier> ITEM_TYPE_LOOKUP = Arrays
            .stream(values())
            .collect(toMap(ItemIdentifier::identifier, Function.identity()));

    ItemIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

    public static ItemIdentifier itemIdentifier(String identifier) {
        return Optional
                .ofNullable(ITEM_TYPE_LOOKUP.get(identifier))
                .orElseThrow(() -> new IllegalArgumentException("Item not available in model"));
    }
}
