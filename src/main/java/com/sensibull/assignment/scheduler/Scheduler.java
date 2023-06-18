package com.sensibull.assignment.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensibull.assignment.client.ClientWebSocketStomp;
import com.sensibull.assignment.constants.APIConstant;
import com.sensibull.assignment.models.ApiResponseDto;
import com.sensibull.assignment.models.ClientSubscribeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Configuration
@EnableScheduling
public class Scheduler {

    @Autowired
    private ClientWebSocketStomp clientWebSocketStomp;

    @Scheduled(fixedDelay = 60000 * 15)
    public void pollUnderlyingInformation() {
        ApiResponseDto apiResponseDto = makeHttpCallToStockServer(APIConstant.UNDERLYING);
        apiResponseDto.getPayload().get(0).getToken();
        ClientSubscribeMessage model = ClientSubscribeMessage.builder().msg_command("subscribe").data_type("quote").tokens(Arrays.asList(2)).build();
        clientWebSocketStomp.makeCallToWebSocketServer();

    }

    @Scheduled(fixedDelay = 60000)
    public void pollDerivativePrices() {
        makeHttpCallToStockServer(APIConstant.UNDERLYING);
    }

    private ApiResponseDto makeHttpCallToStockServer(String url) {
        HttpRequest httpRequest = null;
        ApiResponseDto apiResponseDto = null;
        try {
            httpRequest = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                if (response.body() == null || response.body().isBlank()) {
                    return null;
                }
                ObjectMapper mapper = new ObjectMapper();
                apiResponseDto = mapper.readValue(response.body(), ApiResponseDto.class);
            } else {
                //we can log the error, or we can implement the retry functionality on the basis of status code
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return apiResponseDto;
    }

}

