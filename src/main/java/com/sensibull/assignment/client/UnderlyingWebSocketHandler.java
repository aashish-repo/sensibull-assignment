package com.sensibull.assignment.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensibull.assignment.models.ClientMessage;
import com.sensibull.assignment.models.ServerMessageResponse;
import com.sensibull.assignment.repository.UnderlyingPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


@Component
public class UnderlyingWebSocketHandler implements WebSocketHandler {

    private static ClientMessage clientMessageSubscribe;
    private static ClientMessage clientMessageUnsubscribe;

    @Autowired
    private UnderlyingPriceRepository underlyingPriceRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected To Websocket");
        /**
         * unsubscribe the previously subscribed messages and subscribe for new ones
         */
        if(clientMessageUnsubscribe!=null){
            clientMessageUnsubscribe.setMsg_command("unsubscribe");
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(clientMessageUnsubscribe)));
        }
        session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(clientMessageSubscribe)));
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

    public static void setClientMessage(ClientMessage clientMessage) {
        if(UnderlyingWebSocketHandler.clientMessageSubscribe!=null){
            UnderlyingWebSocketHandler.clientMessageUnsubscribe = UnderlyingWebSocketHandler.clientMessageSubscribe;
        }
        UnderlyingWebSocketHandler.clientMessageSubscribe = clientMessage;
    }
}
