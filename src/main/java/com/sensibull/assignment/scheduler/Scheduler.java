package com.sensibull.assignment.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensibull.assignment.constants.APIConstant;
import com.sensibull.assignment.models.ApiResponseDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Configuration
@EnableScheduling
public class Scheduler {
    @Scheduled(fixedDelay = 60000*15)
    public void pollUnderlyingPrices(){
        makeHttpCallToStockServer(APIConstant.UNDERLYING);
    }

    @Scheduled(fixedDelay = 60000)
    public void pollDerivativePrices(){
        makeHttpCallToStockServer(APIConstant.UNDERLYING);
    }

    private void makeHttpCallToStockServer(String url){
        HttpRequest httpRequest=null;
        try {
            httpRequest =HttpRequest.newBuilder().uri(new URI(url)).GET().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() ==200){
                ObjectMapper mapper = new ObjectMapper();
                ApiResponseDto apiResponseDto = mapper.readValue(response.body(), ApiResponseDto.class);
            }
            else{
                //we can log the error, or we can implement the retry functionality on the basis of code
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

