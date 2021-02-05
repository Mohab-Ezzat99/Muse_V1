package com.example.muse;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.muse.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterBinding activityRegisterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());

        activityRegisterBinding.registerTvLogin.setOnClickListener(this);
        activityRegisterBinding.registerBtnRegister.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_tv_login:
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.register_btn_register:
                Intent intent2 = new Intent(getBaseContext(), OnBoardingActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}