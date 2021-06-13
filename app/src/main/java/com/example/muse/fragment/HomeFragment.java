package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.Calendar;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;
    private ImageView iv_custom;
    private int day, month, year;

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
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        iv_custom = view.findViewById(R.id.FHome_iv_custom);
        iv_custom.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext()
                    , android.R.style.Theme_Holo_Light_Dialog_MinWidth, (view1, year, month, dayOfMonth) -> Toast.makeText(getContext(), dayOfMonth + "/" + (++month) + "/" + year, Toast.LENGTH_SHORT).show(), year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackground(null);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackground(null);
        });

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