package com.sensibull.assignment.client;

import com.sensibull.assignment.constants.APIConstant;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.ExecutionException;

import static com.sensibull.assignment.constants.APIConstant.WEB_SOCKET;


@Component
public class ClientWebSocketStomp {

    @Autowired
    private UnderlyingWebSocketHandler underlyingWebSocketHandler;

    @Autowired
    private DerivativeWebSocketHandler derivativeWebSocketHandler;

    public void makeCallToWebSocketServer(String handler) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(20 * 1024 * 1024);
        WebSocketClient client = new StandardWebSocketClient(container);
        try {
            /**
             * Here we can create the factory for different type of handler and then after input condition we can pass
             * that object in the websocket handler
             */
            client.execute(APIConstant.DERIVATIVE_CHECK.equals(handler) ?derivativeWebSocketHandler :underlyingWebSocketHandler, WEB_SOCKET).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
