package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.muse.R;
import com.example.muse.model.DeviceModel;
import java.util.ArrayList;

public class RVDeviceBotAdapter extends RecyclerView.Adapter<RVDeviceBotAdapter.DBViewHolder> {
    private ArrayList<DeviceModel> mDevice =new ArrayList<>();
    private OnDeviceItemListener listener;
    private Context context;

    public RVDeviceBotAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public RVDeviceBotAdapter.DBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVDeviceBotAdapter.DBViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_device, null, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull DBViewHolder holder, int position) {
        DeviceModel mBottomDevice = mDevice.get(position);
        holder.device=mBottomDevice;
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(mBottomDevice.getIcon(),null));
        holder.tv_device.setText(mBottomDevice.getName());
    }

    @Override
    public int getItemCount() {
        return mDevice.size();
    }

    public void addItem(DeviceModel mBottomDevice){
        this.mDevice.add(mBottomDevice);
        notifyDataSetChanged();
    }

    public void setListener(OnDeviceItemListener listener) {
        this.listener= listener;
    }

    class DBViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_device;
        DeviceModel device;

        public DBViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon=itemView.findViewById(R.id.itemBD_iv);
            tv_device=itemView.findViewById(R.id.itemBD_tv);

            itemView.setOnClickListener(v -> listener.OnItemClick(device));
        }
    }
}
