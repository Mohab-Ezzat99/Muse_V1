package com.example.muse.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.muse.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Locale;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private NavController navControllerMain;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navControllerMain = Navigation.findNavController(requireActivity(), R.id.main_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navControllerMain);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.homeFragment
                , R.id.devicesFragment
                , R.id.goalFragment
                , R.id.alertsFragment
                , R.id.menuFragment)
                .build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity()
                ,navControllerMain,appBarConfiguration);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}