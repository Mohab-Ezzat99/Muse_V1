package com.example.muse.adapters;

import android.view.View;

import com.example.muse.model.DeviceModel;
import com.example.muse.model.DeviceRequestModel;

public interface OnDeviceItemListener {
    void OnItemClick(DeviceRequestModel device);
    void OnBottomSheetItemClick(DeviceModel device,int position);
    void OnItemLongClick(View view,DeviceRequestModel device);
}
