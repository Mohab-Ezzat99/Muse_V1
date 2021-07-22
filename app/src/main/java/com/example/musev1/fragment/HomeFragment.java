package com.example.musev1.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
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
import com.example.musev1.model.GoalModel;
import com.example.musev1.model.InsightDataModel;
import com.example.musev1.utility.SaveState;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements MenuItem.OnMenuItemClickListener {
    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;
    private ImageView iv_custom, iv_arrow;

    private int day, month, year, homeId, unitOfData, chipAggregation;
    private DatePickerDialog datePickerDialog;

    private ConstraintLayout constLayout_expand;
    private CardView cv_insight;

    private Spinner spinnerUnit, spinnerAgg;
    private TextView tv_current, tv_average, tv_per, tv_consumedV, tv_estimation, tv_used, tv_prediction, tv_percent;
    private ProgressBar progressBar;

    private boolean notViewed = true;
    public static ArrayList<InsightDataModel> dataModels = new ArrayList<>();
    public ArrayList<GoalModel> goalModels = new ArrayList<>();

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
        navControllerChart.navigate(R.id.chartDayFragment);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        if (SaveState.getDarkModeState())
            MainActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            MainActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));

        /*
        //iv_custom
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        iv_custom = view.findViewById(R.id.FHome_iv_custom);
        iv_custom.setOnClickListener(this::showPopup);

        // insight inflation
        spinnerUnit = view.findViewById(R.id.home_spinner_unit);
        tv_current = view.findViewById(R.id.home_tv_currentV);
        tv_average = view.findViewById(R.id.home_tv_averageV);
        tv_consumedV = view.findViewById(R.id.home_tv_consumedV);
        tv_estimation = view.findViewById(R.id.home_tv_estV);

        // goal inflation
        spinnerAgg = view.findViewById(R.id.home_spinner_aggregation);
        tv_used = view.findViewById(R.id.home_tv_goalUsed);
        tv_prediction = view.findViewById(R.id.home_tv_predictionV);
        tv_percent = view.findViewById(R.id.home_tv_percent);
        progressBar = view.findViewById(R.id.home_pb);

        // chip inflation
        chipNavigationBar = view.findViewById(R.id.FHome_chipNav);
        tv_per = view.findViewById(R.id.home_tv_per);

        //default info
        getHouseInfo();

        //display insight info depend on unit spinner
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitOfData = position;
                MainActivity.displayLoadingDialog();
                getInsightReq(chipAggregation, unitOfData);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //catch goal info depend on aggregation
        spinnerAgg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGoalInfo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //chipNav
        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i) {
                case R.id.dayFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartDayFragment);

                    tv_per.setText("Per day");
                    chipAggregation = 1;
                    MainActivity.displayLoadingDialog();
                    getInsightReq(1, unitOfData);
                    return;

                case R.id.weekFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartWeekFragment);

                    tv_per.setText("Per week");
                    chipAggregation = 2;
                    MainActivity.displayLoadingDialog();
                    getInsightReq(2, unitOfData);
                    return;

                case R.id.monthFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartMonthFragment);

                    tv_per.setText("Per month");
                    chipAggregation = 3;
                    MainActivity.displayLoadingDialog();
                    getInsightReq(3, unitOfData);
                    return;

                case R.id.yearFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartYearFragment);

                    tv_per.setText("Per year");
                    chipAggregation = 4;
                    MainActivity.displayLoadingDialog();
                    getInsightReq(4, unitOfData);
            }
        });

        // arrow info
        constLayout_expand=view.findViewById(R.id.constLayoutExpanded);
        iv_arrow=view.findViewById(R.id.home_iv_arrow);
        cv_insight=view.findViewById(R.id.home_cv_insight);
        iv_arrow.setOnClickListener(v -> {
            if (constLayout_expand.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cv_insight, new AutoTransition());
                constLayout_expand.setVisibility(View.VISIBLE);
                iv_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
            } else {
                TransitionManager.beginDelayedTransition(cv_insight, new Fade());
                constLayout_expand.setVisibility(View.GONE);
                iv_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        //refresh realtime
        updateRealtime();

        //back pressed
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finishAffinity();
                    }
                });
        */
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

    //get house device info
    //1st info step
    @SuppressLint("SetTextI18n")
    public void getHouseInfo() {
        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getHouse()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            goalModels = result.getGoals();

                            homeId = result.getId();
                            getGoalInfo(0);
                            getInsightReq(0, 0);
                        },
                        error -> {
                            Toast.makeText(getContext(), "House Error! " + error.getMessage(), Toast.LENGTH_LONG).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }

    //2ed info step
    @SuppressLint("SetTextI18n")
    public void getInsightReq(int aggregation, int unit) {
        MainActivity.museViewModel.getInsightRequest(homeId, aggregation, unit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result1 -> {
                            //array of data chart
                            dataModels.clear();
                            dataModels = result1.getData();

                            tv_average.setText(result1.getAverageUsage()+" W");
                            tv_consumedV.setText(result1.getUsage()+" W");
                            tv_estimation.setText(result1.getEstimatedUsage()+"W");

                            if (chipNavigationBar.getSelectedItemId() == -1) {
                                chipNavigationBar.setItemSelected(R.id.dayFragment, true);
                                MainActivity.isFirstTime.setValue(true);
                            }

                            getCurrentUsage(String.valueOf(unit));
                        },
                        error -> {
                            Toast.makeText(getContext(), "Insight Error! " + error.getMessage(), Toast.LENGTH_LONG).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }

    //3th info step
    @SuppressLint("SetTextI18n")
    public void getCurrentUsage(String unit) {
        MainActivity.museViewModel.getCurrentUsageRequest(homeId, unit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result2 -> {
                    tv_current.setText(result2.string() + " W");
                    if (MainActivity.progressDialog.isShowing())
                        MainActivity.progressDialog.dismiss();
                }, error -> {
                    Toast.makeText(getContext(), "Realtime Error! " + error.getMessage(), Toast.LENGTH_LONG).show();
                    if (MainActivity.progressDialog.isShowing())
                        MainActivity.progressDialog.dismiss();
                });
    }

    @SuppressLint("SetTextI18n")
    public void getGoalInfo(int aggregation) {
        for (GoalModel model : goalModels) {
            if (model.getType() == aggregation) {
                tv_used.setText(model.getUsed()+" W");
                tv_percent.setText(model.getPercent()+"%");
                progressBar.setProgress(model.getPercent());
                if (model.getEstimation() == 0) {
                    tv_prediction.setText("Goal will not achieve");
                    tv_prediction.setTextColor(Color.RED);
                }
                else {
                    tv_prediction.setText("Goal will achieve");
                    tv_prediction.setTextColor(Color.GREEN);
                }
                break;
            }
        }
    }

    public void updateRealtime(){
        getCurrentUsage(String.valueOf(unitOfData));
        if(isAdded())
            Toast.makeText(requireContext(), "Realtime refreshed", Toast.LENGTH_SHORT).show();
        refresh();
    }

    public void refresh(){
        new Handler().postDelayed(this::updateRealtime,15000);
    }
}