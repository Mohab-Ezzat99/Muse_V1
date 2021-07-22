package com.example.musev1.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.adapters.RVAddSchedulesAdapter;
import com.example.musev1.model.DeviceRequestModel;
import com.example.musev1.model.ScheduleModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RVAddSchedulesAdapter adapter;
    private Group not_add;
    private String[] strings;
    private List<DeviceRequestModel> result_devices;

    public SchedulesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedules, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);
        not_add = view.findViewById(R.id.FSchedules_group);

        //recycleView
        recyclerView = view.findViewById(R.id.FSchedules_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RVAddSchedulesAdapter(getContext());
        recyclerView.setAdapter(adapter);
        setupSwipe();

        getAllDevicesReq(0, 0);
        getAllSchedulesReq();

        FloatingActionButton fab_add = view.findViewById(R.id.FSchedules_fab_add);
        fab_add.setOnClickListener(v -> {
            if (result_devices.size() == 0)
                Toast.makeText(getContext(), "No device found to set schedule", Toast.LENGTH_LONG).show();
            else
                showBottomSheet(view);
        });
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NonConstantResourceId"})
    public void showBottomSheet(View view) {
        //init
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_schedules, view.findViewById(R.id.schedulesBotSheet));

        //catch spinner devices
        Spinner spinner_device = bottom_sheet.findViewById(R.id.schedulesBotSheet_spinner_devices);
        ArrayAdapter<String> stringsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
        stringsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_device.setAdapter(stringsAdapter);

        //radio group
        RadioGroup radioGroup=bottom_sheet.findViewById(R.id.schedulesBotSheet_rg);
        RelativeLayout relativeLayout_at,relativeLayout_after;
        relativeLayout_at=bottom_sheet.findViewById(R.id.schedulesBotSheet_relativeLayout_at);
        relativeLayout_after=bottom_sheet.findViewById(R.id.schedulesBotSheet_relativeLayout_after);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.schedulesBotSheet_rb_at:
                    relativeLayout_at.setVisibility(View.VISIBLE);
                    relativeLayout_after.setVisibility(View.INVISIBLE);
                    break;

                case R.id.schedulesBotSheet_rb_after:
                    relativeLayout_after.setVisibility(View.VISIBLE);
                    relativeLayout_at.setVisibility(View.INVISIBLE);
            }
        });

        Spinner spinner_state = bottom_sheet.findViewById(R.id.schedulesBotSheet_spinner_state);
        Spinner spinner_at = bottom_sheet.findViewById(R.id.schedulesBotSheet_spinner_at);
        Spinner spinner_after = bottom_sheet.findViewById(R.id.schedulesBotSheet_spinner_after);
        ThemedToggleButtonGroup tg_long = bottom_sheet.findViewById(R.id.schedulesBotSheet_tg_long);
        ThemedToggleButtonGroup tg_small = bottom_sheet.findViewById(R.id.schedulesBotSheet_tg_small);

        //btn submit
        Button btn_submit = bottom_sheet.findViewById(R.id.schedulesBotSheet_btn_submit);
        btn_submit.setOnClickListener(v1 -> {
            // add item to rv
            DeviceRequestModel device = result_devices.get(spinner_device.getSelectedItemPosition());
            ScheduleModel scheduleModel;

            List<ThemedButton> buttons_long = tg_long.getSelectedButtons();
            List<ThemedButton> buttons_small = tg_small.getSelectedButtons();
            StringBuilder dayStringBuilder = new StringBuilder();
            String days = "";
            int after = -1;

            if (buttons_long.size() > 0)
                for (ThemedButton themedButton : buttons_long)
                    dayStringBuilder.append(",").append(themedButton.getText());

            if (buttons_small.size() > 0)
                for (ThemedButton themedButton : buttons_small)
                    dayStringBuilder.append(",").append(themedButton.getText());

            if (dayStringBuilder.length() > 0) {
                if (dayStringBuilder.length() == 28)
                    days = "Everyday";
                else {
                    days = dayStringBuilder.toString();
                    days = days.substring(1);
                }
            }

            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.schedulesBotSheet_rb_at:
                    scheduleModel = new ScheduleModel(device.getId(), spinner_state.getSelectedItemPosition()
                            , spinner_at.getSelectedItem().toString(), 0, days);
                    break;

                case R.id.schedulesBotSheet_rb_after:
                    switch (spinner_state.getSelectedItemPosition()) {
                        case 0:
                            after = 30;
                            break;

                        case 1:
                            after = 60;
                            break;

                        case 2:
                            after = 180;
                            break;

                        case 3:
                            after = 360;
                    }
                    scheduleModel = new ScheduleModel(device.getId(), spinner_state.getSelectedItemPosition()
                            , null, after, days);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + radioGroup.getCheckedRadioButtonId());
            }

            MainActivity.museViewModel.addSchedule(scheduleModel).enqueue(new Callback<ScheduleModel>() {
                @Override
                public void onResponse(@NotNull Call<ScheduleModel> call, @NotNull Response<ScheduleModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                        getAllSchedulesReq();
                        bottomSheetDialog.dismiss();
                    } else Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NotNull Call<ScheduleModel> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    private void setupSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                ScheduleModel scheduleModel = adapter.getItemAt(viewHolder.getAdapterPosition());
                MainActivity.displayLoadingDialog();
                MainActivity.museViewModel.deleteScheduleById(scheduleModel.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                            getAllSchedulesReq();
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }
                });
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void getAllDevicesReq(int aggregation, int unit) {
        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getAllDevicesRequest(aggregation, unit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            result_devices = result;
                            strings = new String[result.size()];
                            for (int i = 0; i < result.size(); i++)
                                strings[i] = result.get(i).getName();
                            MainActivity.progressDialog.dismiss();
                        },
                        error -> {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }

    public void getAllSchedulesReq() {
        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getAllSchedulesRequest()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            if (result.size() != 0) {
                                // visibility
                                not_add.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            } else {
                                // visibility
                                not_add.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                            adapter.submitList(result);
                            MainActivity.progressDialog.dismiss();
                        },
                        error -> {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }
}