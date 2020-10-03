package com.example.carbonfootprint.model;

public class LeaderboardModel extends NewsfeedModel{

    private String badge;

    public LeaderboardModel(String image, String date, String name, float distance, String type, float carbonScore) {
        super(image, date, name, distance, type, carbonScore);
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
