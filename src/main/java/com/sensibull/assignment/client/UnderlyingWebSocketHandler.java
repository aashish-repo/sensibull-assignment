package com.sensibull.assignment.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensibull.assignment.models.ClientSubscribeMessage;
import com.sensibull.assignment.models.ServerMessageResponse;
import com.sensibull.assignment.repository.UnderlyingPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


@Component
public class UnderlyingWebSocketHandler implements WebSocketHandler {

    private static ClientSubscribeMessage clientMessage;

    @Autowired
    private UnderlyingPriceRepository underlyingPriceRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected To Websocket");
        session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(clientMessage)));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        TextMessage textMessage = (TextMessage) message;
        if(textMessage.getPayload().contains("ping")){
            //Adding this condition now, because object mapper is failing while deserializing the ping object
            return;
        }
        ServerMessageResponse messageResponse = new ObjectMapper().enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT).readValue(textMessage.getPayload(), ServerMessageResponse.class);
        underlyingPriceRepository.updatePriceForToken(messageResponse.getPayload().getToken(), messageResponse.getPayload().getPrice());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("WebSocket Connection Closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public static void setClientMessage(ClientSubscribeMessage clientSubscribeMessage) {
        clientMessage = clientSubscribeMessage;
    }
}
