package com.sensibull.assignment.controllers;

import com.sensibull.assignment.models.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class APIServerController {

    @GetMapping("underlying-prices")
    public ResponseEntity<ApiResponseDto> getUnderlyingPrices(){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("derivative-prices/{underlying-symbol}")
    public ResponseEntity<ApiResponseDto> getDerivativePrices(@PathVariable("underlying-symbol") String underlyingSymbol){

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
