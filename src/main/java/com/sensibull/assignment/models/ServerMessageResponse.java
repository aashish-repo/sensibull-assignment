package com.sensibull.assignment.models;

import lombok.Data;

@Data
public class ServerMessageResponse {
    private String data_type;
    private TokenPrice payload;
}
