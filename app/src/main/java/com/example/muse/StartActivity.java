package com.example.muse;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.muse.utility.SaveState;

public class StartActivity extends AppCompatActivity {
    public static final String TV = "TV";
    public static final String FRIDGE = "Fridge";
    public static final String AIR = "Air Conditioner";
    public static final String DEVICE = "Another device";

    public NavController navControllerStart;
    public Toolbar toolbar;
    public SaveState saveState;
    public static int color;

    private static Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        saveState=new SaveState(this);
        navControllerStart = Navigation.findNavController(this, R.id.start_fragment);

        // toolbar
        toolbar =findViewById(R.id.start_tool);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleStyle);
        setSupportActionBar(toolbar);
        activeActionBar(this);

        //color secondary for dark mode
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(R.attr.colorOnSecondary, typedValue, true);
        color = typedValue.data;

        window = this.getWindow();
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

    public static void setupLightStatusBar(int statusColor) {
        // status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusColor);
        int flag = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.getDecorView().setSystemUiVisibility(flag);
    }

    public static void setupBackgroundStatusBar(int statusColor) {
        // status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusColor);
        int flags = window.getDecorView().getSystemUiVisibility(); // get current flag
        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
        window.getDecorView().setSystemUiVisibility(flags);
    }
}