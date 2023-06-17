package com.sensibull.assignment.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientSubscribeMessageModel {
    private String msg_command;
    private String data_type;
    private List<Integer> tokens;
}
