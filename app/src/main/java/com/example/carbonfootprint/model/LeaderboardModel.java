package com.example.carbonfootprint.model;

public class LeaderboardModel extends NewsfeedModel{

    private String badge;

    public LeaderboardModel() {}

    public LeaderboardModel(String image, String date, String name, float distance, String type, float carbonScore,
                            String userId) {
        super(date, name, distance, type, carbonScore, image, userId);
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
