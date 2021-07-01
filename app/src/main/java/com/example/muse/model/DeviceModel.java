package com.example.muse.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "devices_table")
public class DeviceModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int icon;
    private boolean isOn;

    private String percent;
    private int progress;

    // for conditions displaying in fragment
    private boolean isAdded;
    private boolean hasGoal;
    private boolean hasSchedules;
    private boolean hasCustomAlert;
    private boolean hasAlert;
    private String alertMessage;

    //check if custom alert exist
    private boolean isAlertOn;
    private String time;
    private String time_type;
    private String days;

    public DeviceModel() {
    }

    //add new device
    @Ignore
    public DeviceModel(int icon, String name) {
        this.name = name;
        this.icon = icon;
    }

    //init device
    public void initDevice(String percent, int progress, boolean isOn) {
        this.percent = percent;
        this.progress = progress;
        this.isOn = isOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    //___________________________________________________________________________________________//

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

    //___________________________________________________________________________________________//

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public boolean isHasGoal() {
        return hasGoal;
    }

    public void setHasGoal(boolean hasGoal) {
        this.hasGoal = hasGoal;
    }

    public boolean isHasSchedules() {
        return hasSchedules;
    }

    public void setHasSchedules(boolean hasSchedules) {
        this.hasSchedules = hasSchedules;
    }

    public boolean isHasCustomAlert() {
        return hasCustomAlert;
    }

    public void setHasCustomAlert(boolean hasCustomAlert) {
        this.hasCustomAlert = hasCustomAlert;
    }

    public boolean isHasAlert() {
        return hasAlert;
    }

    public void setHasAlert(boolean hasAlert) {
        this.hasAlert = hasAlert;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    //___________________________________________________________________________________________//

    public boolean isAlertOn() {
        return isAlertOn;
    }

    public void setAlertOn(boolean alertOn) {
        isAlertOn = alertOn;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
