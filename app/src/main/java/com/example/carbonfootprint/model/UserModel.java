package com.example.carbonfootprint.model;

public class UserModel {

    private String carSize;
    private String fuelType;
    private Integer electricity;

    public UserModel() {
    }

    public UserModel(String carSize, String fuelType, Integer electricity) {
        this.carSize = carSize;
        this.fuelType = fuelType;
        this.electricity = electricity;
    }

    public String getCarSize() {
        return carSize;
    }

    public void setCarSize(String carSize) {
        this.carSize = carSize;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getElectricity() {
        return electricity;
    }

    public void setElectricity(Integer electricity) {
        this.electricity = electricity;
    }
}
