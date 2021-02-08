package com.example.muse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.muse.fragment.MainFragment;

public class StartActivity extends AppCompatActivity {
    public static final String TV = "TV";
    public static final String FRIDGE = "Fridge";
    public static final String AIR = "Air Conditioner";
    public static final String DEVICE = "Another device";

    @SuppressLint("StaticFieldLeak")
    public static NavController navControllerStart;
    @SuppressLint("StaticFieldLeak")
    public static Toolbar toolbar;
    @SuppressLint("StaticFieldLeak")
    public static TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        navControllerStart = Navigation.findNavController(this, R.id.start_fragment);

        // toolbar
        toolbar =findViewById(R.id.start_tool);
        mTitle = toolbar.findViewById(R.id.startTool_title);
        setSupportActionBar(toolbar);
        activeActionBar(this);
    }

    public static void activeActionBar(Context baseContext){
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.selectedDeviceFragment)
                .build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) baseContext,navControllerStart,appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return MainFragment.navControllerMain.navigateUp() || super.onSupportNavigateUp();
    }
}