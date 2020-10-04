package com.example.carbonfootprint.model;

public class ElectricityModel {

    private String month;
    private Double consumption;
    private Double carbonScore;

    public ElectricityModel(String month, Double consumption, Double carbonScore) {
        this.month = month;
        this.consumption = consumption;
        this.carbonScore = carbonScore;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public Double getCarbonScore() {
        return carbonScore;
    }

    public void setCarbonScore(Double carbonScore) {
        this.carbonScore = carbonScore;
    }
}
