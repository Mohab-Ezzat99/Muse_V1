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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.muse.R;
import com.example.muse.model.DeviceModel;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

public class SelectedDeviceFragment extends Fragment implements View.OnClickListener {

    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;
    private TextView tv_name;
    private ImageView iv_icon, iv_setting;
    private CardView cv_insight, cv_goal, cv_schedules;
    private ImageView iv_custom;
    private DeviceModel device;
    private int day, month, year;

    public SelectedDeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (getArguments() != null) {
            SelectedDeviceFragmentArgs args = SelectedDeviceFragmentArgs.fromBundle(getArguments());
            device=args.getSelectedDevice();
        }

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(device.getName());
        return inflater.inflate(R.layout.fragment_selected_device, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_name = view.findViewById(R.id.selectedD_tv_name);
        iv_icon = view.findViewById(R.id.selectedD_iv_icon);
        cv_insight = view.findViewById(R.id.selectedD_cv_insight);
        cv_goal = view.findViewById(R.id.selectedD_cv_goal);
        cv_schedules = view.findViewById(R.id.selectedD_cv_schedule);
        iv_custom = view.findViewById(R.id.selectedD_iv_custom);
        iv_setting = view.findViewById(R.id.selectedD_iv_setting);
        displayItem();

        cv_insight.setOnClickListener(this);
        cv_goal.setOnClickListener(this);
        cv_schedules.setOnClickListener(this);
        iv_custom.setOnClickListener(this);
        iv_setting.setOnClickListener(this);

        //iv_custom
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

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
    private void displayItem() {
        iv_icon.setImageDrawable(getResources().getDrawable(device.getIcon(),null));
        tv_name.setText(device.getName());
    }

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.selectedD_cv_insight:
                displayDialog(R.layout.dialog_insight);
                break;

            case R.id.selectedD_cv_goal:
                View view = displayDialog(R.layout.dialog_goal);
                ImageView iv_icon = view.findViewById(R.id.dialogGoal_iv_icon);
                TextView tv_name = view.findViewById(R.id.dialogGoal_tv_name);
                iv_icon.setImageDrawable(getResources().getDrawable(device.getIcon(),null));
                tv_name.setText(device.getName());
                break;

            case R.id.selectedD_cv_schedule:
                View view1 = displayDialog(R.layout.dialog_schedules);
                TextView tv_name1 = view1.findViewById(R.id.dialogSchedule_tv_name);
                tv_name1.setText(device.getName());
                break;

            case R.id.selectedD_iv_setting:
                Navigation.findNavController(requireActivity(), R.id.main_fragment)
                        .navigate(SelectedDeviceFragmentDirections.actionSelectedDeviceFragmentToDeviceSettingFragment(device));
                break;

            case R.id.selectedD_iv_custom:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext()
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , (view2, year, month, dayOfMonth) ->
                        Toast.makeText(getContext(), dayOfMonth + "/" + (++month) + "/" + year, Toast.LENGTH_SHORT).show()
                        , year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackground(null);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackground(null);
                break;
        }
    }

    public View displayDialog(int layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(),R.style.DialogStyle);
        View view = LayoutInflater.from(requireContext()).inflate(layout, null, false);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return view;
    }
}