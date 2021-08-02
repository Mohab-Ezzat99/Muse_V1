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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.interfaces.OnBottomItemListener;
import com.example.musev1.interfaces.OnDeviceItemListener;
import com.example.musev1.adapters.RVDeviceBotAdapter;
import com.example.musev1.model.AlertModel;
import com.example.musev1.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

public class SelectedDeviceFragment extends Fragment implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;

    private TextView tv_name, tv_percent, tv_per, tv_currentV, tv_avgV, tv_consV, tv_estV;
    private ImageView iv_icon, dialogIv_icon, iv_arrow, iv_custom;
    private CardView cv_goal, cv_schedules, cv_customAlert, cv_insight;
    private SwitchCompat switchCompat;
    private ProgressBar progressBar;
    private DatePickerDialog datePickerDialog;
    private BottomSheetDialog bottomSheetDialog;
    private ConstraintLayout constLayout_expand;
    private Spinner spinnerUnit;
    private boolean realtimeSwitch;
    private int unitPos = 0, chipAgg = 0;

    private DeviceModel device;
    private int day, month, year, chosenIcon = -1;

    public SelectedDeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // get selected device id
        if (getArguments() != null) {
            SelectedDeviceFragmentArgs args = SelectedDeviceFragmentArgs.fromBundle(getArguments());
            device = args.getDevice();
        }

        // fetch view before getting data
        View view = inflater.inflate(R.layout.fragment_selected_device, container, false);

        tv_name = view.findViewById(R.id.selectedD_tv_name);
        iv_icon = view.findViewById(R.id.selectedD_iv_icon);
        cv_goal = view.findViewById(R.id.selectedD_cv_goal);
        cv_schedules = view.findViewById(R.id.selectedD_cv_schedule);
        cv_customAlert = view.findViewById(R.id.selectedD_cv_customAlert);
        switchCompat = view.findViewById(R.id.selectedD_switch);
        tv_percent = view.findViewById(R.id.selectedD_progressValue);
        progressBar = view.findViewById(R.id.selectedD_pb);
        iv_custom = view.findViewById(R.id.selectedD_iv_custom);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(device.getName());
        navControllerChart = Navigation.findNavController(requireActivity(), R.id.selectedD_fragment);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_icon.setImageResource(device.getIcon());
        tv_name.setText(device.getName());

        // spinner info inflation
        spinnerUnit = view.findViewById(R.id.selectedD_spinner_unit);
        tv_currentV = view.findViewById(R.id.selectedD_tv_currentV);
        tv_avgV = view.findViewById(R.id.selectedD_tv_averageV);
        tv_consV = view.findViewById(R.id.selectedD_tv_consumedV);
        tv_estV = view.findViewById(R.id.selectedD_tv_estV);

        // device state..on/off
        //when display
        switchCompat.setChecked(device.isOn());
        if (device.isOn()) {
            iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
            tv_percent.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            iv_icon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
            tv_percent.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        //set change
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
                tv_percent.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                iv_icon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
                tv_percent.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            device.setOn(isChecked);
            MainActivity.museViewModel.updateDevice(device);
        });

        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        //iv_custom
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        // charts taps
        chipNavigationBar = view.findViewById(R.id.selectedD_chipNav);
        tv_per = view.findViewById(R.id.selectedD_tv_per);
        chipNavigationBar.setItemSelected(R.id.dayFragment, true);
        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i) {
                case R.id.dayFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartDayFragment);
                    chipAgg=0;
                    initData();
                    return;

                case R.id.weekFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartWeekFragment);
                    chipAgg=1;
                    initData();
                    return;

                case R.id.monthFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartMonthFragment);
                    chipAgg=2;
                    initData();
                    return;

                case R.id.yearFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartYearFragment);
                    chipAgg=3;
                    initData();
            }
        });

        // expanded card info
        constLayout_expand = view.findViewById(R.id.selectedD_constLayoutExpanded);
        iv_arrow = view.findViewById(R.id.selectedD_iv_arrow);
        cv_insight = view.findViewById(R.id.selectedD_cv_insight);
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

        cv_goal.setOnClickListener(this);
        cv_schedules.setOnClickListener(this);
        cv_customAlert.setOnClickListener(this);
        iv_custom.setOnClickListener(this);

        //refresh realtime
        updateRealtime();
    }

    // inflation setting icon in menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_device_selected,menu);
    }

    // click on setting menu icon
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_settings:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.DialogStyle);
                View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null, false);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // inflation
                dialogIv_icon = view.findViewById(R.id.dialogEdit_iv_icon);
                TextView tv_save = view.findViewById(R.id.dialogEdit_tv_save);
                TextView tv_delete = view.findViewById(R.id.dialogEdit_tv_delete);
                TextInputEditText et_name = view.findViewById(R.id.dialogEdit_et_deviceName);
                dialogIv_icon.setImageResource(device.getIcon());
                dialogIv_icon.setOnClickListener(v -> {
                    MainActivity.hideKeyboardFrom(requireContext(), dialogIv_icon);
                    showBottomSheet(dialogIv_icon);
                });

                tv_save.setOnClickListener(v -> {
                    // fetch data edited
                    String new_name = Objects.requireNonNull(et_name.getText()).toString();
                    if (!new_name.equals(""))
                        device.setName(new_name);

                    if (chosenIcon != -1)
                        device.setIcon(chosenIcon);

                    MainActivity.museViewModel.updateDevice(device);
                    Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireActivity(), R.id.main_fragment).popBackStack();
                    alertDialog.dismiss();

                });

                tv_delete.setOnClickListener(v -> {
                    MainActivity.museViewModel.deleteDevice(device);
                    Toast.makeText(getContext(), "deleted successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireActivity(), R.id.main_fragment).popBackStack();
                    alertDialog.dismiss();
                });
                return true;

            //click back arrow
            case android.R.id.home:
                Navigation.findNavController(requireActivity(), R.id.main_fragment).popBackStack();
                return true;
        }

        return true;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.selectedD_cv_goal:
                if (device.isHasGoal()) {
                    View viewG = displayDialog(R.layout.item_add_goal);
                    ImageView ivG_icon = viewG.findViewById(R.id.itemAG_iv_icon);
                    TextView tv_name = viewG.findViewById(R.id.itemAG_tv_name);
                    TextView tv_prediction = viewG.findViewById(R.id.itemAG_predictionV);
                    TextView tv_used = viewG.findViewById(R.id.itemAG_tv_used);
                    TextView tv_type = viewG.findViewById(R.id.itemAG_tv_type);
                    MainActivity.museViewModel.getGoalByDeviceId(device.getId()).observe(getViewLifecycleOwner(), goalModel -> {
                        ivG_icon.setImageResource(goalModel.getPictureId());
                        tv_name.setText(goalModel.getDeviceName());
                        tv_used.setText(goalModel.getUsageLimit());
                        tv_prediction.setText(R.string.txt_goal_will_be_achieved);
                        tv_type.setText(goalModel.getType());
                    });
                } else
                    Toast.makeText(getContext(), "No goal found", Toast.LENGTH_SHORT).show();
                break;

            case R.id.selectedD_cv_schedule:
                if (device.isHasSchedule()) {
                    View viewS = displayDialog(R.layout.item_add_schedules);
                    ImageView iv_icon = viewS.findViewById(R.id.itemAS_iv_icon);
                    TextView tv_name = viewS.findViewById(R.id.itemAS_tv_name);
                    TextView tv_state = viewS.findViewById(R.id.itemAS_tv_state);
                    TextView tv_later = viewS.findViewById(R.id.itemAS_tv_later);
                    TextView tv_period = viewS.findViewById(R.id.itemAS_tv_period);
                    TextView tv_days = viewS.findViewById(R.id.itemAS_tv_days);
                    Group repeatGroup = viewS.findViewById(R.id.itemAS_repeatGroup);
                    MainActivity.museViewModel.getScheduleByDeviceId(device.getId()).observe(getViewLifecycleOwner(), scheduleModel -> {
                        iv_icon.setImageResource(scheduleModel.getPictureId());
                        tv_name.setText(scheduleModel.getDeviceName());
                        tv_state.setText(scheduleModel.getState());

                        if (!scheduleModel.getAtTime().equals("")) {
                            tv_later.setText("At");
                            tv_period.setText(scheduleModel.getAtTime());
                        }

                        if (!scheduleModel.getAfterPeriod().equals("")) {
                            tv_later.setText("After");
                            tv_period.setText(scheduleModel.getAfterPeriod());
                        }

                        if (scheduleModel.getRepeat().equals(""))
                            repeatGroup.setVisibility(View.GONE);
                        else
                            tv_days.setText(scheduleModel.getRepeat());
                    });
                } else
                    Toast.makeText(getContext(), "No schedule found", Toast.LENGTH_SHORT).show();
                break;

            case R.id.selectedD_cv_customAlert:
                if (device.isHasCustomAlert()) {
                    View viewC = displayDialog(R.layout.item_add_custom_alert);
                    ImageView iv_icon=viewC.findViewById(R.id.itemAC_iv_icon);
                    TextView tv_name = viewC.findViewById(R.id.itemAC_tv_name);
                    TextView tv_state = viewC.findViewById(R.id.itemAC_tv_state);
                    TextView tv_later = viewC.findViewById(R.id.itemAC_tv_later);
                    TextView tv_period = viewC.findViewById(R.id.itemAC_tv_period);
                    TextView tv_max = viewC.findViewById(R.id.itemAC_tv_max);
                    MainActivity.museViewModel.getCustomAlertByDeviceId(device.getId()).observe(getViewLifecycleOwner(), customAlertModel -> {
                        iv_icon.setImageResource(customAlertModel.getPictureId());
                        tv_name.setText(customAlertModel.getDeviceName());
                        tv_state.setText(customAlertModel.getState());
                        tv_max.setText(customAlertModel.getMaxUsage());

                        if (!customAlertModel.getAtTime().equals("")) {
                            tv_later.setText("At");
                            tv_period.setText(customAlertModel.getAtTime());
                        }

                        if (!customAlertModel.getForPeriod().equals("")) {
                            tv_later.setText("After");
                            tv_period.setText(customAlertModel.getForPeriod());
                        }
                    });
                } else
                    Toast.makeText(getContext(), "No custom alert found", Toast.LENGTH_SHORT).show();
                break;

            case R.id.selectedD_iv_custom:
                showPopup(iv_custom);
                break;
        }
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
        popupMenu.inflate(R.menu.menu_custom);
        popupMenu.show();
    }

    //custom period click
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
                , android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackground(null);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackground(null);
    }

    //display dialog for goals or schedules
    public View displayDialog(int layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.DialogStyle);
        View view = LayoutInflater.from(requireContext()).inflate(layout, null, false);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void showBottomSheet(View view) {
        //init
        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_device, view.findViewById(R.id.deviceBotSheet));

        TextView tv_title = bottom_sheet.findViewById(R.id.deviceBotSheet_tv_title);
        tv_title.setText("Select icon");

        RecyclerView rv = bottom_sheet.findViewById(R.id.deviceBotSheet_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // adapter with click listener
        RVDeviceBotAdapter botAdapter = new RVDeviceBotAdapter();
        botAdapter.setList(MainActivity.modelArrayList);
        rv.setAdapter(botAdapter);

        botAdapter.setListener((device, position) -> {
            chosenIcon = device.getIcon();
            dialogIv_icon.setImageResource(chosenIcon);
            bottomSheetDialog.dismiss();
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void updateRealtime() {
        if (realtimeSwitch) {
            realtimeSwitch = false;
            if (unitPos == 0)
                tv_currentV.setText("30 W");
            else
                tv_currentV.setText("15 EGP");
        } else {
            realtimeSwitch = true;
            if (unitPos == 0)
                tv_currentV.setText("45 W");
            else
                tv_currentV.setText("22 EGP");
        }
        refresh();
    }

    public void refresh(){
        new Handler().postDelayed(this::updateRealtime, 8000);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        if (unitPos == 0) {
            switch (chipAgg) {
                case 0:
                    tv_per.setText("Per day");
                    tv_avgV.setText("80 W");
                    tv_consV.setText("120 W");
                    tv_estV.setText("250 W");
                    break;

                case 1:
                    tv_per.setText("Per weak");
                    tv_avgV.setText("107 W");
                    tv_consV.setText("830 W");
                    tv_estV.setText("1000 W");
                    break;

                case 2:
                    tv_per.setText("Per month");
                    tv_avgV.setText("11 KW");
                    tv_consV.setText("127 KW");
                    tv_estV.setText("150 KW");
                    break;

                case 3:
                    tv_per.setText("Per year");
                    tv_avgV.setText("160 KW");
                    tv_consV.setText("360 KW");
                    tv_estV.setText("500 KW");
                    break;
            }
            if (realtimeSwitch)
                tv_currentV.setText("30 W");
            else
                tv_currentV.setText("45 W");
        } else {
            switch (chipAgg) {
                case 0:
                    tv_per.setText("Per day");
                    tv_avgV.setText("40 EGP");
                    tv_consV.setText("60 EGP");
                    tv_estV.setText("120 EGP");
                    break;

                case 1:
                    tv_per.setText("Per weak");
                    tv_avgV.setText("53 EGP");
                    tv_consV.setText("415 EGP");
                    tv_estV.setText("500 EGP");
                    break;

                case 2:
                    tv_per.setText("Per month");
                    tv_avgV.setText("5500 EGP");
                    tv_consV.setText("6350 EGP");
                    tv_estV.setText("7500 EGP");
                    break;

                case 3:
                    tv_per.setText("Per year");
                    tv_avgV.setText("20000 EGP");
                    tv_consV.setText("43800 EGP");
                    tv_estV.setText("50300 EGP");
                    break;
            }

            if(!realtimeSwitch)
                tv_currentV.setText("15 EGP");
            else
                tv_currentV.setText("22 EGP");
        }
    }
}