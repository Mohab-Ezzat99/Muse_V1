package com.example.muse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.RVAlertAdapter;
import com.example.muse.model.MAlert;

import java.util.Objects;

public class AlertsFragment extends Fragment {

    private RecyclerView recyclerView;

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
        StartActivity.setupBackgroundStatusBar(StartActivity.colorPrimaryVarient);


        recyclerView = view.findViewById(R.id.FAlerts_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RVAlertAdapter adapter=new RVAlertAdapter(getContext());
        adapter.addItem(new MAlert(ContextCompat.getDrawable(requireContext(), R.drawable.ic_air_conditioner)
                ,StartActivity.AIR, requireActivity().getResources().getString(R.string.txt_has_been_on_for_more_3_hrs)));
        adapter.addItem(new MAlert(ContextCompat.getDrawable(requireContext(), R.drawable.ic_tv)
                ,StartActivity.TV, requireActivity().getResources().getString(R.string.txt_has_been_on_for_more_3_hrs)));
        adapter.addItem(new MAlert(ContextCompat.getDrawable(requireContext(), R.drawable.ic_fridge)
                ,StartActivity.FRIDGE,requireActivity().getResources().getString(R.string.txt_has_been_on_for_more_3_hrs)));
        adapter.addItem(new MAlert(ContextCompat.getDrawable(requireContext(), R.drawable.ic_tv)
                ,StartActivity.TV,requireActivity().getResources().getString(R.string.txt_has_been_on_for_more_3_hrs)));
        adapter.addItem(new MAlert(ContextCompat.getDrawable(requireContext(), R.drawable.ic_air_conditioner)
                ,StartActivity.AIR,requireActivity().getResources().getString(R.string.txt_has_been_on_for_more_3_hrs)));
        adapter.addItem(new MAlert(ContextCompat.getDrawable(requireContext(), R.drawable.ic_fridge)
                ,StartActivity.FRIDGE,requireActivity().getResources().getString(R.string.txt_has_been_on_for_more_3_hrs)));
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.FAlerts_fab_add).setOnClickListener(v -> Toast.makeText(getContext(), "Soon", Toast.LENGTH_SHORT).show());
    }
}