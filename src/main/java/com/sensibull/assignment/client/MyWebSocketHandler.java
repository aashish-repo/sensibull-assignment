package com.sensibull.assignment.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensibull.assignment.models.ClientSubscribeMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Arrays;

@Component
public class MyWebSocketHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected To Websocket");
        ClientSubscribeMessage message = ClientSubscribeMessage.builder().msg_command("subscribe").data_type("quote").tokens(Arrays.asList(4849248)).build();
        session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(message)));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
