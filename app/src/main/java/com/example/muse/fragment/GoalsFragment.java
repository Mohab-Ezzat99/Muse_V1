package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.RVAddGoalAdapter;
import com.example.muse.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class GoalsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RVAddGoalAdapter adapter;
    private Group not_add;

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
        StartActivity.setupBackgroundStatusBar(StartActivity.colorPrimaryVariant);

        FloatingActionButton fab_add = view.findViewById(R.id.FGoals_fab_add);
        fab_add.setOnClickListener(v -> showBottomSheet(view));
        not_add = view.findViewById(R.id.FGoals_group);

        recyclerView = view.findViewById(R.id.FGoals_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RVAddGoalAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void showBottomSheet(View view) {
        //init
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_goal, view.findViewById(R.id.goalBotSheet));

        //catch spinner
        Spinner spinner_device = bottom_sheet.findViewById(R.id.goalBotSheet_spinner_devices);
        ArrayList<String> values = new ArrayList<>();
        values.add("Devices");
        values.add(StartActivity.DEVICE);

        String[] strings = new String[values.size()];
        for (int i = 0; i < values.size(); i++)
            strings[i] = values.get(i);

        ArrayAdapter<String> stringsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
        stringsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_device.setAdapter(stringsAdapter);

        Button btn_submit = bottom_sheet.findViewById(R.id.goalBotSheet_btn_submit);
        btn_submit.setOnClickListener(v1 -> {
            // add item to rv
            adapter.addItem(new DeviceModel(getResources().getDrawable(R.drawable.ic_plug, null),StartActivity.DEVICE));

            // visibility
            not_add.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            bottomSheetDialog.dismiss();
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }
}