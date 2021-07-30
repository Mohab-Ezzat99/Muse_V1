package com.example.musev1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customAlerts_table")
public class CustomAlertModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int deviceId;
    private String deviceName;
    private int pictureId;
    private String state;
    private String atTime;
    private String forPeriod;
    private String maxUsage;

    public CustomAlertModel(int deviceId,String deviceName,int pictureId, String state, String atTime, String forPeriod, String maxUsage) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.pictureId = pictureId;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public String getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(String maxUsage) {
        this.maxUsage = maxUsage;
    }
}
