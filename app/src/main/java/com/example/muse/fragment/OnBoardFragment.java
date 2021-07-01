package com.example.muse.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.muse.MainActivity;
import com.example.muse.utility.NonSwipeableViewPager;
import com.example.muse.R;
import com.example.muse.adapters.VPOnBoardAdapter;
import com.example.muse.utility.SaveState;

import java.util.ArrayList;
import java.util.Objects;

public class OnBoardFragment extends Fragment {

    public OnBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        return inflater.inflate(R.layout.fragment_on_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        if (SaveState.getDarkModeState())
            MainActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            MainActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));

        NonSwipeableViewPager viewPager=view.findViewById(R.id.onBoard_vp);
        ArrayList<Fragment> screens=new ArrayList<>();
        screens.add(new OnFirstFragment());
        screens.add(new OnSecondFragment());
        screens.add(new OnThirdFragment());
        screens.add(new OnFourthFragment());
        VPOnBoardAdapter adapter=new VPOnBoardAdapter(requireActivity().getSupportFragmentManager(),0);
        adapter.setScreens(screens);
        viewPager.setAdapter(adapter);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }
}