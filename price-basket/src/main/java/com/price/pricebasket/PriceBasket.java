package com.price.pricebasket;

import com.price.pricebasket.domain.Basket;
import com.price.pricebasket.domain.Item;
import com.price.pricebasket.inventory.Inventory;
import com.price.pricebasket.inventory.ItemIdentifier;
import com.price.pricebasket.inventory.ProductInventory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Slf4j
public class PriceBasket {

    public static void main(String[] args) {
        if (0 == args.length) {
            throw new IllegalArgumentException("Error pricing goods. No items provided");
        }

        final Inventory inventory = getInventory();
        inventory.loadDefaultInventory();

        List<Item> itemList = Stream
                .of(args)
                .map(String::toLowerCase)
                .peek(log::info)
                .map(ItemIdentifier::itemIdentifier)
                .peek(itemType -> log.debug(format("Found item type %s", itemType)))
                .map(itemType -> inventory.getItem(itemType.identifier()))
                .peek(item -> log.debug(item.toString()))
                .collect(Collectors.toList());

        Basket basket = new Basket(itemList);
        log.info(format("Basket create at %s contains %d item", basket.getCreated(), basket.getItemList().size()));
    }

    private static Inventory getInventory() {
        return new ProductInventory();
    }


}
