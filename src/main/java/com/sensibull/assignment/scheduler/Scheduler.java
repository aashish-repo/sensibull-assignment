package com.sensibull.assignment.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensibull.assignment.client.ClientWebSocketStomp;
import com.sensibull.assignment.constants.APIConstant;
import com.sensibull.assignment.entity.UnderlyingEntity;
import com.sensibull.assignment.models.ApiResponseDto;
import com.sensibull.assignment.models.PricesResponse;
import com.sensibull.assignment.repository.UnderlyingPriceRepository;
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
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class Scheduler {

    @Autowired
    private ClientWebSocketStomp clientWebSocketStomp;

    @Autowired
    private UnderlyingPriceRepository stockPriceRepository;

    private List<UnderlyingEntity> stockEntityList;
    private List<Integer> underlyingTokens;

    @Scheduled(fixedDelay = 60000 * 15)
    public void pollUnderlyingInformation() {
        ApiResponseDto apiResponseDto = makeHttpCallToStockServer(APIConstant.UNDERLYING);

        if(!"true".equalsIgnoreCase(apiResponseDto.getSuccess())){
            //log error that we didn't get the underlying api response
            return;
        }

        convertUnderDtoToEntity(apiResponseDto.getPayload());

        stockPriceRepository.saveAllAndFlush(stockEntityList);

/*        ClientSubscribeMessage model = ClientSubscribeMessage.builder().msg_command("subscribe").data_type("quote").tokens(Arrays.asList(2)).build();
        clientWebSocketStomp.makeCallToWebSocketServer();*/
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

    private void convertUnderDtoToEntity(List<PricesResponse> responseList){
        underlyingTokens = new ArrayList<>();
        stockEntityList = new ArrayList<>();
        for(PricesResponse pricesResponse : responseList){
            UnderlyingEntity stockEntity = new UnderlyingEntity();
            stockEntity.setExpiry(pricesResponse.getExpiry());
            stockEntity.setStrike(pricesResponse.getStrike());
            stockEntity.setSymbol(pricesResponse.getSymbol());
            stockEntity.setToken(pricesResponse.getToken());
            stockEntity.setInstrument_type(pricesResponse.getInstrument_type());
            stockEntity.setSymbol(pricesResponse.getSymbol());
            underlyingTokens.add(pricesResponse.getToken());
            stockEntityList.add(stockEntity);
        }

    }

}

