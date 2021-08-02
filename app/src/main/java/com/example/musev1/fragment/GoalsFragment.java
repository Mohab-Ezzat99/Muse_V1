package com.example.musev1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.adapters.RVAddGoalAdapter;
import com.example.musev1.model.DeviceModel;
import com.example.musev1.model.GoalModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class GoalsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RVAddGoalAdapter adapter;
    private Group not_add;
    private String[] strings;
    private List<DeviceModel> result_devices;

    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        return inflater.inflate(R.layout.fragment_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);
        not_add = view.findViewById(R.id.FGoals_group);

        //recycleView
        recyclerView = view.findViewById(R.id.FGoals_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RVAddGoalAdapter(getContext());
        recyclerView.setAdapter(adapter);
        setupSwipe();

        MainActivity.museViewModel.getAllGoals().observe(getViewLifecycleOwner(), goalModels -> {
            if (goalModels.size() != 0) {
                // visibility
                not_add.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                // visibility
                not_add.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            adapter.submitList(goalModels);
        });

        MainActivity.museViewModel.getAvailableGoals().observe(getViewLifecycleOwner(), deviceModels -> {
            result_devices = deviceModels;
            strings = new String[deviceModels.size()];
            for (int i = 0; i < deviceModels.size(); i++)
                strings[i] = deviceModels.get(i).getName();
        });

        // fab add
        FloatingActionButton fab_add = view.findViewById(R.id.FGoals_fab_add);
        fab_add.setOnClickListener(v -> {
            if (result_devices.size() == 0)
                Toast.makeText(getContext(), "No device found to set goal", Toast.LENGTH_LONG).show();
            else
                showBottomSheet(view);
        });
    }

    public void showBottomSheet(View view) {
        //init
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_goal, view.findViewById(R.id.goalBotSheet));

        //catch spinner
        Spinner spinner_device = bottom_sheet.findViewById(R.id.goalBotSheet_spinner_devices);
        ArrayAdapter<String> stringsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
        stringsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_device.setAdapter(stringsAdapter);

        Spinner spinner_agg = bottom_sheet.findViewById(R.id.goalBotSheet_spinner_agg);
        Spinner spinner_values = bottom_sheet.findViewById(R.id.goalBotSheet_spinner_values);

        Button btn_submit = bottom_sheet.findViewById(R.id.goalBotSheet_btn_submit);
        btn_submit.setOnClickListener(v1 -> {
            // add item to rv
            DeviceModel device = result_devices.get(spinner_device.getSelectedItemPosition());
            device.setHasGoal(true);
            MainActivity.museViewModel.updateDevice(device);

            MainActivity.museViewModel.insertGoal(new GoalModel(device.getId(),device.getName(),device.getIcon()
                    , spinner_agg.getSelectedItemPosition()
                    , spinner_values.getSelectedItem().toString(), 0));

            bottomSheetDialog.dismiss();
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    private void setupSwipe()
    {
        ItemTouchHelper.SimpleCallback callback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                GoalModel goalModel = adapter.getItemAt(viewHolder.getAdapterPosition());

                MainActivity.museViewModel.getDevice(goalModel.getDeviceId())
                        .observe(getViewLifecycleOwner(), deviceModel -> {
                            deviceModel.setHasGoal(false);
                            MainActivity.museViewModel.updateDevice(deviceModel);
                        });

                MainActivity.museViewModel.deleteGoal(goalModel);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}