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
import com.example.muse.model.ScheduleModel;

public class RVAddSchedulesAdapter extends ListAdapter<ScheduleModel, RVAddSchedulesAdapter.ASViewHolder> {
    private final Context context;
    private static final DiffUtil.ItemCallback<ScheduleModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ScheduleModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ScheduleModel oldItem, @NonNull ScheduleModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ScheduleModel oldItem, @NonNull ScheduleModel newItem) {
            return oldItem.getDescription().equals(newItem.getDescription());
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
        ScheduleModel schedule_model = getItem(position);
        holder.tv_decs.setText(schedule_model.getDescription());
        switch (schedule_model.getPictureId()) {
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

    public ScheduleModel getItemAt(int position) {
        return getItem(position);
    }

    static class ASViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_decs;
        private ImageView iv_icon;

        public ASViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAS_iv_icon);
            tv_decs = itemView.findViewById(R.id.itemAS_tv_dec);
        }
    }
}
