package com.example.musev1.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musev1.R;
import com.example.musev1.interfaces.OnBottomItemListener;
import com.example.musev1.interfaces.OnDeviceItemListener;
import com.example.musev1.model.DeviceModel;
import java.util.ArrayList;

public class RVDeviceBotAdapter extends RecyclerView.Adapter<RVDeviceBotAdapter.DBViewHolder> {
    private ArrayList<DeviceModel> mDevice =new ArrayList<>();
    private OnBottomItemListener listener;

    public RVDeviceBotAdapter() {
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public RVDeviceBotAdapter.DBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVDeviceBotAdapter.DBViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_device, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DBViewHolder holder, int position) {
        DeviceModel mBottomDevice = mDevice.get(position);
        holder.device=mBottomDevice;
        holder.iv_icon.setImageResource(mBottomDevice.getIcon());
        holder.tv_device.setText(mBottomDevice.getName());
        holder.position=position;
    }

    @Override
    public int getItemCount() {
        return mDevice.size();
    }

    public void setList(ArrayList<DeviceModel> mDevice) {
        this.mDevice = mDevice;
        notifyDataSetChanged();
    }

    public void setListener(OnBottomItemListener listener) {
        this.listener= listener;
    }

    class DBViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_device;
        DeviceModel device;
        int position;

        public DBViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon=itemView.findViewById(R.id.itemBD_iv);
            tv_device=itemView.findViewById(R.id.itemBD_tv);

            itemView.setOnClickListener(v -> listener.OnBottomSheetItemClick(device,++position));
        }
    }
}
