package com.fourkins.rove.post;

import java.util.Date;

public class Post {

    private int postId;
    private int userId;
    private double latitude;
    private double longitude;
    private String message;
    private Date timestamp;

    public Post() {

    }

    public Post(int postId, int userId, double latitude, double longitude,
            String message, long timestamp) {
        super();
        this.postId = postId;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
        this.timestamp = new Date(timestamp);
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = new Date(timestamp);
    }
}
