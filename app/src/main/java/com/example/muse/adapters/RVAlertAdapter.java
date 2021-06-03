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
import com.example.muse.interfaces.OnDeviceItemListener;
import com.example.muse.model.DeviceModel;
import java.util.List;

public class RVAlertAdapter extends RecyclerView.Adapter<RVAlertAdapter.AlertViewHolder> {
    private List<DeviceModel> mDevice;
    private Context context;
    private OnDeviceItemListener listener;

    public RVAlertAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlertViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alert, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        DeviceModel mAlert = mDevice.get(position);
        holder.device=mAlert;
        holder.tv_name.setText(mAlert.getName());
        holder.tv_message.setText(mAlert.getAlertMessage());
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(mAlert.getIcon(),null));
    }

    @Override
    public int getItemCount() {
        return mDevice.size();
    }

    public void setList(List<DeviceModel> mDevice) {
        this.mDevice = mDevice;
        notifyDataSetChanged();
    }

    public void addItem(DeviceModel mAlert) {
        this.mDevice.add(mAlert);
        notifyDataSetChanged();
    }

    public void setListener(OnDeviceItemListener listener) {
        this.listener = listener;
    }

    class AlertViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_icon,iv_close;
        private TextView tv_name, tv_message;
        private DeviceModel device;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon=itemView.findViewById(R.id.itemAlert_iv_device);
            iv_close=itemView.findViewById(R.id.itemAlert_iv_close);
            tv_name=itemView.findViewById(R.id.itemAlert_tv_name);
            tv_message=itemView.findViewById(R.id.itemAlert_tv_message);

            iv_close.setOnClickListener(v -> listener.OnItemClick(device));
        }
    }
}