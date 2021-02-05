package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.muse.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private ChipNavigationBar chipNavigationBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // status bar color
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.white, null));
        int flag = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.getDecorView().setSystemUiVisibility(flag);

        chipNavigationBar =view.findViewById(R.id.FHome_chipNav);
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

    private void loadFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FHome_fragment,fragment).commit();
    }
}