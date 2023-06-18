package com.sensibull.assignment.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class ClientMessage {
    private String msg_command;
    private String data_type;
    private List<Integer> tokens;
}
