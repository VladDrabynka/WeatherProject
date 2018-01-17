package com.netcracker.service.impl;

import com.netcracker.model.WeatherInfo;
import com.netcracker.model.entites.GeneralInfo;
import com.netcracker.model.entites.TemperatureInfo;
import com.netcracker.model.entites.TimeInfo;
import com.netcracker.model.repository.GeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertationService {

    @Autowired
    GeneralRepository repo;

    public GeneralInfo convertToGeneral(WeatherInfo owmInfo){
        GeneralInfo generalInfo = new GeneralInfo();
        generalInfo.setHumidity(owmInfo.getHumidity());
        generalInfo.setPressure(owmInfo.getPressure());
        generalInfo.setVisibility(owmInfo.getVisibility());

        TimeInfo timeInfo = new TimeInfo();

        timeInfo.setSunrise(owmInfo.getSunriseTimestamp());
        timeInfo.setSunset(owmInfo.getSunsetTimestamp());
        timeInfo.setDateCalculation(owmInfo.getDateCalculationTimestamp());
        generalInfo.setTimeInfo(timeInfo);

        TemperatureInfo temperatureInfo = new TemperatureInfo();

        temperatureInfo.setTemp(owmInfo.getTemp());
        temperatureInfo.setTempMin(owmInfo.getTempMin());
        temperatureInfo.setTempMax(owmInfo.getTempMax());

        generalInfo.setTemperatureInfo(temperatureInfo);

        return generalInfo;
    }
}
