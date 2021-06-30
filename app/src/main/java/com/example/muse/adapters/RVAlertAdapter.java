package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.model.DeviceModel;

public class RVAlertAdapter extends ListAdapter<DeviceModel, RVAlertAdapter.AlertViewHolder> {
    private final Context context;
    private OnDeviceItemListener listener;

    private static final DiffUtil.ItemCallback<DeviceModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<DeviceModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DeviceModel oldItem, @NonNull DeviceModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DeviceModel oldItem, @NonNull DeviceModel newItem) {
            return oldItem.getPercent().equals(newItem.getPercent()) &&
                    oldItem.getAlertMessage().equals(newItem.getAlertMessage());
        }
    };

    public RVAlertAdapter(Context context) {
        super(DIFF_CALLBACK);
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
        DeviceModel mAlert = getItem(position);
        holder.device = mAlert;
        holder.tv_name.setText(mAlert.getName());
        holder.tv_message.setText(mAlert.getAlertMessage());
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(mAlert.getIcon(), null));
    }

    public DeviceModel getItemAt(int position) {
        return getItem(position);
    }

    public void setListener(OnDeviceItemListener listener) {
        this.listener = listener;
    }

    class AlertViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_icon;
        private final TextView tv_name, tv_message;
        private DeviceModel device;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAlert_iv_device);
            tv_name=itemView.findViewById(R.id.itemAlert_tv_name);
            tv_message=itemView.findViewById(R.id.itemAlert_tv_message);

            itemView.setOnClickListener(v -> listener.OnItemClick(device));
            itemView.setOnLongClickListener(v -> {
                listener.OnItemLongClick(v,device);
                return true;
            });
        }
    }
}