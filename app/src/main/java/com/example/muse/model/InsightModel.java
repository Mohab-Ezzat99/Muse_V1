package com.example.muse.model;

import java.util.ArrayList;

public class InsightModel {
    private int usage;
    private int averageUsage;
    private int estimatedUsage;
    private ArrayList<InsightDataModel> data;

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public int getAverageUsage() {
        return averageUsage;
    }

    public void setAverageUsage(int averageUsage) {
        this.averageUsage = averageUsage;
    }

    public int getEstimatedUsage() {
        return estimatedUsage;
    }

    public void setEstimatedUsage(int estimatedUsage) {
        this.estimatedUsage = estimatedUsage;
    }

    public ArrayList<InsightDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<InsightDataModel> data) {
        this.data = data;
    }
}
