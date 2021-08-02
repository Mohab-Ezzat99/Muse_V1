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
import com.example.musev1.interfaces.OnAlertItemListener;
import com.example.musev1.interfaces.OnDeviceItemListener;
import com.example.musev1.model.AlertModel;

public class RVAlertAdapter extends ListAdapter<AlertModel, RVAlertAdapter.AlertViewHolder> {
    private final Context context;
    private OnAlertItemListener listener;

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

        holder.iv_icon.setImageResource(mAlert.getPictureId());
        holder.tv_name.setText(mAlert.getDeviceName());
        holder.tv_desc.setText(mAlert.getDescription());
    }

    public AlertModel getItemAt(int position) {
        return getItem(position);
    }

    public void setListener(OnAlertItemListener listener) {
        this.listener = listener;
    }

    class AlertViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_icon;
        private final TextView tv_name, tv_desc;
        private AlertModel alertModel;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemAlert_iv_device);
            tv_name = itemView.findViewById(R.id.itemAlert_tv_name);
            tv_desc = itemView.findViewById(R.id.itemAlert_tv_desc);

            itemView.setOnClickListener(v -> listener.OnItemClick(alertModel));
        }
    }
}