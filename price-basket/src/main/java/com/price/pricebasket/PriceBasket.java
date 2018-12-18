package com.price.pricebasket;

import lombok.extern.java.Log;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Log
public class PriceBasket {
    public static void main(String[] args) {
        final SupplierProduct supplierProduct = loadInventory();
        List<Item> itemList = Stream
                .of(args)
                .map(String::toLowerCase)
                .peek(log::info)
                .map(ItemType::itemType)
                .peek(itemType -> log.fine(format("Found item type %s", itemType)))
                .map(itemType -> supplierProduct.getSupplier(itemType).get())
                .peek(item -> log.fine(item.toString()))
                .collect(Collectors.toList());

        Basket basket = new Basket(itemList);
        log.info(format("Basket create at %s contains %d item", basket.getCreated(), basket.getItemList().size()));
    }

    private static SupplierProduct loadInventory() {
        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.loadInventory();
        return supplierProduct;
    }
}
