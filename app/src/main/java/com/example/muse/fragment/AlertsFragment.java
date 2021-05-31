package com.example.muse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.RVAlertAdapter;
import com.example.muse.model.DeviceModel;

import java.util.List;
import java.util.Objects;

public class AlertsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Group not_add;

    public AlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        StartActivity.setupBackgroundStatusBar(StartActivity.colorPrimaryVariant);

        not_add = view.findViewById(R.id.FAlerts_group);
        recyclerView = view.findViewById(R.id.FAlerts_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RVAlertAdapter adapter=new RVAlertAdapter(getContext());
        recyclerView.setAdapter(adapter);

        StartActivity.museViewModel.getAllDevices().observe(getViewLifecycleOwner(), deviceModels -> {
            if(deviceModels.size()!=0)
            {
                // visibility
                not_add.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            adapter.setList(deviceModels);
        });
    }
}