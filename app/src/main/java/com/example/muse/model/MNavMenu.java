package com.example.muse.model;

import android.graphics.drawable.Drawable;

public class MNavMenu {
    private Drawable icon;
    private String name;

    public MNavMenu(Drawable icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
