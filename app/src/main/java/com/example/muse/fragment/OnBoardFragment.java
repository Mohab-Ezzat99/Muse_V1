package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.muse.NonSwipeableViewPager;
import com.example.muse.R;
import com.example.muse.adapters.VPOnBoardAdapter;

import java.util.ArrayList;

public class OnBoardFragment extends Fragment {

    public OnBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NonSwipeableViewPager viewPager=view.findViewById(R.id.onBoard_vp);
        ArrayList<Fragment> screens=new ArrayList<>();
        screens.add(new OnFirstFragment());
        screens.add(new OnSecondFragment());
        screens.add(new OnThirdFragment());
        screens.add(new OnFourthFragment());
        VPOnBoardAdapter adapter=new VPOnBoardAdapter(requireActivity().getSupportFragmentManager(),0);
        adapter.setScreens(screens);
        viewPager.setAdapter(adapter);
    }
}