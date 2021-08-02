package com.example.musev1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.interfaces.OnAlertItemListener;
import com.example.musev1.interfaces.OnDeviceItemListener;
import com.example.musev1.adapters.RVAlertAdapter;
import com.example.musev1.model.AlertModel;
import com.example.musev1.model.DeviceModel;

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
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);

        //init
        not_add = view.findViewById(R.id.FAlerts_group);
        recyclerView = view.findViewById(R.id.FAlerts_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //recycleView
        adapter = new RVAlertAdapter(getContext());
        recyclerView.setAdapter(adapter);
        setupSwipe();

        MainActivity.museViewModel.getAllAlerts().observe(getViewLifecycleOwner(), deviceModels -> {
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

        adapter.setListener(alertModel ->
                MainActivity.museViewModel.getDevice(alertModel.getDeviceId())
                        .observe(getViewLifecycleOwner(), deviceModel ->
                                navController.navigate(AlertsFragmentDirections
                                        .actionAlertsFragmentToSelectedDeviceFragment(deviceModel))));
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

                AlertModel alertModel = adapter.getItemAt(viewHolder.getAdapterPosition());
                MainActivity.museViewModel.deleteAlert(alertModel);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}