package com.sensibull.assignment.client;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.ExecutionException;

import static com.sensibull.assignment.constants.APIConstant.WEB_SOCKET;


@Component
public class ClientWebSocketStomp {

    public void makeCallToWebSocketServer() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(20 * 1024 * 1024);
        WebSocketClient client = new StandardWebSocketClient(container);
        try {
            client.execute(new MyWebSocketHandler(), WEB_SOCKET).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
