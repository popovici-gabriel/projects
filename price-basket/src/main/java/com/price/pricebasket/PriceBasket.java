package com.price.pricebasket;

import com.price.pricebasket.domain.Basket;
import com.price.pricebasket.domain.Invoice;
import com.price.pricebasket.domain.Item;
import com.price.pricebasket.inventory.Inventory;
import com.price.pricebasket.inventory.ItemIdentifier;
import com.price.pricebasket.inventory.ProductInventory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Slf4j
public class PriceBasket {

    public static void main(String[] args) {
        if (0 == args.length) {
            throw new IllegalArgumentException("No items provided");
        }

        final Inventory inventory = getInventory();
        inventory.loadDefaultInventory();

        Map<Item, Long> groupByQuantity = Stream
                .of(args)
                .map(String::toLowerCase)
                .peek(log::debug)
                .map(ItemIdentifier::itemIdentifier)
                .peek(itemType -> log.debug(format("Found item type %s", itemType)))
                .map(itemType -> inventory.getItem(itemType.identifier()))
                .peek(item -> log.debug(item.toString()))
                .collect(groupingBy(identity(), counting()));

        Set<Item> uniqueItems = groupByQuantity
                .entrySet()
                .stream()
                .map(PriceBasket::mapByQuantity)
                .collect(toSet());

        new Invoice().generate(new Basket(uniqueItems));
    }

    private static Inventory getInventory() {
        return new ProductInventory();
    }


    private static Item mapByQuantity(Map.Entry<Item, Long> itemCount) {
        Item item = itemCount.getKey();
        Long quantity = itemCount.getValue();
        return Item
                .builder()
                .quantity(quantity.intValue())
                .price(item.getPrice())
                .product(item.getProduct())
                .discount(item.getDiscount())
                .build();
    }
}
