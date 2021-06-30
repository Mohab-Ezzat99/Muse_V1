package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.RVAddGoalAdapter;
import com.example.muse.adapters.RVAddSchedulesAdapter;
import com.example.muse.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchedulesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RVAddSchedulesAdapter adapter;
    private Group not_add;
    private String[] strings;
    private List<DeviceModel> result;

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
        StartActivity.setupBackgroundStatusBar(StartActivity.colorPrimaryVariant);
        not_add = view.findViewById(R.id.FSchedules_group);

        //recycleView
        recyclerView = view.findViewById(R.id.FSchedules_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RVAddSchedulesAdapter(getContext());
        recyclerView.setAdapter(adapter);
        setupSwipe();

        StartActivity.museViewModel.getDevicesSchedules().observe(getViewLifecycleOwner(), deviceModels -> {
            if (deviceModels.size() != 0) {
                // visibility
                not_add.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                // visibility
                not_add.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            adapter.submitList(deviceModels);
        });

        StartActivity.museViewModel.getDevicesAdded().observe(getViewLifecycleOwner(), deviceModels -> {
            result = deviceModels;
            strings = new String[deviceModels.size()];
            for (int i = 0; i < deviceModels.size(); i++)
                strings[i] = deviceModels.get(i).getName();
        });

        FloatingActionButton fab_add = view.findViewById(R.id.FSchedules_fab_add);
        fab_add.setOnClickListener(v -> {
            if (result.size() == 0)
                Toast.makeText(getContext(), "No Devices yet", Toast.LENGTH_SHORT).show();
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

        //btn submit
        Button btn_submit = bottom_sheet.findViewById(R.id.schedulesBotSheet_btn_submit);
        btn_submit.setOnClickListener(v1 -> {
            // add item to rv
            DeviceModel device = result.get(spinner_device.getSelectedItemPosition());
            device.setHasSchedules(true);
            StartActivity.museViewModel.updateDevice(device);

            // visibility
            not_add.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
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

                DeviceModel device = adapter.getItemAt(viewHolder.getAdapterPosition());
                device.setHasSchedules(false);
                StartActivity.museViewModel.updateDevice(device);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}