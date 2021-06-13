package com.example.muse.interfaces;

import android.view.View;

import com.example.muse.model.DeviceModel;

public interface OnDeviceItemListener {
    void OnItemClick(DeviceModel device);
    void OnItemLongClick(View view,DeviceModel device);
}
