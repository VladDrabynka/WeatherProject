package com.netcracker.model.entites;

import javax.persistence.*;

@Entity
@Table(name = "TEMPERATURE")
public class TemperatureInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "temperature_id", nullable = false, unique = true)
    private Long temperatureId;

    @Column(name = "temp", nullable = false)
    private Double temp;

    @Column(name = "temp_min", nullable = false)
    private Double tempMin;

    @Column(name = "temp_max", nullable = false)
    private Double tempMax;

    public TemperatureInfo() {
    }

    public TemperatureInfo(Double temp, Double tempMin, Double tempMax) {
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    public Long getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(Long temperatureId) {
        this.temperatureId = temperatureId;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public String toString() {
        return "TemperatureInfo{" +
                "temperatureId=" + temperatureId +
                ", temp=" + temp +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }
}
