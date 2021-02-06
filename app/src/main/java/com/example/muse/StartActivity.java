package com.example.muse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static NavController navControllerStart;
    public static final String TV = "TV";
    public static final String FRIDGE = "Fridge";
    public static final String AIR = "Air Conditioner";
    public static final String DEVICE = "Another device";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        navControllerStart = Navigation.findNavController(this, R.id.start_fragment);
    }
}