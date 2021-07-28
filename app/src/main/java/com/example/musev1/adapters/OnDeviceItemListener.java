package com.example.musev1.adapters;

import android.view.View;

import com.example.musev1.model.AlertModel;
import com.example.musev1.model.DeviceModel;
import com.example.musev1.model.DeviceRequestModel;

public interface OnDeviceItemListener {
    void OnItemClick(DeviceModel device);
    void OnItemClick(AlertModel alertModel);
    void OnBottomSheetItemClick(DeviceModel device,int position);
    void OnItemLongClick(View view,DeviceModel device);
}
