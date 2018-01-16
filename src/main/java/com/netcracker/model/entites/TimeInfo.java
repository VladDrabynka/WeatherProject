package com.netcracker.model.entites;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TIME_INFO")
public class TimeInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "time_id", nullable = false, unique = true)
    private Long timeId;

    @Column(name = "sunrise", nullable = false)
    private Timestamp sunrise;

    @Column(name = "sunset", nullable = false)
    private Timestamp sunset;

    public TimeInformation() {
    }

    public TimeInformation(Timestamp sunrise, Timestamp sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
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

    @Override
    public String toString() {
        return "TimeInformation{" +
                "timeId=" + timeId +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}