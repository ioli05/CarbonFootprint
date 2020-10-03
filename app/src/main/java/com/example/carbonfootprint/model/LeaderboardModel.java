package com.example.carbonfootprint.model;

import java.util.Date;

public class LeaderboardModel extends NewsfeedModel{

    private String badge;

    public LeaderboardModel() {}

    public LeaderboardModel(String image, Date timestamp, String name, float distance, String type, float carbonScore,
                            String userId) {
        super(timestamp, name, distance, type, carbonScore, image, userId);
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
