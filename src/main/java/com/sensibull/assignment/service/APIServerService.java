package com.sensibull.assignment.service;

import com.sensibull.assignment.models.ApiResponseDto;

public interface APIServerService {
    ApiResponseDto fetchUnderlyingData();

    ApiResponseDto fetchDerivativeData();
}
