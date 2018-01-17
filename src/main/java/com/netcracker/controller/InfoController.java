package com.netcracker.controller;

import com.netcracker.model.WeatherInfo;
import com.netcracker.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    WeatherService service;

    @GetMapping("/{city}")
    public WeatherInfo getWeatherByCity(@PathVariable("city") String city) {
        return service.currentWeatherAtCity(city);
    }

    @GetMapping("/id={cityId}")
    public WeatherInfo getWeatherByCityId(@PathVariable("cityId") int cityId) {
        return service.currentWeatherAtCity(cityId);
    }

    @GetMapping("/{country}/{city}")
    public WeatherInfo getWeatherByCityAndCountry(@PathVariable("city") String city, @PathVariable("country") String country) {
        return service.currentWeatherAtCity(city, country);
    }
}
