package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.Objects;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    @SuppressLint("StaticFieldLeak")
    public static NavController navControllerMain;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        //bottom nav
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        navControllerMain = Navigation.findNavController(requireActivity(), R.id.main_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navControllerMain);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.homeFragment
                , R.id.devicesFragment
                , R.id.goalFragment
                , R.id.alertsFragment
                , R.id.menuFragment)
                .build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navControllerMain,appBarConfiguration);

        //set English
        Configuration config = requireContext().getResources().getConfiguration();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        config.locale = locale;
        requireContext().getResources().updateConfiguration(config,
                requireContext().getResources().getDisplayMetrics());
    }
}