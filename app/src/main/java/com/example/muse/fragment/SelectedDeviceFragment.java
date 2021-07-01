package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.MainActivity;
import com.example.muse.R;
import com.example.muse.adapters.OnDeviceItemListener;
import com.example.muse.adapters.RVDeviceBotAdapter;
import com.example.muse.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

public class SelectedDeviceFragment extends Fragment implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;
    private TextView tv_name, tv_percent;
    private ImageView iv_icon, dialogIv_icon;
    private CardView cv_goal, cv_schedules;
    private SwitchCompat switchCompat;
    private ProgressBar progressBar;
    private ImageView iv_custom;
    private DeviceModel device;
    private int day, month, year;
    private DatePickerDialog datePickerDialog;
    private ConstraintLayout constLayout_expand;
    private CardView cv_insight;
    private ImageView iv_arrow;
    private Spinner spinner;
    private TextView tv_current,tv_average,tv_per,tv_perV, tv_estimation;
    private BottomSheetDialog bottomSheetDialog;

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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_selected_device, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(device.getName());
        navControllerChart = Navigation.findNavController(requireActivity(), R.id.selectedD_fragment);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_name = view.findViewById(R.id.selectedD_tv_name);
        iv_icon = view.findViewById(R.id.selectedD_iv_icon);
        cv_goal = view.findViewById(R.id.selectedD_cv_goal);
        cv_schedules = view.findViewById(R.id.selectedD_cv_schedule);
        switchCompat = view.findViewById(R.id.selectedD_switch);
        tv_percent = view.findViewById(R.id.selectedD_progressValue);
        progressBar = view.findViewById(R.id.selectedD_pb);

        // set selected device info
        iv_custom = view.findViewById(R.id.selectedD_iv_custom);
        iv_icon.setImageResource(device.getIcon());
        tv_name.setText(device.getName());
        switchCompat.setChecked(device.isOn());

        // display getting info
        if(device.isOn()){
            iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
            tv_percent.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            iv_icon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
            tv_percent.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
                tv_percent.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                iv_icon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
                tv_percent.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            device.setOn(isChecked);
            MainActivity.museViewModel.updateDevice(device);
        });

        cv_goal.setOnClickListener(this);
        cv_schedules.setOnClickListener(this);
        iv_custom.setOnClickListener(this);

        //iv_custom
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        // charts taps
        chipNavigationBar =view.findViewById(R.id.selectedD_chipNav);
        tv_per = view.findViewById(R.id.selectedD_tv_per);
        chipNavigationBar.setItemSelected(R.id.dayFragment,true);
        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.dayFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartDayFragment);
                    tv_per.setText("Per day");
                    return;

                case R.id.weekFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartWeekFragment);
                    tv_per.setText("Per week");
                    return;

                case R.id.monthFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartMonthFragment);
                    tv_per.setText("Per month");
                    return;

                case R.id.yearFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartYearFragment);
                    tv_per.setText("Per year");
            }
        });

        // spinner info
        spinner=view.findViewById(R.id.selectedD_spinner_unit);
        tv_current=view.findViewById(R.id.selectedD_tv_currentV);
        tv_average=view.findViewById(R.id.selectedD_tv_averageV);
        tv_perV=view.findViewById(R.id.selectedD_tv_perV);
        tv_estimation=view.findViewById(R.id.selectedD_tv_estV);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    tv_current.setText("20 W");
                    tv_average.setText("200 W");
                    tv_perV.setText("30 KW");
                    tv_estimation.setText("20 KW");
                }
                else
                {
                    tv_current.setText("12 EGP");
                    tv_average.setText("120 EGP");
                    tv_perV.setText("390 EGP");
                    tv_estimation.setText("260 EGP");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        constLayout_expand=view.findViewById(R.id.selectedD_constLayoutExpanded);
        iv_arrow=view.findViewById(R.id.selectedD_iv_arrow);
        cv_insight=view.findViewById(R.id.selectedD_cv_insight);
        iv_arrow.setOnClickListener(v -> {
            if(constLayout_expand.getVisibility()== View.GONE){
                TransitionManager.beginDelayedTransition(cv_insight,new AutoTransition());
                constLayout_expand.setVisibility(View.VISIBLE);
                iv_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }
            else
            {
                TransitionManager.beginDelayedTransition(cv_insight,new Fade());
                constLayout_expand.setVisibility(View.GONE);
                iv_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_device_selected,menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.DialogStyle);
            View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null, false);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            dialogIv_icon = view.findViewById(R.id.dialogEdit_iv_icon);
            TextView tv_save = view.findViewById(R.id.dialogEdit_tv_save);
            TextView tv_delete = view.findViewById(R.id.dialogEdit_tv_delete);
            TextInputEditText et_name = view.findViewById(R.id.dialogEdit_et_deviceName);

            dialogIv_icon.setImageResource(device.getIcon());
            dialogIv_icon.setOnClickListener(v -> showBottomSheet(dialogIv_icon));

            tv_save.setOnClickListener(v -> {
                if (!(et_name.getText().toString().equals("")))
                    device.setName(et_name.getText().toString());
                MainActivity.museViewModel.updateDevice(device);
                Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireActivity(),R.id.main_fragment).popBackStack();
                alertDialog.dismiss();
            });

            tv_delete.setOnClickListener(v -> {
                MainActivity.museViewModel.deleteDevice(device);
                Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireActivity(),R.id.main_fragment).popBackStack();
                alertDialog.dismiss();
            });

        }

        else if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireActivity(), R.id.main_fragment).popBackStack();
            return true;
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.selectedD_cv_goal:
                View viewG = displayDialog(R.layout.item_add_goal);
                ImageView ivG_icon = viewG.findViewById(R.id.itemAG_iv_icon);
                TextView tvG_name = viewG.findViewById(R.id.itemAG_tv_name);
                ivG_icon.setImageResource(device.getIcon());
                tvG_name.setText(device.getName());
                break;

            case R.id.selectedD_cv_schedule:
                View viewS = displayDialog(R.layout.item_add_schedules);
                ImageView ivS_icon = viewS.findViewById(R.id.itemAS_iv_icon);
                TextView tvS_name = viewS.findViewById(R.id.itemAS_tv_name);
                ivS_icon.setImageResource(device.getIcon());
                tvS_name.setText(device.getName());
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
        RVDeviceBotAdapter botAdapter = new RVDeviceBotAdapter(getContext());
        botAdapter.setList(MainActivity.modelArrayList);
        rv.setAdapter(botAdapter);

        botAdapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceModel device1) {
                dialogIv_icon.setImageResource(device1.getIcon());
                device.setIcon(device1.getIcon());
                bottomSheetDialog.dismiss();
            }

            @Override
            public void OnItemLongClick(View view, DeviceModel device) {

            }
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }
}