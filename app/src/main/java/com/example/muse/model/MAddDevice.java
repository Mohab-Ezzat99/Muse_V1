package com.example.muse.model;

import android.graphics.drawable.Drawable;

public class MAddDevice {

    private Drawable icon;
    private boolean checked;
    private String device;
    private String percent;
    private int progress;
    public static long id=0;

    public MAddDevice(Drawable icon, boolean checked, String device, String percent, int progress) {
        this.icon = icon;
        this.checked = checked;
        this.device = device;
        this.percent = percent;
        this.progress = progress;
        id++;
    }

    public Drawable getImage() {
        return icon;
    }

    public void setImage(Drawable icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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

    public long getId() {
        return id;
    }
}
