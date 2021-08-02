package com.example.musev1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedules_table")
public class ScheduleModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long deviceId;
    private String deviceName;
    private int pictureId;
    private String state;
    private String atTime;
    private String afterPeriod;
    private String repeat;

    public ScheduleModel(long deviceId,String deviceName,int pictureId, String state, String atTime, String afterPeriod, String repeat) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.pictureId = pictureId;
        this.state = state;
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

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
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

    public void setNewState(String state) {
        this.state = state;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public String getAfterPeriod() {
        return afterPeriod;
    }

    public void setAfterPeriod(String afterPeriod) {
        this.afterPeriod = afterPeriod;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
