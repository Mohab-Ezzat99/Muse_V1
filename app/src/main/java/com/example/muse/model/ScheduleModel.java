package com.example.muse.model;

public class ScheduleModel {
    private int id;
    private int deviceId;
    private int pictureId;
    private String description;
    private int newState;
    private String atTime;
    private int afterPeriod;
    private String repeat;

    public ScheduleModel(int deviceId, int newState, String atTime, int afterPeriod, String repeat) {
        this.deviceId = deviceId;
        this.newState = newState;
        this.atTime = atTime;
        this.afterPeriod = afterPeriod;
        this.repeat = repeat;
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

    public int getNewState() {
        return newState;
    }

    public void setNewState(int newState) {
        this.newState = newState;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public int getAfterPeriod() {
        return afterPeriod;
    }

    public void setAfterPeriod(int afterPeriod) {
        this.afterPeriod = afterPeriod;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
