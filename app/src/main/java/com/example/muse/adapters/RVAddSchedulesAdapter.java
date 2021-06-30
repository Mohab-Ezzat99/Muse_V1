package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ASViewHolder holder, int position) {
        DeviceModel deviceModel = getItem(position);
//        holder.tv_name.setText(deviceModel.getName());
    }

    public DeviceModel getItemAt(int position) {
        return getItem(position);
    }

    static class ASViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;

        public ASViewHolder(@NonNull View itemView) {
            super(itemView);
//            tv_name=itemView.findViewById(R.id.dialogSchedule_tv_name);

        }
    }
}
