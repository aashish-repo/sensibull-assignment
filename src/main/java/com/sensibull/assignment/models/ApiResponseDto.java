package com.sensibull.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor@AllArgsConstructor
public class ApiResponseDto {

    private String success;
    private List<PricesResponse> payload;

}
