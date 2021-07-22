package com.example.musev1.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.utility.SaveState;

import java.util.Objects;

public class SplashFragment extends Fragment {

    private Animation anim_left;
    private ImageView iv;
    private TextView tv_desc;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

        //StatusBar color
        if (SaveState.getDarkModeState())
            MainActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            MainActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        anim_left = AnimationUtils.loadAnimation(getContext(), R.anim.anim_left);
        iv = view.findViewById(R.id.splash_iv_light);
        tv_desc = view.findViewById(R.id.splash_tv_desc);
        iv.setAnimation(anim_left);
        tv_desc.setAnimation(anim_left);

        new Handler().postDelayed(() -> {
            if(isAdded())
                Navigation.findNavController(requireActivity(),R.id.start_fragment).navigate(R.id.action_splashFragment_to_loginFragment);
        },1800);
    }
}