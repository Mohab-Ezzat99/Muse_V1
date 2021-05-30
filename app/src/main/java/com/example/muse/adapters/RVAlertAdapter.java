package com.example.muse.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.model.DeviceModel;

import java.util.ArrayList;

public class RVAlertAdapter extends RecyclerView.Adapter<RVAlertAdapter.AlertViewHolder> {

    private ArrayList<DeviceModel> mDevice = new ArrayList<>();
    private Context context;

    public RVAlertAdapter(Context context) {
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
        DeviceModel mAlert = mDevice.get(position);
        holder.tv_name.setText(mAlert.getName());
        holder.tv_message.setText(mAlert.getAlertMessage());
        switch (mAlert.getName()) {
            case "TV":
                holder.iv_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_tv));
                break;
            case "Air Conditioner":
                holder.iv_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_air_conditioner));
                break;
            case "Fridge":
                holder.iv_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fridge));
                break;
            default:
                holder.iv_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plug));
                break;
        }    }

    @Override
    public int getItemCount() {
        return mDevice.size();
    }

    public void addItem(DeviceModel mAlert) {
        this.mDevice.add(mAlert);
        notifyDataSetChanged();
    }

    public class AlertViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private TextView tv_name, tv_message;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_icon=itemView.findViewById(R.id.itemAlert_iv_device);
            tv_name=itemView.findViewById(R.id.itemAlert_tv_name);
            tv_message=itemView.findViewById(R.id.itemAlert_tv_message);

        }
    }
}