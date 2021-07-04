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
import com.example.muse.model.AlertModel;
import com.example.muse.model.DeviceModel;
import com.example.muse.model.DeviceRequestModel;
import com.example.muse.model.DeviceResponseModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedDeviceFragment extends Fragment implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;
    private TextView tv_name, tv_percent;
    private ImageView iv_icon, dialogIv_icon;
    private CardView cv_goal, cv_schedules;
    private SwitchCompat switchCompat;
    private ProgressBar progressBar;
    private ImageView iv_custom;
    private DeviceResponseModel device;
    private int day, month, year;
    private DatePickerDialog datePickerDialog;
    private ConstraintLayout constLayout_expand;
    private CardView cv_insight;
    private ImageView iv_arrow;
    private Spinner spinner;
    private TextView tv_current, tv_average, tv_per, tv_perV, tv_estimation;
    private BottomSheetDialog bottomSheetDialog;
    private int chosenIcon = -1, deviceId = -1;

    public SelectedDeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (getArguments() != null) {
            SelectedDeviceFragmentArgs args = SelectedDeviceFragmentArgs.fromBundle(getArguments());
            deviceId = args.getDeviceId();
        }

        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getDeviceById(deviceId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    // set selected device info
                    device = result;
                    setupIcons(iv_icon, device.getPictureId());
                    tv_name.setText(device.getName());
                    switchCompat.setChecked(device.getState() != 0);

                    // display getting info
                    if (device.getState() != 0) {
                        iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
                        tv_percent.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        iv_icon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
                        tv_percent.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    MainActivity.progressDialog.dismiss();
                }, error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    MainActivity.progressDialog.dismiss();
                });

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_selected_device, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        if (device != null)
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
        iv_custom = view.findViewById(R.id.selectedD_iv_custom);

        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.displayLoadingDialog();
            if (isChecked) {
                iv_icon.setColorFilter(MainActivity.colorPrimaryVariant);
                tv_percent.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                MainActivity.museViewModel.setState(device.getId(), 1).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            Toast.makeText(getContext(), "Updated state", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }
                });
            }
            else {
                iv_icon.setColorFilter(requireContext().getResources().getColor(R.color.gray));
                tv_percent.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                MainActivity.museViewModel.setState(device.getId(), 0).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            Toast.makeText(getContext(), "Updated state", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }
                });
            }
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

            setupIcons(dialogIv_icon, device.getPictureId());
            dialogIv_icon.setOnClickListener(v -> showBottomSheet(dialogIv_icon));

            tv_save.setOnClickListener(v -> {
                String new_name = Objects.requireNonNull(et_name.getText()).toString();
                DeviceRequestModel requestModel = null;

                if (chosenIcon >= 0) {
                    if (!(new_name.equals("")))
                        requestModel = new DeviceRequestModel(chosenIcon, new_name);
                    else requestModel = new DeviceRequestModel(chosenIcon, device.getName());
                    chosenIcon=-1;
                } else {
                    if (!(new_name.equals(""))) {
                        requestModel = new DeviceRequestModel(device.getPictureId(), new_name);
                    }
                }

                if (requestModel != null) {
                    MainActivity.museViewModel.editDeviceById(device.getId(), requestModel).enqueue(new Callback<DeviceResponseModel>() {
                        @Override
                        public void onResponse(@NotNull Call<DeviceResponseModel> call, @NotNull Response<DeviceResponseModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(requireActivity(), R.id.main_fragment).popBackStack();
                                alertDialog.dismiss();
                            } else
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NotNull Call<DeviceResponseModel> call, @NotNull Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "No changes", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireActivity(), R.id.main_fragment).popBackStack();
                    alertDialog.dismiss();
                }
            });

            tv_delete.setOnClickListener(v -> MainActivity.museViewModel.deleteDeviceById(device.getId()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireActivity(), R.id.main_fragment).popBackStack();
                        alertDialog.dismiss();
                    } else Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }));

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
                setupIcons(ivG_icon, device.getPictureId());
                tvG_name.setText(device.getName());
                break;

            case R.id.selectedD_cv_schedule:
                View viewS = displayDialog(R.layout.item_add_schedules);
                ImageView ivS_icon = viewS.findViewById(R.id.itemAS_iv_icon);
                setupIcons(ivS_icon, device.getPictureId());
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
            public void OnItemClick(DeviceRequestModel device1) {

            }

            @Override
            public void OnItemClick(AlertModel alertModel) {

            }

            @Override
            public void OnBottomSheetItemClick(DeviceModel device, int position) {
                chosenIcon = position;
                setupIcons(dialogIv_icon, chosenIcon);
                bottomSheetDialog.dismiss();
            }

            @Override
            public void OnItemLongClick(View view, DeviceRequestModel device) {

            }
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    public void setupIcons(ImageView imageView, int id) {

        switch (id) {
            case 1:
                imageView.setImageResource(R.drawable.ic_tv);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_fridge);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_air_conditioner);
                break;
            case 4:
                imageView.setImageResource(R.drawable.ic_pc);
                break;
            case 5:
                imageView.setImageResource(R.drawable.ic_clothes_dryer);
                break;
            case 6:
                imageView.setImageResource(R.drawable.ic_freezer);
                break;
            case 7:
                imageView.setImageResource(R.drawable.ic_coffee_maker);
                break;
            case 8:
                imageView.setImageResource(R.drawable.ic_dishwasher);
                break;
            case 9:
                imageView.setImageResource(R.drawable.ic_fan_heater);
                break;
            case 10:
                imageView.setImageResource(R.drawable.ic_toaster);
                break;
            case 11:
                imageView.setImageResource(R.drawable.ic_water_dispenser);
                break;
            case 12:
                imageView.setImageResource(R.drawable.ic_plug);
        }
    }
}