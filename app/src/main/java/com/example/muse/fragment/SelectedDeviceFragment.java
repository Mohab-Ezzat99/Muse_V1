package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;

public class SelectedDeviceFragment extends Fragment implements View.OnClickListener {

    private String item;
    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;
    private TextView tv_item_device,tv_notif_device;
    private ImageView iv_item_icon;
    private CardView cv_insight,cv_goal,cv_schedules;
    private FloatingActionButton fab;
    private ImageView iv_calender;

    public SelectedDeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        return inflater.inflate(R.layout.fragment_selected_device, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_item_device=view.findViewById(R.id.selectedD_item_tv_device);
        iv_item_icon=view.findViewById(R.id.selectedD_item_iv_icon);
        tv_notif_device=view.findViewById(R.id.selectedD_notif_tv_device);
        cv_insight=view.findViewById(R.id.selectedD_cv_insight);
        cv_goal=view.findViewById(R.id.selectedD_cv_goal);
        cv_schedules=view.findViewById(R.id.selectedD_cv_schedule);
        fab=view.findViewById(R.id.selectedD_fab);
        iv_calender=view.findViewById(R.id.selectedD_iv_cat);

        if (getArguments() != null) {
            SelectedDeviceFragmentArgs args=SelectedDeviceFragmentArgs.fromBundle(getArguments());
            item= args.getName();
            displayItem(item);
        }

        cv_insight.setOnClickListener(this);
        cv_goal.setOnClickListener(this);
        cv_schedules.setOnClickListener(this);
        fab.setOnClickListener(this);
        iv_calender.setOnClickListener(this);

        navControllerChart = Navigation.findNavController(requireActivity(), R.id.selectedD_fragment);
        chipNavigationBar =view.findViewById(R.id.selectedD_chipNav);
        chipNavigationBar.setItemSelected(R.id.dayFragment,true);

        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.dayFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartDayFragment);
                    return;

                case R.id.weekFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartWeekFragment);
                    return;

                case R.id.monthFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartMonthFragment);
                    return;

                case R.id.yearFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartYearFragment);
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void displayItem(String item) {
        switch (item) {
            case StartActivity.TV:
                //item
                iv_item_icon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_tv, null));
                tv_item_device.setText(StartActivity.TV);
                //notifications
                tv_notif_device.setText(StartActivity.TV);
                break;

            case StartActivity.FRIDGE:
                //item
                iv_item_icon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_fridge, null));
                tv_item_device.setText(StartActivity.FRIDGE);
                //notifications
                tv_notif_device.setText(StartActivity.FRIDGE);
                break;

            case StartActivity.AIR:
                //item
                iv_item_icon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_air_conditioner, null));
                tv_item_device.setText(StartActivity.AIR);
                //notifications
                tv_notif_device.setText(StartActivity.AIR);
                break;

            case StartActivity.DEVICE:
                //item
                iv_item_icon.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_plug, null));
                tv_item_device.setText(StartActivity.DEVICE);
                //notifications
                tv_notif_device.setText(StartActivity.DEVICE);
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectedD_cv_insight:
            case R.id.selectedD_cv_goal:
            case R.id.selectedD_cv_schedule:
            case R.id.selectedD_fab:
            case R.id.selectedD_iv_cat:
                Toast.makeText(getContext(), "Soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}