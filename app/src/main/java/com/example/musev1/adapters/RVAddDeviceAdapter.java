package com.example.musev1.adapters;

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

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.interfaces.OnDeviceItemListener;
import com.example.musev1.model.DeviceModel;

import java.util.ArrayList;
import java.util.List;

public class RVAddDeviceAdapter extends RecyclerView.Adapter<RVAddDeviceAdapter.ADViewHolder> {
    private List<DeviceModel> DeviceModels;
    private OnDeviceItemListener listener;
    private final Context context;
    private OnSwitchListener switchListener;

    public RVAddDeviceAdapter(Context context) {
        this.context = context;
        this.DeviceModels = new ArrayList<>();
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ADViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ADViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_device, null, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ADViewHolder holder, int position) {
        DeviceModel deviceModel = DeviceModels.get(position);

        holder.tv_device.setText(deviceModel.getName());
        holder.device = deviceModel;
        holder.iv_icon.setImageResource(deviceModel.getIcon());
        holder.switchCompat.setChecked(deviceModel.isOn());

        holder.tv_percent.setText("50%");
        holder.progressBar.setProgress(50);

        if (deviceModel.isOn()) {
            holder.iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
            holder.tv_percent.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.iv_icon.setColorFilter(context.getResources().getColor(R.color.gray));
            holder.tv_percent.setVisibility(View.INVISIBLE);
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return DeviceModels.size();
    }

    public void setDeviceModels(List<DeviceModel> deviceModels) {
        this.DeviceModels = deviceModels;
        notifyDataSetChanged();
    }

    public void setListener(OnDeviceItemListener listener) {
        this.listener = listener;
    }

    public void setSwitchListener(OnSwitchListener switchListener) {
        this.switchListener = switchListener;
    }

    class ADViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_icon;
        private final SwitchCompat switchCompat;
        private final TextView tv_device,tv_percent;
        private final ProgressBar progressBar;
        private DeviceModel device;

        @SuppressLint("SetTextI18n")
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

            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> switchListener.isChecked(device, isChecked));
        }
    }

    public interface OnSwitchListener {
        void isChecked(DeviceModel device, boolean isOn);
    }
}