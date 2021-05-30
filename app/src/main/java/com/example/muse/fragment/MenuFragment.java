package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        StartActivity.setupBackgroundStatusBar(StartActivity.colorPrimaryVariant);

        RecyclerView recyclerView = view.findViewById(R.id.FMenu_rv);
        RVNavMenuAdapter adapter = new RVNavMenuAdapter();
        adapter.addItem(new NavMenuModel(getResources().getDrawable(R.drawable.ic_schedules, null), "Schedules"));
        adapter.addItem(new NavMenuModel(getResources().getDrawable(R.drawable.ic_report, null), "Report"));
        adapter.addItem(new NavMenuModel(getResources().getDrawable(R.drawable.ic_dollar, null), "Billing"));
        adapter.addItem(new NavMenuModel(getResources().getDrawable(R.drawable.ic_moon, null), "Dark mode"));
        adapter.addItem(new NavMenuModel(getResources().getDrawable(R.drawable.ic_contactus, null), "Contact us"));
        adapter.addItem(new NavMenuModel(getResources().getDrawable(R.drawable.ic_logout, null), "Logout"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.setListener(new RVNavMenuAdapter.OnItemClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onItemClick(int position) {
                //item click
                switch (position) {
                    //Schedules
                    case 0:
                        Navigation.findNavController(requireActivity(),R.id.main_fragment)
                                .navigate(R.id.action_menuFragment_to_schedulesFragment);
                        break;
                    //Report
                    case 1:
                        Navigation.findNavController(requireActivity(),R.id.main_fragment)
                                .navigate(R.id.action_menuFragment_to_reportFragment);
                        break;
                    //Billing
                    case 2:
                        Navigation.findNavController(requireActivity(),R.id.main_fragment)
                                .navigate(R.id.action_menuFragment_to_billingFragment);
                        break;
                    //Contact us
                    case 4:
                        Uri callUri = Uri.parse("tel:01205186367");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, callUri);
                        startActivity(callIntent);
                        break;
                    //Logout
                    case 5:
                        Navigation.findNavController(requireActivity(), R.id.start_fragment).popBackStack();
                        Navigation.findNavController(requireActivity(), R.id.start_fragment).navigate(R.id.loginFragment);
                        break;
                }
            }

            @Override
            public void isDarkModeChecked(boolean isChecked) {
                SaveState.setDarkModeState(isChecked);
                StartActivity.setupMode();
            }
        });
    }
}