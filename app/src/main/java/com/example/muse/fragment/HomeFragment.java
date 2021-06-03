package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.utility.SaveState;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;


public class HomeFragment extends Fragment {

    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navControllerChart = Navigation.findNavController(requireActivity(), R.id.FHome_fragment);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        if (SaveState.getDarkModeState())
            StartActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            StartActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

        //iv_custom
        view.findViewById(R.id.FHome_iv_custom).setOnClickListener(v -> Toast.makeText(getContext(), "Soon", Toast.LENGTH_SHORT).show());

        //chipNav
        chipNavigationBar = view.findViewById(R.id.FHome_chipNav);
        chipNavigationBar.setItemSelected(R.id.dayFragment, true);
        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i) {
                case R.id.dayFragment:
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

                case R.id.yearFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartYearFragment);
            }
        });
    }
}