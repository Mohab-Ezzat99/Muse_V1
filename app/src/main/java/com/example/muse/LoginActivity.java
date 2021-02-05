package com.example.muse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.muse.databinding.ActivityLoginBinding;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        activityLoginBinding.loginTvForgot.setOnClickListener(this);
        activityLoginBinding.loginTvSignUp.setOnClickListener(this);
        activityLoginBinding.loginBtnLogin.setOnClickListener(this);
        activityLoginBinding.loginFabFb.setOnClickListener(this);
        activityLoginBinding.loginFabGoogle.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_tv_forgot:
                displayToast("Forgot password");
                break;

            case R.id.login_tv_signUp:
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.login_btn_login:
                Intent intent2 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent2);
                finish();
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}