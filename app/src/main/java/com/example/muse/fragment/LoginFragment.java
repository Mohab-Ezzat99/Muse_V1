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

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView tv_forgot, tv_signUp;
    private Button btn_login;
    private FloatingActionButton fab_fb, fab_google;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //status bar color
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.cyan, null));
        int flags = window.getDecorView().getSystemUiVisibility(); // get current flag
        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
        window.getDecorView().setSystemUiVisibility(flags);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

        tv_forgot = view.findViewById(R.id.login_tv_forgot);
        tv_signUp = view.findViewById(R.id.login_tv_signUp);
        btn_login = view.findViewById(R.id.login_btn_login);
        fab_fb = view.findViewById(R.id.login_fab_fb);
        fab_google = view.findViewById(R.id.login_fab_google);

        tv_forgot.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        fab_fb.setOnClickListener(this);
        fab_google.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_tv_forgot:
                displayToast("Forgot password");
                break;

            case R.id.login_tv_signUp:
                StartActivity.navControllerStart.navigate(R.id.action_loginFragment_to_registerFragment);
                break;

            case R.id.login_btn_login:
                StartActivity.navControllerStart.navigate(R.id.action_loginFragment_to_mainFragment);
                break;

            case R.id.login_fab_fb:
                displayToast("Facebook");
                break;

            case R.id.login_fab_google:
                displayToast("Google");
                break;
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}