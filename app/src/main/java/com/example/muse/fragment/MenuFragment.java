package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.RVNavMenuAdapter;
import com.example.muse.model.NavMenuModel;
import com.example.muse.utility.SaveState;

import java.util.Objects;

public class MenuFragment extends Fragment {

    private NavController navController_main;
    private NavController navController_start;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController_main = Navigation.findNavController(requireActivity(), R.id.main_fragment);
        navController_start = Navigation.findNavController(requireActivity(), R.id.start_fragment);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

        //StatusBar color
        StartActivity.setupBackgroundStatusBar(StartActivity.colorPrimaryVariant);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.FMenu_rv);
        RVNavMenuAdapter adapter = new RVNavMenuAdapter(getContext());
        adapter.addItem(new NavMenuModel(R.drawable.ic_schedules, "Schedules"));
        adapter.addItem(new NavMenuModel(R.drawable.ic_notification, "Notifications"));
        adapter.addItem(new NavMenuModel(R.drawable.ic_moon, "Dark mode"));
        adapter.addItem(new NavMenuModel(R.drawable.ic_contactus, "Contact us"));
        adapter.addItem(new NavMenuModel(R.drawable.ic_logout, "Logout"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setListener(new RVNavMenuAdapter.OnItemClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onItemClick(int position) {
                //item click
                switch (position) {
                    //Schedules
                    case 0:
                        navController_main.navigate(R.id.action_menuFragment_to_schedulesFragment);
                        break;
                    //Contact us
                    case 3:
                        Uri callUri = Uri.parse("tel:01205186367");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, callUri);
                        startActivity(callIntent);
                        break;
                    //Logout
                    case 4:
                        StartActivity.mAuth.signOut();
                        navController_start.popBackStack();
                        navController_start.navigate(R.id.loginFragment);
                        break;
                }
            }

            @Override
            public void isDarkModeChecked(boolean isChecked) {
                SaveState.setDarkModeState(isChecked);
                StartActivity.setupMode();
            }

            @Override
            public void isNotificationChecked(boolean isChecked) {
                SaveState.setNotificationState(isChecked);
            }
        });
    }
}