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
import com.example.muse.model.AlertModel;

public class RVAlertAdapter extends ListAdapter<AlertModel, RVAlertAdapter.AlertViewHolder> {
    private final Context context;
    private OnDeviceItemListener listener;

    private static final DiffUtil.ItemCallback<AlertModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<AlertModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull AlertModel oldItem, @NonNull AlertModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlertModel oldItem, @NonNull AlertModel newItem) {
            return oldItem.getDescription().equals(newItem.getDescription());
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

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        AlertModel mAlert = getItem(position);
        holder.alertModel = mAlert;
        holder.tv_desc.setText(mAlert.getDescription());
        switch (mAlert.getPictureId()) {
            case 0:
                holder.iv_icon.setImageResource(R.drawable.ic_home);
                break;

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
    }

    public AlertModel getItemAt(int position) {
        return getItem(position);
    }

    public void setListener(OnDeviceItemListener listener) {
        this.listener = listener;
    }

    class AlertViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_icon;
        private final TextView tv_desc;
        private AlertModel alertModel;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAlert_iv_device);
            tv_desc=itemView.findViewById(R.id.itemAlert_tv_desc);

            itemView.setOnClickListener(v -> listener.OnItemClick(alertModel));
        }
    }
}