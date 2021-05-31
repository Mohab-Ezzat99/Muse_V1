package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.utility.SaveState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView tv_forgot, tv_signUp;
    private Button btn_login;
    private NavController navController;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_login, container, false);
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

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

        tv_forgot = view.findViewById(R.id.login_tv_forgot);
        tv_signUp = view.findViewById(R.id.login_tv_signUp);
        btn_login = view.findViewById(R.id.login_btn_login);

        tv_forgot.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_tv_forgot:
                displayToast("Forgot password");
                break;

            case R.id.login_tv_signUp:
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
                break;

            case R.id.login_btn_login:
                navController.navigate(R.id.action_loginFragment_to_mainFragment);
                break;
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}