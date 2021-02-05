package com.example.muse.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.model.MNavMenu;

import java.util.ArrayList;

public class RVNavMenuAdapter extends RecyclerView.Adapter<NMViewHolder> {

    private ArrayList<MNavMenu> MNavMenus = new ArrayList<>();

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public NMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NMViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NMViewHolder holder, int position) {
        MNavMenu MNavMenu = MNavMenus.get(position);
        holder.iv_icon.setImageDrawable(MNavMenu.getIcon());
        holder.tv_name.setText(MNavMenu.getName());

        if (position == MNavMenus.size() - 1)
            holder.switchCompat.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return MNavMenus.size();
    }

    public void addItem(MNavMenu MNavMenu){
        this.MNavMenus.add(MNavMenu);
        notifyDataSetChanged();
    }
}

class NMViewHolder extends RecyclerView.ViewHolder {

    ImageView iv_icon;
    TextView tv_name;
    SwitchCompat switchCompat;

    public NMViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.itemNM_iv_icon);
        tv_name = itemView.findViewById(R.id.itemNM_name);
        switchCompat = itemView.findViewById(R.id.itemNM_switch);
    }
}
