package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.muse.R;
import com.example.muse.interfaces.OnADItemListener;
import com.example.muse.model.MAddDevice;
import java.util.ArrayList;

public class RVAddDeviceAdapter extends RecyclerView.Adapter<RVAddDeviceAdapter.ADViewHolder> {

    private ArrayList<MAddDevice> MAddDevices =new ArrayList<>();
    private OnADItemListener listener;

    public RVAddDeviceAdapter(OnADItemListener listener) {
        this.listener = listener;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ADViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ADViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_device, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ADViewHolder holder, int position) {
        MAddDevice MAddDevice = MAddDevices.get(position);
        holder.iv_icon.setImageDrawable(MAddDevice.getImage());
        holder.switchCompat.setChecked(MAddDevice.isChecked());
        holder.name=MAddDevice.getDevice();
        holder.tv_device.setText(holder.name);
        holder.tv_percent.setText(MAddDevice.getPercent());
        holder.progressBar.setProgress(MAddDevice.getProgress());
    }

    @Override
    public int getItemCount() {
        return MAddDevices.size();
    }

    public void addItem(MAddDevice MAddDevice){
        this.MAddDevices.add(MAddDevice);
        notifyDataSetChanged();
    }

    class ADViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        SwitchCompat switchCompat;
        TextView tv_device,tv_percent;
        ProgressBar progressBar;
        String name;

        public ADViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon=itemView.findViewById(R.id.itemAD_iv_icon);
            switchCompat=itemView.findViewById(R.id.itemAD_switch);
            tv_device=itemView.findViewById(R.id.itemAD_tv_device);
            tv_percent=itemView.findViewById(R.id.itemAD_tv_percent);
            progressBar=itemView.findViewById(R.id.itemAD_pb);

            itemView.setOnClickListener(v -> listener.OnItemClick(name));
        }
    }
}