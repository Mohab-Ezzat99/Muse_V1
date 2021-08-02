package com.example.musev1.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.R;
import com.example.musev1.model.GoalModel;

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

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull AGViewHolder holder, int position) {
        GoalModel goalModel = getItem(position);
        holder.iv_icon.setImageResource(goalModel.getPictureId());
        holder.tv_name.setText(goalModel.getDeviceName());
        holder.tv_used.setText(goalModel.getUsageLimit());
        if (position%2 != 0) {
            holder.tv_prediction.setText(R.string.txt_goal_will_be_achieved);
        } else {
            holder.tv_prediction.setText(R.string.txt_goal_will_not_achieved);
            holder.tv_prediction.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        switch (goalModel.getType()) {
            case 0:
                holder.tv_type.setText("Day");
                break;

            case 1:
                holder.tv_type.setText("Week");
                break;

            case 2:
                holder.tv_type.setText("Month");
                break;

            case 3:
                holder.tv_type.setText("Year");
        }
    }

    public GoalModel getItemAt(int position) {
        return getItem(position);
    }

    static class AGViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_icon;
        private final TextView tv_name,tv_prediction, tv_used, tv_type;

        public AGViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAG_iv_icon);
            tv_name = itemView.findViewById(R.id.itemAG_tv_name);
            tv_prediction = itemView.findViewById(R.id.itemAG_predictionV);
            tv_used = itemView.findViewById(R.id.itemAG_tv_used);
            tv_type = itemView.findViewById(R.id.itemAG_tv_type);
        }
    }
}
