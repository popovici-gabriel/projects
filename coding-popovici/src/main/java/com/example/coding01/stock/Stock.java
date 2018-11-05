package com.example.coding01.stock;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@ToString
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private LocalDate valueDate;

    private Float open;

    private Float high;

    private Float low;

    private Float close;

    private Float adjustedClose;

    private Integer volume;
}
