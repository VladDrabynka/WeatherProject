package com.netcracker.service;

import com.netcracker.model.WeatherInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface WeatherService {

    WeatherInfo currentWeatherAtCity(int cityId);

    WeatherInfo currentWeatherAtCity(String cityName);

    WeatherInfo currentWeatherAtCity(String cityName, String countryCode);
}
