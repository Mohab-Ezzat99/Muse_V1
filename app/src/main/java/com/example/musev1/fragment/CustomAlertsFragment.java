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
import com.example.musev1.adapters.RVAddCustomAlertAdapter;
import com.example.musev1.databinding.FragmentCustomAlertsBinding;
import com.example.musev1.model.CustomAlertModel;
import com.example.musev1.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomAlertsFragment extends Fragment {

    private RVAddCustomAlertAdapter adapter;
    private String[] strings;
    private List<DeviceModel> result_devices;
    private FragmentCustomAlertsBinding binding;
    private DeviceModel device;

    public CustomAlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_alerts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentCustomAlertsBinding.bind(view);

        //StatusBar color
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);

        binding.FCustomAlertRv.setHasFixedSize(true);
        binding.FCustomAlertRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RVAddCustomAlertAdapter();
        binding.FCustomAlertRv.setAdapter(adapter);
        setupSwipe();

        MainActivity.museViewModel.getAllCustomAlerts().observe(getViewLifecycleOwner(), customAlertModels -> {
            if (customAlertModels.size() != 0) {
                // visibility
                binding.FCustomAlertGroup.setVisibility(View.GONE);
                binding.FCustomAlertRv.setVisibility(View.VISIBLE);
            } else {
                // visibility
                binding.FCustomAlertGroup.setVisibility(View.VISIBLE);
                binding.FCustomAlertRv.setVisibility(View.GONE);
            }
            adapter.submitList(customAlertModels);
        });

        getAvailableCustomAlerts();
        binding.FCustomAlertFabAdd.setOnClickListener(v -> {
            if (result_devices.size() == 0)
                Toast.makeText(getContext(), "No device found to set custom alert", Toast.LENGTH_LONG).show();
            else
                showBottomSheet(view);
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void showBottomSheet(View view) {
        //init
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_custom_alert, view.findViewById(R.id.customAlertBotSheet));

        Spinner spinner_device = bottom_sheet.findViewById(R.id.customAlertBotSheet_spinner_devices);
        ArrayAdapter<String> stringsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
        stringsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_device.setAdapter(stringsAdapter);

        //radio group
        RadioGroup radioGroup = bottom_sheet.findViewById(R.id.customAlertBotSheet_rg);
        RelativeLayout relativeLayout_at, relativeLayout_after;
        relativeLayout_at = bottom_sheet.findViewById(R.id.customAlertBotSheet_rl_at);
        relativeLayout_after = bottom_sheet.findViewById(R.id.customAlertBotSheet_rl_after);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.customAlertBotSheet_rb_at:
                    relativeLayout_at.setVisibility(View.VISIBLE);
                    relativeLayout_after.setVisibility(View.INVISIBLE);
                    break;

                case R.id.customAlertBotSheet_rb_after:
                    relativeLayout_after.setVisibility(View.VISIBLE);
                    relativeLayout_at.setVisibility(View.INVISIBLE);
            }
        });

        Spinner spinner_state = bottom_sheet.findViewById(R.id.customAlertBotSheet_spinner_state);
        Spinner spinner_at = bottom_sheet.findViewById(R.id.customAlertBotSheet_spinner_at);
        Spinner spinner_after = bottom_sheet.findViewById(R.id.customAlertBotSheet_spinner_after);
        Spinner spinner_max = bottom_sheet.findViewById(R.id.customAlertBotSheet_spinner_max);

        //btn submit
        Button btn_submit = bottom_sheet.findViewById(R.id.customAlertBotSheet_btn_submit);
        btn_submit.setOnClickListener(v1 -> {
            // add item to rv
            device = result_devices.get(spinner_device.getSelectedItemPosition());
            device.setHasCustomAlert(true);
            MainActivity.museViewModel.updateDevice(device);
            getAvailableCustomAlerts();

            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.customAlertBotSheet_rb_at:
                    MainActivity.museViewModel.insertCustomAlert(new CustomAlertModel(
                            device.getId(), device.getName(), device.getIcon()
                            , spinner_state.getSelectedItem().toString()
                            , spinner_at.getSelectedItem().toString(), ""
                            , spinner_max.getSelectedItem().toString()));
                    break;

                case R.id.customAlertBotSheet_rb_after:
                    MainActivity.museViewModel.insertCustomAlert(new CustomAlertModel(
                            device.getId(),device.getName(),device.getIcon()
                            , spinner_state.getSelectedItem().toString(), ""
                            , spinner_after.getSelectedItem().toString()
                            , spinner_max.getSelectedItem().toString()));
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

                CustomAlertModel customAlertModel = adapter.getItemAt(viewHolder.getAdapterPosition());
                device = MainActivity.museViewModel.getDevice(customAlertModel.getDeviceId());
                device.setHasCustomAlert(false);
                MainActivity.museViewModel.updateDevice(device);
                getAvailableCustomAlerts();
                MainActivity.museViewModel.deleteCustomAlert(customAlertModel);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.FCustomAlertRv);
    }

    private void getAvailableCustomAlerts() {
        result_devices = MainActivity.museViewModel.getAvailableCustomAlerts();
        strings = new String[result_devices.size()];
        for (int i = 0; i < result_devices.size(); i++)
            strings[i] = result_devices.get(i).getName();
    }
}