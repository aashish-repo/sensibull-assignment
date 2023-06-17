package com.sensibull.assignment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class StockEntity {
    @Id
    @GeneratedValue
    private Long id;
    //will use symbol as identification for underlying and derivatives
    private String symbol;
    private String underlying;
    private Integer token;
    private String instrument_type;
    private String expiry;
    private Integer strike;
    private Double price;
}
