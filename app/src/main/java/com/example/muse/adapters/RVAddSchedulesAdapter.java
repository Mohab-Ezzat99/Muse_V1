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

public class RVAddSchedulesAdapter extends ListAdapter<DeviceModel, RVAddSchedulesAdapter.ASViewHolder> {
    private final Context context;
    private static final DiffUtil.ItemCallback<DeviceModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<DeviceModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DeviceModel oldItem, @NonNull DeviceModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DeviceModel oldItem, @NonNull DeviceModel newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getPercent().equals(newItem.getPercent()) &&
                    oldItem.getPercent().equals(newItem.getPercent());
        }
    };

    public RVAddSchedulesAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ASViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ASViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_schedules, parent, false));
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ASViewHolder holder, int position) {
        DeviceModel deviceModel = getItem(position);
        holder.tv_name.setText(deviceModel.getName());
        holder.iv_icon.setImageResource(deviceModel.getIcon());
        if (deviceModel.isAlertOn())
            holder.tv_state.setText("ON");
        else
            holder.tv_state.setText("OFF");
        holder.tv_type.setText(deviceModel.getTime_type());
        holder.tv_time.setText(deviceModel.getTime());
        if (deviceModel.getDays().length() == 0) {
            holder.tv_on.setVisibility(View.GONE);
            holder.tv_days.setVisibility(View.GONE);
        }
        else {
            holder.tv_days.setText(deviceModel.getDays());
            holder.tv_on.setVisibility(View.VISIBLE);
            holder.tv_days.setVisibility(View.VISIBLE);
        }
    }

    public DeviceModel getItemAt(int position) {
        return getItem(position);
    }

    static class ASViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_state, tv_type, tv_time, tv_on, tv_days;
        private ImageView iv_icon;

        public ASViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemAS_tv_name);
            iv_icon = itemView.findViewById(R.id.itemAS_iv_icon);
            tv_state = itemView.findViewById(R.id.itemAS_tv_state);
            tv_type = itemView.findViewById(R.id.itemAS_tv_type);
            tv_time = itemView.findViewById(R.id.itemAS_tv_time);
            tv_on = itemView.findViewById(R.id.itemAS_tv_on);
            tv_days = itemView.findViewById(R.id.itemAS_tv_days);
        }
    }
}
