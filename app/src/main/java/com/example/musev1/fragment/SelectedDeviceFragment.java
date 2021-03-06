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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.adapters.RVDeviceBotAdapter;
import com.example.musev1.databinding.FragmentSelectedDeviceBinding;
import com.example.musev1.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

public class SelectedDeviceFragment extends Fragment implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private NavController navControllerChart;
    private DatePickerDialog datePickerDialog;
    private BottomSheetDialog bottomSheetDialog;
    private ImageView dialogIv_icon;
    private boolean realtimeSwitch;
    private int unitPos = 0, chipAgg = 0;

    private DeviceModel device;
    private int day, month, year, chosenIcon = -1;
    private FragmentSelectedDeviceBinding binding;

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

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_selected_device, container, false);
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
        binding = FragmentSelectedDeviceBinding.bind(view);

        binding.selectedDIvIcon.setImageResource(device.getIcon());
        binding.selectedDTvName.setText(device.getName());

        // device state..on/off
        //when display
        binding.selectedDSwitch.setChecked(device.isOn());
        if (device.isOn()) {
            binding.selectedDIvIcon.setColorFilter(MainActivity.colorPrimaryVariant);
            binding.selectedDProgressValue.setVisibility(View.VISIBLE);
            binding.selectedDPb.setVisibility(View.VISIBLE);
        } else {
            binding.selectedDIvIcon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
            binding.selectedDProgressValue.setVisibility(View.INVISIBLE);
            binding.selectedDPb.setVisibility(View.INVISIBLE);
        }
        //set change
        binding.selectedDSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.selectedDIvIcon.setColorFilter(MainActivity.colorPrimaryVariant);
                binding.selectedDProgressValue.setVisibility(View.VISIBLE);
                binding.selectedDPb.setVisibility(View.VISIBLE);
            } else {
                binding.selectedDIvIcon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
                binding.selectedDProgressValue.setVisibility(View.INVISIBLE);
                binding.selectedDPb.setVisibility(View.INVISIBLE);
            }
            device.setOn(isChecked);
            MainActivity.museViewModel.updateDevice(device);
        });

        binding.selectedDSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.selectedDChipNav.setItemSelected(R.id.dayFragment, true);
        binding.selectedDChipNav.setOnItemSelectedListener(i -> {
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
                    chipAgg = 3;
                    initData();
            }
        });

        // expanded card info
        binding.selectedDIvArrow.setOnClickListener(v -> {
            if (binding.selectedDConstLayoutExpanded.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.selectedDCvInsight, new AutoTransition());
                binding.selectedDConstLayoutExpanded.setVisibility(View.VISIBLE);
                binding.selectedDIvArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            } else {
                TransitionManager.beginDelayedTransition(binding.selectedDCvInsight, new Fade());
                binding.selectedDConstLayoutExpanded.setVisibility(View.GONE);
                binding.selectedDIvArrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });

        binding.selectedDCvGoal.setOnClickListener(this);
        binding.selectedDCvSchedule.setOnClickListener(this);
        binding.selectedDCvCustomAlert.setOnClickListener(this);
        binding.selectedDIvCustom.setOnClickListener(this);

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
                    Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
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
                showPopup(binding.selectedDIvCustom);
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
                binding.selectedDTvCurrentV.setText("30 W");
            else
                binding.selectedDTvCurrentV.setText("15 EGP");
        } else {
            realtimeSwitch = true;
            if (unitPos == 0)
                binding.selectedDTvCurrentV.setText("45 W");
            else
                binding.selectedDTvCurrentV.setText("22 EGP");
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
                    binding.selectedDTvPer.setText("Per day");
                    binding.selectedDTvAverageV.setText("80 W");
                    binding.selectedDTvConsumedV.setText("120 W");
                    binding.selectedDTvEstV.setText("250 W");
                    break;

                case 1:
                    binding.selectedDTvPer.setText("Per weak");
                    binding.selectedDTvAverageV.setText("107 W");
                    binding.selectedDTvConsumedV.setText("830 W");
                    binding.selectedDTvEstV.setText("1000 W");
                    break;

                case 2:
                    binding.selectedDTvPer.setText("Per month");
                    binding.selectedDTvAverageV.setText("11 KW");
                    binding.selectedDTvConsumedV.setText("127 KW");
                    binding.selectedDTvEstV.setText("150 KW");
                    break;

                case 3:
                    binding.selectedDTvPer.setText("Per year");
                    binding.selectedDTvAverageV.setText("160 KW");
                    binding.selectedDTvConsumedV.setText("360 KW");
                    binding.selectedDTvEstV.setText("500 KW");
                    break;
            }
            if (realtimeSwitch)
                binding.selectedDTvCurrentV.setText("30 W");
            else
                binding.selectedDTvCurrentV.setText("45 W");
        } else {
            switch (chipAgg) {
                case 0:
                    binding.selectedDTvPer.setText("Per day");
                    binding.selectedDTvAverageV.setText("40 EGP");
                    binding.selectedDTvConsumedV.setText("60 EGP");
                    binding.selectedDTvEstV.setText("120 EGP");
                    break;

                case 1:
                    binding.selectedDTvPer.setText("Per weak");
                    binding.selectedDTvAverageV.setText("53 EGP");
                    binding.selectedDTvConsumedV.setText("415 EGP");
                    binding.selectedDTvEstV.setText("500 EGP");
                    break;

                case 2:
                    binding.selectedDTvPer.setText("Per month");
                    binding.selectedDTvAverageV.setText("5500 EGP");
                    binding.selectedDTvConsumedV.setText("6350 EGP");
                    binding.selectedDTvEstV.setText("7500 EGP");
                    break;

                case 3:
                    binding.selectedDTvPer.setText("Per year");
                    binding.selectedDTvAverageV.setText("20000 EGP");
                    binding.selectedDTvConsumedV.setText("43800 EGP");
                    binding.selectedDTvEstV.setText("50300 EGP");
                    break;
            }

            if(!realtimeSwitch)
                binding.selectedDTvCurrentV.setText("15 EGP");
            else
                binding.selectedDTvCurrentV.setText("22 EGP");
        }
    }
}