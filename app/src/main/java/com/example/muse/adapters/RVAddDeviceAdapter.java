package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.MainActivity;
import com.example.muse.R;
import com.example.muse.model.DeviceRequestModel;

import java.util.ArrayList;
import java.util.List;

public class RVAddDeviceAdapter extends RecyclerView.Adapter<RVAddDeviceAdapter.ADViewHolder> {
    private List<DeviceRequestModel> DeviceRequestModels;
    private OnDeviceItemListener listener;
    private final Context context;
    private OnSwitchListener switchListener;

    public RVAddDeviceAdapter(Context context) {
        this.context = context;
        this.DeviceRequestModels = new ArrayList<>();
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ADViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ADViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_device, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ADViewHolder holder, int position) {
        DeviceRequestModel deviceModel = DeviceRequestModels.get(position);

        holder.tv_device.setText(deviceModel.getName());
        holder.device = deviceModel;
        switch (deviceModel.getPictureId()) {
            case 1:
                holder.iv_icon.setImageResource(R.drawable.ic_tv);
                break;
            case 2:
                holder.iv_icon.setImageResource(R.drawable.ic_fridge);
                break;
            case 3:
                holder.iv_icon.setImageResource(R.drawable.ic_air_conditioner);
                break;
            case 4:
                holder.iv_icon.setImageResource(R.drawable.ic_pc);
                break;
            case 5:
                holder.iv_icon.setImageResource(R.drawable.ic_clothes_dryer);
                break;
            case 6:
                holder.iv_icon.setImageResource(R.drawable.ic_freezer);
                break;
            case 7:
                holder.iv_icon.setImageResource(R.drawable.ic_coffee_maker);
                break;
            case 8:
                holder.iv_icon.setImageResource(R.drawable.ic_dishwasher);
                break;
            case 9:
                holder.iv_icon.setImageResource(R.drawable.ic_fan_heater);
                break;
            case 10:
                holder.iv_icon.setImageResource(R.drawable.ic_toaster);
                break;
            case 11:
                holder.iv_icon.setImageResource(R.drawable.ic_water_dispenser);
                break;
            case 12:
                holder.iv_icon.setImageResource(R.drawable.ic_plug);
        }
        holder.switchCompat.setChecked(deviceModel.getState() != 0);

        holder.tv_percent.setText(String.valueOf(deviceModel.getUsage()));
        holder.progressBar.setProgress(deviceModel.getPercent());

        if(deviceModel.getState() != 0){
            holder.iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
            holder.tv_percent.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);

            holder.tv_percent.setText(String.valueOf(deviceModel.getUsage()));
            holder.progressBar.setProgress(deviceModel.getPercent());
        }
        else {
            holder.iv_icon.setColorFilter(context.getResources().getColor(R.color.gray));
            holder.tv_percent.setVisibility(View.INVISIBLE);
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return DeviceRequestModels.size();
    }

    public void setDeviceRequestModels(List<DeviceRequestModel> deviceModels) {
        this.DeviceRequestModels = deviceModels;
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
        private DeviceRequestModel device;

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

            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked)
                    switchListener.isChecked(device,1);
                else
                    switchListener.isChecked(device,0);
            });
        }
    }

    public interface OnSwitchListener {
        void isChecked(DeviceRequestModel device, int state);
    }
}