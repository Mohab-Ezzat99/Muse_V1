package com.example.muse.model;

public class DeviceRequestModel {
    private int id;
    private int pictureId;
    private String name;
    private String mqttId;
    private int usage;
    private int percent;
    private int state;

    public DeviceRequestModel(int pictureId, String name, String mqttId) {
        this.pictureId = pictureId;
        this.name = name;
        this.mqttId = mqttId;
    }

    public DeviceRequestModel(int pictureId, String name) {
        this.pictureId = pictureId;
        this.name = name;
    }

    public DeviceRequestModel(int id, int state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMqttId() {
        return mqttId;
    }

    public void setMqttId(String mqttId) {
        this.mqttId = mqttId;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
