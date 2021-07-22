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
import com.example.musev1.model.NavMenuModel;
import com.example.musev1.utility.SaveState;

import java.util.ArrayList;

public class RVNavMenuAdapter extends RecyclerView.Adapter<RVNavMenuAdapter.NMViewHolder> {
    private ArrayList<NavMenuModel> NavMenuModels = new ArrayList<>();
    private OnItemClickListener listener;
    private final Context context;

    public RVNavMenuAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public NMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NMViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_menu, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull NMViewHolder holder, int position) {
        NavMenuModel NavMenuModel = NavMenuModels.get(position);
        holder.iv_icon.setImageDrawable(context.getResources().getDrawable(NavMenuModel.getIcon(),null));
        holder.tv_name.setText(NavMenuModel.getName());
        holder.pos=position;
        if (position == 2 || position == 3 || position==4) {
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
        return NavMenuModels.size();
    }

    public void addItem(NavMenuModel NavMenuModel) {
        this.NavMenuModels.add(NavMenuModel);
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
                //dark mode
                if (pos == 3)
                    listener.isDarkModeChecked(isChecked);
                //notification
                else
                    listener.isNotificationChecked(isChecked);
            });
        }
    }
}
