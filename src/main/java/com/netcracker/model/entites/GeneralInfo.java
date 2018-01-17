package com.netcracker.model.entites;

import javax.persistence.*;

@Entity
@Table(name = "GENERAL")
public class GeneralInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "general_id", nullable = false, unique = true)
    private Long generalId;

    @Column(name = "pressure", nullable = false)
    private Long pressure;

    @Column(name = "humidity", nullable = false)
    private Long humidity;

    @Column(name = "visibility", nullable = false)
    private Long visibility;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="time_id")
    TimeInfo timeInfo;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="temperature_id")
    TemperatureInfo temperatureInfo;

    public GeneralInfo() {
    }

    public GeneralInfo(Long pressure, Long humidity, Long visibility, TimeInfo timeInfo, TemperatureInfo temperatureInfo) {
        this.pressure = pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.timeInfo = timeInfo;
        this.temperatureInfo = temperatureInfo;
    }

    public Long getGeneralId() {
        return generalId;
    }

    public void setGeneralId(Long generalId) {
        this.generalId = generalId;
    }

    public Long getPressure() {
        return pressure;
    }

    public void setPressure(Long pressure) {
        this.pressure = pressure;
    }

    public Long getHumidity() {
        return humidity;
    }

    public void setHumidity(Long humidity) {
        this.humidity = humidity;
    }

    public Long getVisibility() {
        return visibility;
    }

    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public TemperatureInfo getTemperatureInfo() {
        return temperatureInfo;
    }

    public void setTemperatureInfo(TemperatureInfo temperatureInfo) {
        this.temperatureInfo = temperatureInfo;
    }

    @Override
    public String toString() {
        return "GeneralInfo{" +
                "generalId=" + generalId +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", visibility=" + visibility +
                timeInfo.toString() + "\n" +
                temperatureInfo.toString() +
                '}';
    }
}
