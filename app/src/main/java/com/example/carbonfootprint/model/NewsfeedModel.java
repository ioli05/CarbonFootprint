package com.example.carbonfootprint.model;

public class NewsfeedModel {

    private String date;
    private String name;
    private double distance;
    private String type;
    private double carbonScore;
    private String photoURI;
    private String userId;

    public NewsfeedModel(String date, String name, double distance, String type, double carbonScore, String photoURI, String userId) {
        this.date = date;
        this.name = name;
        this.distance = distance;
        this.type = type;
        this.carbonScore = carbonScore;
        this.photoURI = photoURI;
        this.userId = userId;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCarbonScore() {
        return carbonScore;
    }

    public void setCarbonScore(float carbonScore) {
        this.carbonScore = carbonScore;
    }
}
