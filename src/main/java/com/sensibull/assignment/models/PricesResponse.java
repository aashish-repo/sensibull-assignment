package com.sensibull.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricesResponse {
    private String symbol;
    private String underlying;
    private Integer token;
    private String instrument_type;
    private String expiry;
    private Integer strike;
    private Double price;
}
