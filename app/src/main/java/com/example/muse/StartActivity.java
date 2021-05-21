package com.example.muse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.muse.fragment.MainFragment;

import java.time.format.TextStyle;

public class StartActivity extends AppCompatActivity {
    public static final String TV = "TV";
    public static final String FRIDGE = "Fridge";
    public static final String AIR = "Air Conditioner";
    public static final String DEVICE = "Another device";

    public NavController navControllerStart;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        navControllerStart = Navigation.findNavController(this, R.id.start_fragment);

        // toolbar
        toolbar =findViewById(R.id.start_tool);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleStyle);
        setSupportActionBar(toolbar);
        activeActionBar(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.main_fragment).navigateUp() || super.onSupportNavigateUp();
    }

    public void activeActionBar(Context baseContext){
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.selectedDeviceFragment)
                .build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) baseContext,navControllerStart,appBarConfiguration);
    }
}