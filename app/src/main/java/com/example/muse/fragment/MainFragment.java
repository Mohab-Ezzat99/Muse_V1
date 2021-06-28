package com.example.muse.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.utility.SaveState;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private NavController navControllerMain;
    private static NotificationManager notificationManager;
    private static final String CHANNEL_ID = "first_channel";


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navControllerMain = Navigation.findNavController(requireActivity(), R.id.main_fragment);
        navControllerMain.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.alertsFragment) {
                SaveState.setNewAlert(0);
                bottomNavigationView.removeBadge(R.id.alertsFragment);
            }
        });
        NavigationUI.setupWithNavController(bottomNavigationView, navControllerMain);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.homeFragment
                , R.id.devicesFragment
                , R.id.goalFragment
                , R.id.alertsFragment
                , R.id.menuFragment)
                .build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity()
                , navControllerMain, appBarConfiguration);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(SaveState.getLastAlerts()!=0)
            bottomNavigationView.showBadge(R.id.alertsFragment).setNumber(SaveState.getLastAlerts());
        StartActivity.museViewModel.getNewAlerts().observe(requireActivity(), integer -> {
            if(integer!=0)
                bottomNavigationView.showBadge(R.id.alertsFragment).setNumber(integer);
            else
                bottomNavigationView.removeBadge(R.id.alertsFragment);
        });
    }

    public static void displayNotification(Context context, int notificationId, int current) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    "Muse channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Test notification channel");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        PendingIntent pi = new NavDeepLinkBuilder(context)
                .setComponentName(StartActivity.class)
                .setGraph(R.navigation.nav_bottom)
                .setDestination(current)
                .createPendingIntent();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_water_dispenser))
                .setContentTitle("temp")
                .setContentText("Mohab ezzat shosha")
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("My data");

        NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        nmc.notify(notificationId, builder.build());
    }
}