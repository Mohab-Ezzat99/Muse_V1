package com.example.musev1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals_table")
public class GoalModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long deviceId;
    private String deviceName;
    private int pictureId;
    private String type;
    private int used;
    private int estimation;
    private int percent;
    private String usageLimit;
    private int unit;

    public GoalModel(long deviceId,String deviceName,int pictureId, String type, String  usageLimit, int unit) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.pictureId = pictureId;
        this.type = type;
        this.usageLimit = usageLimit;
        this.unit = unit;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(String usageLimit) {
        this.usageLimit = usageLimit;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
