package com.example.muse.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class DeviceModel implements Serializable {

    private String name;
    private Drawable icon;
    private String percent;
    private int progress;
    private boolean isOn;
    private String alertMessage;
    public static long id = 0;

    //add new device
    public DeviceModel(Drawable icon,String name) {
        this.name = name;
        this.icon = icon;
        id++;
    }

    //init device
    public void initDevice(String percent, int progress, boolean isOn) {
        this.percent = percent;
        this.progress = progress;
        this.isOn = isOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        DeviceModel.id = id;
    }
}
