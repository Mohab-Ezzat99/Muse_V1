package com.example.muse.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class VPOnBoardAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> screens=new ArrayList<>();

    public VPOnBoardAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return screens.get(position);
    }

    @Override
    public int getCount() {
        return screens.size();
    }

    public void setScreens(ArrayList<Fragment> screens) {
        this.screens = screens;
        notifyDataSetChanged();
    }
}
