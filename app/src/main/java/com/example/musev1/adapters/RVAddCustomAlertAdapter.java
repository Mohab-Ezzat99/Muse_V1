package com.example.musev1.adapters;

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

import com.example.musev1.R;
import com.example.musev1.model.CustomAlertModel;

public class RVAddCustomAlertAdapter extends ListAdapter<CustomAlertModel, RVAddCustomAlertAdapter.ASViewHolder> {
    private static final DiffUtil.ItemCallback<CustomAlertModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<CustomAlertModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull CustomAlertModel oldItem, @NonNull CustomAlertModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CustomAlertModel oldItem, @NonNull CustomAlertModel newItem) {
            return oldItem.getDeviceName().equals(newItem.getDeviceName()) &&
                    oldItem.getId() == (newItem.getId()) &&
                    oldItem.getPictureId() == (newItem.getPictureId()) &&
                    oldItem.getDeviceId() == (newItem.getDeviceId()) &&
                    oldItem.getState().equals(newItem.getState()) &&
                    oldItem.getForPeriod().equals(newItem.getForPeriod()) &&
                    oldItem.getAtTime().equals(newItem.getAtTime()) &&
                    oldItem.getMaxUsage().equals(newItem.getMaxUsage());
        }
    };

    public RVAddCustomAlertAdapter() {
        super(DIFF_CALLBACK);
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
        CustomAlertModel customAlertModel = getItem(position);
        holder.iv_icon.setImageResource(customAlertModel.getPictureId());
        holder.tv_name.setText(customAlertModel.getDeviceName());
        holder.tv_state.setText(customAlertModel.getState());
        holder.tv_max.setText(customAlertModel.getMaxUsage());

        if (!customAlertModel.getAtTime().equals("")) {
            holder.tv_later.setText("At");
            holder.tv_period.setText(customAlertModel.getAtTime());
        }

        if (!customAlertModel.getForPeriod().equals("")) {
            holder.tv_later.setText("After");
            holder.tv_period.setText(customAlertModel.getForPeriod());
        }
    }

    public CustomAlertModel getItemAt(int position) {
        return getItem(position);
    }

    static class ASViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_state, tv_later, tv_period, tv_max;
        private ImageView iv_icon;

        public ASViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon=itemView.findViewById(R.id.itemAC_iv_icon);
            tv_name = itemView.findViewById(R.id.itemAC_tv_name);
            tv_state = itemView.findViewById(R.id.itemAC_tv_state);
            tv_later = itemView.findViewById(R.id.itemAC_tv_later);
            tv_period = itemView.findViewById(R.id.itemAC_tv_period);
            tv_max = itemView.findViewById(R.id.itemAC_tv_max);
        }
    }
}
