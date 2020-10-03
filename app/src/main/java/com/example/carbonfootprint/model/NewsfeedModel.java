package com.example.carbonfootprint.model;

public class NewsfeedModel {

    private String image;
    private String date;
    private String name;
    private float distance;
    private String type;
    private float carbonScore;

    public NewsfeedModel(String image, String date, String name, float distance, String type, float carbonScore) {
        this.image = image;
        this.date = date;
        this.name = name;
        this.distance = distance;
        this.type = type;
        this.carbonScore = carbonScore;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public float getDistance() {
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

    public float getCarbonScore() {
        return carbonScore;
    }

    public void setCarbonScore(float carbonScore) {
        this.carbonScore = carbonScore;
    }
}
