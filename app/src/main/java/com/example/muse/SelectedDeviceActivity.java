package com.example.muse;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.muse.databinding.ActivitySelectedDeviceBinding;
import com.example.muse.fragment.ChartDayFragment;
import com.example.muse.fragment.ChartMonthFragment;
import com.example.muse.fragment.ChartWeekFragment;
import com.example.muse.fragment.ChartYearFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class SelectedDeviceActivity extends AppCompatActivity {

    private ActivitySelectedDeviceBinding activitySelectedDeviceBinding;
    private final String TV = "TV";
    private final String FRIDGE = "Fridge";
    private final String AIR = "Air Conditioner";
    private final String DEVICE = "Another device";

    private String item;
    private ChipNavigationBar chipNavigationBar;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySelectedDeviceBinding = ActivitySelectedDeviceBinding.inflate(getLayoutInflater());
        setContentView(activitySelectedDeviceBinding.getRoot());

        item = getIntent().getStringExtra("NAME");
        displayItem(item);



        chipNavigationBar =findViewById(R.id.selectedD_chipNav);
        chipNavigationBar.setItemSelected(R.id.dayFragment,true);
        loadFragment(new ChartDayFragment());

        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.dayFragment :
                    loadFragment(new ChartDayFragment());
                    return;

                case R.id.weekFragment :
                    loadFragment(new ChartWeekFragment());
                    return;

                case R.id.monthFragment :
                    loadFragment(new ChartMonthFragment());
                    return;

                case R.id.yearFragment :
                    loadFragment(new ChartYearFragment());
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void displayItem(String item) {
        switch (item) {
            case TV:
                //title
                activitySelectedDeviceBinding.selectedDTvTitle.setText(TV);
                //item
                activitySelectedDeviceBinding.selectedDItemIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_tv, null));
                activitySelectedDeviceBinding.selectedDItemTvDevice.setText(TV);
                //notifications
                activitySelectedDeviceBinding.selectedDNotifTvDevice.setText(TV);
                //goal
                activitySelectedDeviceBinding.selectedDGoalIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_tv, null));
                activitySelectedDeviceBinding.selectedDGoalTvName.setText(TV);
                //schedule
                activitySelectedDeviceBinding.selectedDScheduleTvName.setText(TV);
                break;

            case FRIDGE:
                //title
                activitySelectedDeviceBinding.selectedDTvTitle.setText(FRIDGE);
                //item
                activitySelectedDeviceBinding.selectedDItemIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_fridge, null));
                activitySelectedDeviceBinding.selectedDItemTvDevice.setText(FRIDGE);
                //notifications
                activitySelectedDeviceBinding.selectedDNotifTvDevice.setText(FRIDGE);
                //goal
                activitySelectedDeviceBinding.selectedDGoalIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_fridge, null));
                activitySelectedDeviceBinding.selectedDGoalTvName.setText(FRIDGE);
                //schedule
                activitySelectedDeviceBinding.selectedDScheduleTvName.setText(FRIDGE);
                break;

            case AIR:
                //title
                activitySelectedDeviceBinding.selectedDTvTitle.setText(AIR);
                //item
                activitySelectedDeviceBinding.selectedDItemIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_air_conditioner, null));
                activitySelectedDeviceBinding.selectedDItemTvDevice.setText(AIR);
                //notifications
                activitySelectedDeviceBinding.selectedDNotifTvDevice.setText(AIR);
                //goal
                activitySelectedDeviceBinding.selectedDGoalIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_air_conditioner, null));
                activitySelectedDeviceBinding.selectedDGoalTvName.setText(AIR);
                //schedule
                activitySelectedDeviceBinding.selectedDScheduleTvName.setText(AIR);
                break;

            case DEVICE:
                //title
                activitySelectedDeviceBinding.selectedDTvTitle.setText(DEVICE);
                //item
                activitySelectedDeviceBinding.selectedDItemIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_plug, null));
                activitySelectedDeviceBinding.selectedDItemTvDevice.setText(DEVICE);
                //notifications
                activitySelectedDeviceBinding.selectedDNotifTvDevice.setText(DEVICE);
                //goal
                activitySelectedDeviceBinding.selectedDGoalIvIcon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_plug, null));
                activitySelectedDeviceBinding.selectedDGoalTvName.setText(DEVICE);
                //schedule
                activitySelectedDeviceBinding.selectedDScheduleTvName.setText(DEVICE);
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.selectedD_fragment,fragment).commit();
    }
}