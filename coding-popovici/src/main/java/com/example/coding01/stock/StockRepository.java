package com.example.coding01.stock;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findBySymbolAndValueDate(String symbol, LocalDate valueDate);
}
