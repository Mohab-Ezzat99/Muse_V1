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

public class RVAddCustomAlertAdapter extends ListAdapter<DeviceModel, RVAddCustomAlertAdapter.ASViewHolder> {
    private final Context context;
    private static final DiffUtil.ItemCallback<DeviceModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<DeviceModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DeviceModel oldItem, @NonNull DeviceModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DeviceModel oldItem, @NonNull DeviceModel newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };

    public RVAddCustomAlertAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ASViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ASViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_custom_alert, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ASViewHolder holder, int position) {
        DeviceModel deviceModel = getItem(position);
        holder.tv_name.setText(deviceModel.getName());
        holder.iv_icon.setImageResource(deviceModel.getIcon());
        if(deviceModel.isAlertOn())
            holder.tv_state.setText("ON");
        else
            holder.tv_state.setText("OFF");
        holder.tv_type.setText(deviceModel.getTime_type());
        holder.tv_time.setText(deviceModel.getTime());
    }

    public DeviceModel getItemAt(int position) {
        return getItem(position);
    }

    static class ASViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name,tv_state,tv_type,tv_time;
        private ImageView iv_icon;

        public ASViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.itemAC_tv_name);
            iv_icon=itemView.findViewById(R.id.itemAC_iv_icon);
            tv_state=itemView.findViewById(R.id.itemAC_tv_state);
            tv_type=itemView.findViewById(R.id.itemAC_tv_type);
            tv_time=itemView.findViewById(R.id.itemAC_tv_time);
        }
    }
}
