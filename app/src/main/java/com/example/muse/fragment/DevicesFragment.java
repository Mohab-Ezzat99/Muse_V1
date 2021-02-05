package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.SelectedDeviceActivity;
import com.example.muse.adapters.RVAddDeviceAdapter;
import com.example.muse.adapters.RVDeviceBotAdapter;
import com.example.muse.interfaces.OnADItemListener;
import com.example.muse.model.MAddDevice;
import com.example.muse.model.MBottomDevice;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DevicesFragment extends Fragment implements OnADItemListener {

    private RecyclerView recyclerView;
    private RVAddDeviceAdapter addDeviceAdapter;
    private Group not_add;
    private BottomSheetDialog bottomSheetDialog;

    public static final String TV = "TV";
    public static final String FRIDGE = "Fridge";
    public static final String AIR = "Air Conditioner";
    public static final String DEVICE = "Another device";

    public DevicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

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

        CardView cv_add = view.findViewById(R.id.FDevices_cv_add);
        cv_add.setOnClickListener(v -> showBottomSheet(view));
        not_add = view.findViewById(R.id.FDevices_group);

        recyclerView = view.findViewById(R.id.FDevices_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // adapter with click listener
        addDeviceAdapter = new RVAddDeviceAdapter(name -> {
            Intent intent = new Intent(getActivity(), SelectedDeviceActivity.class);
            intent.putExtra("NAME", name);
            requireActivity().startActivity(intent);
        });
        recyclerView.setAdapter(addDeviceAdapter);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void showBottomSheet(View view) {
        //init
        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_device, view.findViewById(R.id.deviceBotSheet));

        //catch spinner
        RecyclerView rv = bottom_sheet.findViewById(R.id.deviceBotSheet_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));


        // adapter with click listener
        RVDeviceBotAdapter botAdapter = new RVDeviceBotAdapter(this);
        botAdapter.addItem(new MBottomDevice(getResources().getDrawable(R.drawable.ic_tv, null),
                getResources().getString(R.string.tit_tv)));
        botAdapter.addItem(new MBottomDevice(getResources().getDrawable(R.drawable.ic_fridge, null),
                getResources().getString(R.string.tit_fridge)));
        botAdapter.addItem(new MBottomDevice(getResources().getDrawable(R.drawable.ic_air_conditioner, null),
                getResources().getString(R.string.tit_air_conditioner)));
        botAdapter.addItem(new MBottomDevice(getResources().getDrawable(R.drawable.ic_plug, null),
                getResources().getString(R.string.tit_another_device)));
        rv.setAdapter(botAdapter);

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void OnItemClick(String name) {
        switch (name) {
            case TV:
                addDeviceAdapter.addItem(new MAddDevice(getResources().getDrawable(R.drawable.ic_tv, null),
                        true, TV, "50", 50));
                break;

            case FRIDGE:
                addDeviceAdapter.addItem(new MAddDevice(getResources().getDrawable(R.drawable.ic_fridge, null),
                        true, FRIDGE, "50", 50));
                break;

            case AIR:
                addDeviceAdapter.addItem(new MAddDevice(getResources().getDrawable(R.drawable.ic_air_conditioner, null),
                        true, AIR, "50", 50));
                break;

            case DEVICE:
                addDeviceAdapter.addItem(new MAddDevice(getResources().getDrawable(R.drawable.ic_plug, null),
                        true, DEVICE, "50", 50));
                break;
        }
        // visibility
        not_add.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        bottomSheetDialog.dismiss();
    }
}