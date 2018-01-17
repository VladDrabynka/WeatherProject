package com.netcracker.model.entites;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TIME_INFO")
public class TimeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "time_id", nullable = false, unique = true)
    private Long timeId;

    @Column(name = "sunrise", nullable = false)
    private Timestamp sunrise;

    @Column(name = "sunset", nullable = false)
    private Timestamp sunset;

    @Column(name = "date_calc", nullable = false)
    private Timestamp dateCalculation;

    public TimeInfo() {
    }

    public TimeInfo(Timestamp sunrise, Timestamp sunset, Timestamp dateCalculation) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.dateCalculation = dateCalculation;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Timestamp getSunrise() {
        return sunrise;
    }

    public void setSunrise(Timestamp sunrise) {
        this.sunrise = sunrise;
    }

    public Timestamp getSunset() {
        return sunset;
    }

    public void setSunset(Timestamp sunset) {
        this.sunset = sunset;
    }

    public Timestamp getDateCalculation() {
        return dateCalculation;
    }

    public void setDateCalculation(Timestamp dateCalculation) {
        this.dateCalculation = dateCalculation;
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "timeId=" + timeId +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", dateCalculation=" + dateCalculation +
                '}';
    }
}