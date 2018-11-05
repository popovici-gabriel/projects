package com.example.coding01.stock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestController
public class StockResource {

    private final StockRepository stockRepository;

    public StockResource(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @GetMapping(path = "stock/get/{SYMBOL}/{DATE}")
    public Stock findBySymbolAndDate(@PathVariable("SYMBOL") String symbol,
                                     @PathVariable("DATE") String date) {
        Objects.requireNonNull(symbol);
        Objects.requireNonNull(date);

        return stockRepository.findBySymbolAndValueDate(symbol, LocalDate.parse(date, DateTimeFormatter.ISO_DATE));
    }
}
