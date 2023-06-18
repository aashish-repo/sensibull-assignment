package com.sensibull.assignment.models;

import lombok.Data;

import java.util.List;

@Data
public class ServerMessageResponse {
    private String data_type;
    private List<TokenPrice> payload;
}
