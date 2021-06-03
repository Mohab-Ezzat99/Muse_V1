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
import androidx.recyclerview.widget.RecyclerView;
import com.example.muse.R;
import com.example.muse.model.DeviceModel;
import java.util.ArrayList;
import java.util.List;

public class RVAddGoalAdapter extends RecyclerView.Adapter<RVAddGoalAdapter.AGViewHolder> {
    private List<DeviceModel> deviceModels = new ArrayList<>();
    private Context context;

    public RVAddGoalAdapter(Context context) {
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
        DeviceModel deviceModel = deviceModels.get(position);
        holder.tv_name.setText(deviceModel.getName());
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(deviceModel.getIcon(),null));

        if (position % 2 == 0) {
            holder.iv_done.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checked));
            holder.tv_status.setText(R.string.txt_goal_will_be_achieved);
        } else {
            holder.iv_done.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_alarm));
            holder.tv_status.setText(R.string.txt_goal_will_not_achieved);
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return deviceModels.size();
    }

    public void addItem(DeviceModel itemAD) {
        this.deviceModels.add(itemAD);
        notifyDataSetChanged();
    }

    public void setList(List<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
        notifyDataSetChanged();
    }

    static class AGViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon, iv_done;
        private TextView tv_name, tv_status;

        public AGViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAG_iv_icon);
            iv_done = itemView.findViewById(R.id.itemAG_iv_result);
            tv_name = itemView.findViewById(R.id.itemAG_tv_name);
            tv_status = itemView.findViewById(R.id.itemAG_TPre1V);
        }
    }
}
