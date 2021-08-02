package com.example.musev1.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.R;
import com.example.musev1.model.NavMoreModel;
import com.example.musev1.utility.SaveState;

import java.util.ArrayList;

public class RVNavMoreAdapter extends RecyclerView.Adapter<RVNavMoreAdapter.NMViewHolder> {
    private ArrayList<NavMoreModel> navMoreModels = new ArrayList<>();
    private OnItemClickListener listener;
    private final Context context;

    public RVNavMoreAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public NMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NMViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_more, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull NMViewHolder holder, int position) {
        NavMoreModel NavMoreModel = navMoreModels.get(position);
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(NavMoreModel.getIcon(),null));
        holder.tv_name.setText(NavMoreModel.getName());
        holder.pos=position;
        if (position == 2 || position == 3) {
            holder.switchCompat.setVisibility(View.VISIBLE);
            switch (position){
                //notification
                case 2 :
                    holder.switchCompat.setChecked(SaveState.getNotificationState());
                    break;
                //dark mode
                case 3:
                    holder.switchCompat.setChecked(SaveState.getDarkModeState());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return navMoreModels.size();
    }

    public void addItem(NavMoreModel NavMoreModel) {
        this.navMoreModels.add(NavMoreModel);
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void isDarkModeChecked(boolean isChecked);

        void isNotificationChecked(boolean isChecked);
    }

    public class NMViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_icon;
        private final TextView tv_name;
        private final SwitchCompat switchCompat;
        private int pos;

        public NMViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemNM_iv_icon);
            tv_name = itemView.findViewById(R.id.itemNM_name);
            switchCompat = itemView.findViewById(R.id.itemNM_switch);

            itemView.setOnClickListener(v -> listener.onItemClick(pos));

            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                //notification
                if (pos == 2)
                    listener.isNotificationChecked(isChecked);
                //dark mode
                else
                    listener.isDarkModeChecked(isChecked);

            });
        }
    }
}
