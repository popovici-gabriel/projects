package com.example.coding01.stock;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// POCs
@RepositoryRestResource(path = "/stocks1")
public interface StockPocResource extends PagingAndSortingRepository<Stock, Long> {
}
