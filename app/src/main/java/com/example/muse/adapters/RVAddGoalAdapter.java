package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.model.GoalModel;

public class RVAddGoalAdapter extends ListAdapter<GoalModel, RVAddGoalAdapter.AGViewHolder> {
    private final Context context;
    private static final DiffUtil.ItemCallback<GoalModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<GoalModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull GoalModel oldItem, @NonNull GoalModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull GoalModel oldItem, @NonNull GoalModel newItem) {
            return oldItem.getPercent() == newItem.getUsed() &&
                    oldItem.getEstimation() == newItem.getUsed();
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
        GoalModel goalModel = getItem(position);
        switch (goalModel.getPictureId()) {
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
        holder.tv_used.setText(String.valueOf(goalModel.getUsed()));
        holder.tv_percent.setText(String.valueOf(goalModel.getPercent()));
        holder.progressBar.setProgress(goalModel.getPercent());
        if (goalModel.getEstimation() != 0) {
            holder.tv_prediction.setText(R.string.txt_goal_will_be_achieved);
        } else {
            holder.tv_prediction.setText(R.string.txt_goal_will_not_achieved);
            holder.tv_prediction.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    public GoalModel getItemAt(int position) {
        return getItem(position);
    }

    static class AGViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_icon;
        private final TextView tv_prediction, tv_used, tv_percent;
        private final ProgressBar progressBar;

        public AGViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAG_iv_icon);
            tv_prediction = itemView.findViewById(R.id.itemAG_predictionV);
            tv_used = itemView.findViewById(R.id.itemAG_tv_used);
            tv_percent = itemView.findViewById(R.id.itemAG_tv_percent);
            progressBar = itemView.findViewById(R.id.itemAG_pb);
        }
    }
}
