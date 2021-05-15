package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.RVNavMenuAdapter;
import com.example.muse.model.MNavMenu;

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
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(true);
        StartActivity.mTitle.setText(StartActivity.toolbar.getTitle());
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //status bar color
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.cyan, null));
        int flags = window.getDecorView().getSystemUiVisibility(); // get current flag
        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
        window.getDecorView().setSystemUiVisibility(flags);

        RecyclerView recyclerView = view.findViewById(R.id.FMenu_rv);
        RVNavMenuAdapter adapter = new RVNavMenuAdapter();
        adapter.addItem(new MNavMenu(getResources().getDrawable(R.drawable.ic_schedules, null),"Schedules"));
        adapter.addItem(new MNavMenu(getResources().getDrawable(R.drawable.ic_report, null),"Report"));
        adapter.addItem(new MNavMenu(getResources().getDrawable(R.drawable.ic_settings, null),"Settings"));
        adapter.addItem(new MNavMenu(getResources().getDrawable(R.drawable.ic_moon, null),"Dark mode"));
        adapter.addItem(new MNavMenu(getResources().getDrawable(R.drawable.ic_contactus, null),"Contact us"));
        adapter.addItem(new MNavMenu(getResources().getDrawable(R.drawable.ic_logout, null),"Logout"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}