package com.netcracker.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class WeatherInfo {

    private final double temp;
    private final long pressure;
    private final long humidity;
    private final double tempMin;
    private final double tempMax;
    private final long visibility;
    private final Timestamp sunrise;
    private final Timestamp sunset;
    private final Timestamp dateCalculation;

    @JsonCreator
    public WeatherInfo(@JsonProperty("temp") double temp,
                       @JsonProperty("pressure") long pressure,
                       @JsonProperty("humidity") long humidity,
                       @JsonProperty("temp_min") double tempMin,
                       @JsonProperty("temp_max") double tempMax,
                       @JsonProperty("visibility") long visibility,
                       @JsonProperty("dt") Timestamp dt,
                       @JsonProperty("sunrise") Timestamp sunrise,
                        @JsonProperty("sunset") Timestamp sunset) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.visibility = visibility;
        this.dateCalculation = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public long getPressure() {
        return pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public long getVisibility() {
        return visibility;
    }

    public String getDateCalculation() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dateCalculation.getTime()*1000);
    }

    public String getSunrise() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(sunrise.getTime()*1000);
    }

    public String getSunset() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(sunset.getTime()*1000);
    }

    public Timestamp getDateCalculationTimestamp(){
        return dateCalculation;
    }

    public Timestamp getSunsetTimestamp(){
        return sunset;
    }

    public Timestamp getSunriseTimestamp(){
        return sunrise;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", visibility=" + visibility +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", dateCalculation='" + dateCalculation + '\'' +
                '}';
    }
}
