package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.interfaces.OnDeviceItemListener;
import com.example.muse.model.DeviceModel;

import java.util.List;

public class RVAddDeviceAdapter extends RecyclerView.Adapter<RVAddDeviceAdapter.ADViewHolder> {
    private List<DeviceModel> DeviceModels;
    private OnDeviceItemListener listener;
    private Context context;

    public RVAddDeviceAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ADViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ADViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_device, null, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ADViewHolder holder, int position) {
        DeviceModel deviceModel = DeviceModels.get(position);
        holder.device= deviceModel;
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(deviceModel.getIcon(), null));
        holder.switchCompat.setChecked(deviceModel.isOn());
        holder.tv_device.setText(deviceModel.getName());
        holder.tv_percent.setText(deviceModel.getPercent());
        holder.progressBar.setProgress(deviceModel.getProgress());
    }

    @Override
    public int getItemCount() {
        return DeviceModels.size();
    }

    public void addItem(DeviceModel DeviceModel){
        this.DeviceModels.add(DeviceModel);
        notifyDataSetChanged();
    }

    public void setDeviceModels(List<DeviceModel> deviceModels) {
        this.DeviceModels = deviceModels;
        notifyDataSetChanged();
    }

    public void setListener(OnDeviceItemListener listener) {
        this.listener = listener;
    }

    class ADViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private SwitchCompat switchCompat;
        private TextView tv_device,tv_percent;
        private ProgressBar progressBar;
        private DeviceModel device;

        public ADViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAD_iv_icon);
            switchCompat = itemView.findViewById(R.id.itemAD_switch);
            tv_device = itemView.findViewById(R.id.itemAD_tv_device);
            tv_percent = itemView.findViewById(R.id.itemAD_tv_percent);
            progressBar = itemView.findViewById(R.id.itemAD_pb);

            itemView.setOnClickListener(v -> listener.OnItemClick(device));
            itemView.setOnLongClickListener(v -> {
                listener.OnItemLongClick(v,device);
                return true;
            });
        }
    }
}