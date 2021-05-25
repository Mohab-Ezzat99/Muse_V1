package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.model.MNavMenu;
import com.example.muse.utility.SaveState;

import java.util.ArrayList;

public class RVNavMenuAdapter extends RecyclerView.Adapter<RVNavMenuAdapter.NMViewHolder> {

    private ArrayList<MNavMenu> MNavMenus = new ArrayList<>();
    private OnItemClickListener listener;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public NMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NMViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NMViewHolder holder, int position) {
        MNavMenu MNavMenu = MNavMenus.get(position);
        holder.iv_icon.setImageDrawable(MNavMenu.getIcon());
        holder.tv_name.setText(MNavMenu.getName());
        holder.pos=position;
        if (position == 3) {
            holder.switchCompat.setVisibility(View.VISIBLE);
            if(SaveState.getDarkModeState())
                holder.switchCompat.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return MNavMenus.size();
    }

    public void addItem(MNavMenu MNavMenu) {
        this.MNavMenus.add(MNavMenu);
        notifyDataSetChanged();

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void isDarkModeChecked(boolean isChecked);
    }

    public class NMViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_name;
        SwitchCompat switchCompat;
        int pos;
        boolean isDarkModeChecked;

        public NMViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.itemNM_iv_icon);
            tv_name = itemView.findViewById(R.id.itemNM_name);
            switchCompat = itemView.findViewById(R.id.itemNM_switch);

            itemView.setOnClickListener(v -> listener.onItemClick(pos));
            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                isDarkModeChecked=isChecked;
                listener.isDarkModeChecked(isDarkModeChecked);
            });
        }
    }
}
