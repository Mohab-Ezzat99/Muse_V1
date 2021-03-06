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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.adapters.RVAddSchedulesAdapter;
import com.example.musev1.databinding.FragmentSchedulesBinding;
import com.example.musev1.model.DeviceModel;
import com.example.musev1.model.ScheduleModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

public class SchedulesFragment extends Fragment {

    private RVAddSchedulesAdapter adapter;
    private String[] strings;
    private List<DeviceModel> result_devices;
    private FragmentSchedulesBinding binding;
    private DeviceModel device;

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
        binding = FragmentSchedulesBinding.bind(view);

        //StatusBar color
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);

        //recycleView
        binding.FSchedulesRv.setHasFixedSize(true);
        binding.FSchedulesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RVAddSchedulesAdapter();
        binding.FSchedulesRv.setAdapter(adapter);
        setupSwipe();

        MainActivity.museViewModel.getAllSchedules().observe(getViewLifecycleOwner(), scheduleModels -> {
            if (scheduleModels.size() != 0) {
                // visibility
                binding.FSchedulesGroup.setVisibility(View.GONE);
                binding.FSchedulesRv.setVisibility(View.VISIBLE);
            } else {
                // visibility
                binding.FSchedulesGroup.setVisibility(View.VISIBLE);
                binding.FSchedulesRv.setVisibility(View.GONE);
            }
            adapter.submitList(scheduleModels);
        });

        getAvailableSchedules();
        binding.FSchedulesFabAdd.setOnClickListener(v -> {
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
        relativeLayout_at=bottom_sheet.findViewById(R.id.schedulesBotSheet_rl_at);
        relativeLayout_after=bottom_sheet.findViewById(R.id.schedulesBotSheet_rl_after);
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
            device = result_devices.get(spinner_device.getSelectedItemPosition());
            device.setHasSchedule(true);
            MainActivity.museViewModel.updateDevice(device);
            getAvailableSchedules();

            // days of weak
            List<ThemedButton> buttons_long = tg_long.getSelectedButtons();
            List<ThemedButton> buttons_small = tg_small.getSelectedButtons();

            // selected day step by step
            StringBuilder dayStringBuilder = new StringBuilder();
            //result of selected days
            String days = "";

            // 4 days
            if (buttons_long.size() > 0)
                for (ThemedButton themedButton : buttons_long)
                    dayStringBuilder.append(",").append(themedButton.getText());

            // 3 days
            if (buttons_small.size() > 0)
                for (ThemedButton themedButton : buttons_small)
                    dayStringBuilder.append(",").append(themedButton.getText());

            // days not null
            if (dayStringBuilder.length() > 0) {
                // all days selected
                if (dayStringBuilder.length() == 28)
                    days = "Everyday";
                else {
                    days = dayStringBuilder.toString();
                    days = days.substring(1);
                }
            }

            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.schedulesBotSheet_rb_at:
                    MainActivity.museViewModel.insertSchedule(new ScheduleModel(
                            device.getId(), device.getName(), device.getIcon()
                            , spinner_state.getSelectedItem().toString()
                            , spinner_at.getSelectedItem().toString()
                            , "", days));
                    break;

                case R.id.schedulesBotSheet_rb_after:
                    MainActivity.museViewModel.insertSchedule(new ScheduleModel(
                            device.getId(), device.getName(), device.getIcon()
                            , spinner_state.getSelectedItem().toString(), ""
                            , spinner_after.getSelectedItem().toString(), days));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + radioGroup.getCheckedRadioButtonId());
            }
            bottomSheetDialog.dismiss();
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
                device = MainActivity.museViewModel.getDevice(scheduleModel.getDeviceId());
                device.setHasSchedule(false);
                MainActivity.museViewModel.updateDevice(device);
                getAvailableSchedules();
                MainActivity.museViewModel.deleteSchedule(scheduleModel);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.FSchedulesRv);
    }

    private void getAvailableSchedules() {
        result_devices = MainActivity.museViewModel.getAvailableSchedules();
        strings = new String[result_devices.size()];
        for (int i = 0; i < result_devices.size(); i++)
            strings[i] = result_devices.get(i).getName();
    }
}