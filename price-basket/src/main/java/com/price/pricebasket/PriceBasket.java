package com.price.pricebasket;

import lombok.extern.java.Log;

import java.util.stream.Stream;

@Log
public class PriceBasket {
    public static void main(String[] args) {

        Stream
                .of(args)
                .forEach(log::info);
    }
}
