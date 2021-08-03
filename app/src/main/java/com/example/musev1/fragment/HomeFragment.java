package com.example.musev1.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.databinding.FragmentHomeBinding;
import com.example.musev1.utility.SaveState;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Calendar;
import java.util.Objects;


public class HomeFragment extends Fragment implements MenuItem.OnMenuItemClickListener {
    // charts
    private NavController navControllerChart;

    // date
    private int day, month, year;
    private DatePickerDialog datePickerDialog;

    // spinner
    private boolean realtimeSwitch;
    private int unitPos=0, chipAgg =0;
    private FragmentHomeBinding binding;

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

        //hide action bar
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

        // init main graph
        navControllerChart = Navigation.findNavController(requireActivity(), R.id.FHome_fragment);

        //StatusBar color
        if (SaveState.getDarkModeState())
            MainActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            MainActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentHomeBinding.bind(view);

        //iv_custom
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        binding.FHomeIvCustom.setOnClickListener(this::showPopup);

        //display insight info depend on unit spinner
        binding.FHomeSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitPos = position;
                initData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //chipNav
        binding.FHomeChipNav.setItemSelected(R.id.dayFragment, true);
        binding.FHomeChipNav.setOnItemSelectedListener(i -> {
            switch (i) {
                case R.id.dayFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartDayFragment);
                    chipAgg = 0;
                    initData();
                    return;

                case R.id.weekFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartWeekFragment);
                    chipAgg = 1;
                    initData();
                    return;

                case R.id.monthFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartMonthFragment);
                    chipAgg = 2;
                    initData();
                    return;

                case R.id.yearFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartYearFragment);
                    chipAgg = 3;
                    initData();
            }
        });

        // arrow info
        binding.FHomeIvArrow.setOnClickListener(v -> {
            if (binding.constLayoutExpanded.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.FHomeCvInsight, new AutoTransition());
                binding.constLayoutExpanded.setVisibility(View.VISIBLE);
                binding.FHomeIvArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            } else {
                TransitionManager.beginDelayedTransition(binding.FHomeCvInsight, new Fade());
                binding.constLayoutExpanded.setVisibility(View.GONE);
                binding.FHomeIvArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        //refresh realtime
        updateRealtime();
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
        popupMenu.inflate(R.menu.menu_custom);
        popupMenu.show();
    }

    // click on custom menu
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuCustom_day:
                displayDatePicker(0);
                return true;

            case R.id.menuCustom_month:
                displayDatePicker(1);
                datePickerDialog.findViewById(Resources.getSystem()
                        .getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                return true;

            case R.id.menuCustom_year:
                displayDatePicker(2);
                datePickerDialog.findViewById(Resources.getSystem()
                        .getIdentifier("day", "id", "android")).setVisibility(View.GONE);

                datePickerDialog.findViewById(Resources.getSystem()
                        .getIdentifier("month", "id", "android")).setVisibility(View.GONE);
                return true;

            default:
                return false;
        }
    }

    // display data dialog depend on custom choice
    public void displayDatePicker(int position) {
        DatePickerDialog.OnDateSetListener onDateSetListener= (view, year, month, dayOfMonth) -> {
            if (position == 0) {
                Toast.makeText(getContext(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
            } else if (position == 1) {
                Toast.makeText(getContext(), (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), year + "", Toast.LENGTH_SHORT).show();
            }
        };
        datePickerDialog = new DatePickerDialog(getContext()
                , android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackground(null);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackground(null);
    }

    @SuppressLint("SetTextI18n")
    public void updateRealtime() {

        if (realtimeSwitch) {
            realtimeSwitch = false;
            if (unitPos == 0)
                binding.FHomeTvCurrentV.setText("30 W");
            else
                binding.FHomeTvCurrentV.setText("15 EGP");
        } else {
            realtimeSwitch = true;
            if (unitPos == 0)
                binding.FHomeTvCurrentV.setText("45 W");
            else
                binding.FHomeTvCurrentV.setText("22 EGP");
        }
        refresh();
        if (isAdded())
            Toast.makeText(requireContext(), "Realtime refreshed", Toast.LENGTH_SHORT).show();
    }

    public void refresh(){
        new Handler().postDelayed(this::updateRealtime, 10000);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        if (unitPos == 0) {
            switch (chipAgg) {
                case 0:
                    binding.FHomeTvPer.setText("Per day");
                    binding.FHomeTvAverageV.setText("80 W");
                    binding.FHomeTvConsumedV.setText("120 W");
                    binding.FHomeTvEstV.setText("250 W");
                    break;

                case 1:
                    binding.FHomeTvPer.setText("Per weak");
                    binding.FHomeTvAverageV.setText("107 W");
                    binding.FHomeTvConsumedV.setText("830 W");
                    binding.FHomeTvEstV.setText("1000 W");
                    break;

                case 2:
                    binding.FHomeTvPer.setText("Per month");
                    binding.FHomeTvAverageV.setText("11 KW");
                    binding.FHomeTvConsumedV.setText("127 KW");
                    binding.FHomeTvEstV.setText("150 KW");
                    break;

                case 3:
                    binding.FHomeTvPer.setText("Per year");
                    binding.FHomeTvAverageV.setText("160 KW");
                    binding.FHomeTvConsumedV.setText("360 KW");
                    binding.FHomeTvEstV.setText("500 KW");
                    break;
            }
            if (realtimeSwitch)
                binding.FHomeTvCurrentV.setText("30 W");
            else
                binding.FHomeTvCurrentV.setText("45 W");
        } else {
            switch (chipAgg) {
                case 0:
                    binding.FHomeTvPer.setText("Per day");
                    binding.FHomeTvAverageV.setText("40 EGP");
                    binding.FHomeTvConsumedV.setText("60 EGP");
                    binding.FHomeTvEstV.setText("120 EGP");
                    break;

                case 1:
                    binding.FHomeTvPer.setText("Per weak");
                    binding.FHomeTvAverageV.setText("53 EGP");
                    binding.FHomeTvConsumedV.setText("415 EGP");
                    binding.FHomeTvEstV.setText("500 EGP");
                    break;

                case 2:
                    binding.FHomeTvPer.setText("Per month");
                    binding.FHomeTvAverageV.setText("5500 EGP");
                    binding.FHomeTvConsumedV.setText("6350 EGP");
                    binding.FHomeTvEstV.setText("7500 EGP");
                    break;

                case 3:
                    binding.FHomeTvPer.setText("Per year");
                    binding.FHomeTvAverageV.setText("20000 EGP");
                    binding.FHomeTvConsumedV.setText("43800 EGP");
                    binding.FHomeTvEstV.setText("50300 EGP");
                    break;
            }

            if(!realtimeSwitch)
                binding.FHomeTvCurrentV.setText("15 EGP");
            else
                binding.FHomeTvCurrentV.setText("22 EGP");
        }
    }
}