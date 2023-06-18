package com.sensibull.assignment.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensibull.assignment.models.ClientMessage;
import com.sensibull.assignment.models.ServerMessageResponse;
import com.sensibull.assignment.repository.DerivativePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class DerivativeWebSocketHandler implements WebSocketHandler {

    private static ClientMessage clientMessage;
    private static ClientMessage clientMessageUnsubscribe;

    @Autowired
    private DerivativePriceRepository derivativePriceRepository;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected To Websocket");
        if(clientMessageUnsubscribe!=null){
            clientMessageUnsubscribe.setMsg_command("unsubscribe");
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(clientMessageUnsubscribe)));
        }
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
        derivativePriceRepository.updatePriceForToken(messageResponse.getPayload().getToken(), messageResponse.getPayload().getPrice());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("WebSocket Connection Closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    public static void setClientMessage(ClientMessage clientMessage) {
        if(DerivativeWebSocketHandler.clientMessage!=null){
            DerivativeWebSocketHandler.clientMessageUnsubscribe = DerivativeWebSocketHandler.clientMessage;
        }
        DerivativeWebSocketHandler.clientMessage = clientMessage;
    }
}
