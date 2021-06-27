package com.example.muse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.RVAlertAdapter;
import com.example.muse.adapters.OnDeviceItemListener;
import com.example.muse.model.DeviceModel;

import java.util.Objects;

public class AlertsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Group not_add;
    private RVAlertAdapter adapter;
    private NavController navController;

    public AlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        navController= Navigation.findNavController(requireActivity(),R.id.main_fragment);
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

        adapter=new RVAlertAdapter(getContext());
        recyclerView.setAdapter(adapter);
        setupSwipe();
        adapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceModel device) {
                navController.navigate(AlertsFragmentDirections.actionAlertsFragmentToSelectedDeviceFragment(device));
            }

            @Override
            public void OnItemLongClick(View view, DeviceModel device) {

            }
        });

        StartActivity.museViewModel.getDevicesAlerts().observe(getViewLifecycleOwner(), deviceModels -> {
            if(deviceModels.size()!=0)
            {
                // visibility
                not_add.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            else {
                // visibility
                not_add.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            adapter.submitList(deviceModels);
        });
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

                DeviceModel device=adapter.getItemAt(viewHolder.getAdapterPosition());
                device.setHasAlert(false);
                StartActivity.museViewModel.updateDevice(device);
            }
        };

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}