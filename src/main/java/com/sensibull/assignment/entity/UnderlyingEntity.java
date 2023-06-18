package com.sensibull.assignment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "underlying_stock_price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderlyingEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String symbol;
    private String underlying;
    private Integer token;
    private String instrument_type;
    private String expiry;
    private Integer strike;
    private Double price;
}
