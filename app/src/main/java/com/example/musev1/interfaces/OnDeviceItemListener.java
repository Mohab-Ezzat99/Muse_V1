package com.example.musev1.interfaces;

import android.view.View;

import com.example.musev1.model.AlertModel;
import com.example.musev1.model.DeviceModel;
import com.example.musev1.model.DeviceRequestModel;

public interface OnDeviceItemListener {
    void OnItemClick(DeviceModel device);
    void OnItemLongClick(View view,DeviceModel device);
}
