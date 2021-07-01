package com.example.muse;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.muse.model.DeviceModel;
import com.example.muse.utility.SaveState;
import com.example.muse.viewmodels.MuseViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class StartActivity extends AppCompatActivity {
    public static final String TV = "TV";
    public static final String FRIDGE = "Fridge";
    public static final String AIR = "Air Conditioner";
    public static final String PC = "PC";
    public static final String CLOTHES_DRYER = "Clothes dryer";
    public static final String FREEZER = "Freezer";
    public static final String COFFEE_MAKER = "Coffee maker";
    public static final String DISHWASHER = "Dishwasher";
    public static final String FAN_HEATER = "Fan heater";
    public static final String TOASTER = "Toaster";
    public static final String WATER_DISPENSER = "Water dispenser";
    public static final String DEVICE = "Another device";

    public NavController navControllerStart;
    public Toolbar toolbar;
    public static int colorPrimaryVariant;
    public static int colorOnPrimary;
    public static int colorOnSecondary;
    public static ArrayList<DeviceModel> modelArrayList;

    public SaveState saveState;
    private static Window window;
    public static MuseViewModel museViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //set English
        Configuration config = this.getResources().getConfiguration();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        config.locale = locale;
        this.getResources().updateConfiguration(config,
                this.getResources().getDisplayMetrics());

        //init
        saveState=new SaveState(this);
        window = this.getWindow();
        navControllerStart = Navigation.findNavController(this, R.id.start_fragment);
        museViewModel = new ViewModelProvider(this).get(MuseViewModel.class);
        setupMode();

        // toolbar
        toolbar = findViewById(R.id.start_tool);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleStyle);
        setSupportActionBar(toolbar);
        activeActionBar(this);

        //declare devices
        modelArrayList=new ArrayList<>();
        if(modelArrayList.size()==0) {
            modelArrayList.add(new DeviceModel(R.drawable.ic_tv, TV));
            modelArrayList.add(new DeviceModel(R.drawable.ic_fridge, FRIDGE));
            modelArrayList.add(new DeviceModel(R.drawable.ic_air_conditioner, AIR));
            modelArrayList.add(new DeviceModel(R.drawable.ic_pc, PC));
            modelArrayList.add(new DeviceModel(R.drawable.ic_clothes_dryer, CLOTHES_DRYER));
            modelArrayList.add(new DeviceModel(R.drawable.ic_freezer, FREEZER));
            modelArrayList.add(new DeviceModel(R.drawable.ic_coffee_maker, COFFEE_MAKER));
            modelArrayList.add(new DeviceModel(R.drawable.ic_dishwasher, DISHWASHER));
            modelArrayList.add(new DeviceModel(R.drawable.ic_fan_heater, FAN_HEATER));
            modelArrayList.add(new DeviceModel(R.drawable.ic_toaster, TOASTER));
            modelArrayList.add(new DeviceModel(R.drawable.ic_water_dispenser, WATER_DISPENSER));
            modelArrayList.add(new DeviceModel(R.drawable.ic_plug, DEVICE));
        }

        //get some colors
        colorPrimaryVariant = fetchColor(R.attr.colorPrimaryVariant);
        colorOnPrimary = fetchColor(R.attr.colorOnPrimary);
        colorOnSecondary = fetchColor(R.attr.colorOnSecondary);
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

    public static void setupMode() {
        if (SaveState.getDarkModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public int fetchColor(int color){
        //colorPrimaryVariant for dark mode
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(color, typedValue, true);
        return typedValue.data;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}