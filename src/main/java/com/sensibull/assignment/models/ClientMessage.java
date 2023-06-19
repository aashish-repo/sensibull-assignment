package com.sensibull.assignment.models;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientMessage {
    private String msg_command;
    private String data_type;
    private List<Integer> tokens;
}
