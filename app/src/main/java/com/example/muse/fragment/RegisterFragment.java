package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.utility.SaveState;

import java.util.Objects;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private TextView tv_login;
    private Button btn_register;
    private NavController navController;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController=Navigation.findNavController(requireActivity(),R.id.start_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        if (SaveState.getDarkModeState())
            StartActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            StartActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));

        tv_login=view.findViewById(R.id.register_tv_login);
        btn_register=view.findViewById(R.id.register_btn_register);

        tv_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        navController.navigate(R.id.action_registerFragment_to_loginFragment);
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_tv_login:
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
                break;
            case R.id.register_btn_register:
                navController.navigate(R.id.action_registerFragment_to_onBoardFragment);
                break;
        }
    }
}