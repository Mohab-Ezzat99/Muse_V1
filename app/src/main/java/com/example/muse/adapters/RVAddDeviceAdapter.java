package com.example.muse.adapters;

import android.annotation.SuppressLint;
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
import com.example.muse.interfaces.OnADItemListener;
import com.example.muse.model.DeviceModel;
import java.util.ArrayList;

public class RVAddDeviceAdapter extends RecyclerView.Adapter<RVAddDeviceAdapter.ADViewHolder> {

    private ArrayList<DeviceModel> DeviceModels =new ArrayList<>();
    private OnADItemListener listener;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ADViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ADViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_device, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ADViewHolder holder, int position) {
        DeviceModel deviceModel = DeviceModels.get(position);
        holder.device= deviceModel;
        holder.iv_icon.setImageDrawable(deviceModel.getIcon());
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

    public void setListener(OnADItemListener listener) {
        this.listener = listener;
    }

    class ADViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        SwitchCompat switchCompat;
        TextView tv_device,tv_percent;
        ProgressBar progressBar;
        DeviceModel device;

        public ADViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon=itemView.findViewById(R.id.itemAD_iv_icon);
            switchCompat=itemView.findViewById(R.id.itemAD_switch);
            tv_device=itemView.findViewById(R.id.itemAD_tv_device);
            tv_percent=itemView.findViewById(R.id.itemAD_tv_percent);
            progressBar=itemView.findViewById(R.id.itemAD_pb);

            itemView.setOnClickListener(v -> listener.OnItemClick(device));
        }
    }
}