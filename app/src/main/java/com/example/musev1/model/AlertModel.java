package com.example.musev1.model;

public class AlertModel {

    private int id;
    private int deviceId;
    private int pictureId;
    private String description;
    private int state;
    private String atTime;
    private String forPeriod;
    private int maxUsage;

    public AlertModel(int deviceId, int state, String atTime, String forPeriod, int maxUsage) {
        this.deviceId = deviceId;
        this.state = state;
        this.atTime = atTime;
        this.forPeriod = forPeriod;
        this.maxUsage = maxUsage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public String getForPeriod() {
        return forPeriod;
    }

    public void setForPeriod(String forPeriod) {
        this.forPeriod = forPeriod;
    }

    public int getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }
}
