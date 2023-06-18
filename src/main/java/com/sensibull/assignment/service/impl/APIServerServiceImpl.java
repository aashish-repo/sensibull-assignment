package com.sensibull.assignment.service.impl;

import com.sensibull.assignment.entity.UnderlyingEntity;
import com.sensibull.assignment.models.ApiResponseDto;
import com.sensibull.assignment.models.PricesResponse;
import com.sensibull.assignment.repository.UnderlyingPriceRepository;
import com.sensibull.assignment.service.APIServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class APIServerServiceImpl implements APIServerService {

    @Autowired
    private UnderlyingPriceRepository underlyingPriceRepository;
    @Override
    public ApiResponseDto fetchUnderlyingData() {
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setSuccess("true");
        apiResponseDto.setPayload(convertEntityInResponseDto(underlyingPriceRepository.findAll()));
        return apiResponseDto;
    }

    @Override
    public ApiResponseDto fetchDerivativeData() {
        return null;
    }

    private List<PricesResponse> convertEntityInResponseDto(List<UnderlyingEntity> underlyingEntities){
        List<PricesResponse> pricesResponseList = new ArrayList<>();
        for(UnderlyingEntity underlyingEntity : underlyingEntities){
            PricesResponse pricesResponse = new PricesResponse();
            pricesResponse.setExpiry(underlyingEntity.getExpiry());
            pricesResponse.setPrice(underlyingEntity.getPrice());
            pricesResponse.setSymbol(underlyingEntity.getSymbol());
            pricesResponse.setToken(underlyingEntity.getToken());
            pricesResponse.setStrike(underlyingEntity.getStrike());
            pricesResponse.setInstrument_type(underlyingEntity.getInstrument_type());
            pricesResponse.setUnderlying(underlyingEntity.getUnderlying());
            pricesResponseList.add(pricesResponse);
        }
        return pricesResponseList;
    }
}
