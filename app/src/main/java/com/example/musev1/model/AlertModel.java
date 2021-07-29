package com.example.musev1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alerts_table")
public class AlertModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Long deviceId;
    private String deviceName;
    private int pictureId;
    private String description;


    public AlertModel(Long deviceId, String deviceName, int pictureId, String description) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.pictureId = pictureId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
