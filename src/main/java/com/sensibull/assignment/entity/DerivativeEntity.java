package com.sensibull.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "derivative_stock_price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DerivativeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String symbol;
    private String underlying;
    private Integer derivative_token;
    private Integer underlying_token;
    private String instrument_type;
    private String expiry;
    private Integer strike;
    private Double price;
}
