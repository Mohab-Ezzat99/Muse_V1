package com.example.musev1.model;

import java.util.ArrayList;

public class DeviceResponseModel {
    private int id;
    private int pictureId;
    private String name;
    private int state;
    private ArrayList<AlertModel> alerts;
    private ArrayList<GoalModel> goals;
    private ArrayList<ScheduleModel> schedules;
    private ArrayList<AlertModel> customAlerts;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<AlertModel> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<AlertModel> alerts) {
        this.alerts = alerts;
    }

    public ArrayList<GoalModel> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<GoalModel> goals) {
        this.goals = goals;
    }

    public ArrayList<ScheduleModel> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<ScheduleModel> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<AlertModel> getCustomAlerts() {
        return customAlerts;
    }

    public void setCustomAlerts(ArrayList<AlertModel> customAlerts) {
        this.customAlerts = customAlerts;
    }
}
