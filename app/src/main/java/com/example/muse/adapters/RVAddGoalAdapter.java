package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.model.DeviceModel;

public class RVAddGoalAdapter extends ListAdapter<DeviceModel, RVAddGoalAdapter.AGViewHolder> {
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

    public RVAddGoalAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public AGViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AGViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_goal, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull AGViewHolder holder, int position) {
        DeviceModel deviceModel = getItem(position);
        holder.tv_name.setText(deviceModel.getName());
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(deviceModel.getIcon(),null));

        if (position % 2 == 0) {
            holder.tv_status.setText(R.string.txt_goal_will_be_achieved);
        } else {
            holder.tv_status.setText(R.string.txt_goal_will_not_achieved);
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    public DeviceModel getItemAt(int position) {
        return getItem(position);
    }

    static class AGViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_icon;
        private final TextView tv_name, tv_status;

        public AGViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAG_iv_icon);
            tv_name = itemView.findViewById(R.id.itemAG_tv_name);
            tv_status = itemView.findViewById(R.id.itemAG_TPre1V);
        }
    }
}
