package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.OnDeviceItemListener;
import com.example.muse.adapters.RVAddDeviceAdapter;
import com.example.muse.adapters.RVDeviceBotAdapter;
import com.example.muse.model.DeviceModel;
import com.example.muse.utility.SaveState;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class DevicesFragment extends Fragment implements MenuItem.OnMenuItemClickListener {

    private RecyclerView recyclerView;
    private RVAddDeviceAdapter addDeviceAdapter;
    private Group not_add;
    private BottomSheetDialog bottomSheetDialog;
    private NavController navController;
    private DeviceModel currentDevice;

    public DevicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        navController=Navigation.findNavController(requireActivity(),R.id.main_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        StartActivity.setupBackgroundStatusBar(StartActivity.colorPrimaryVariant);

        FloatingActionButton fab_add = view.findViewById(R.id.FDevices_fab_add);
        fab_add.setOnClickListener(v -> showBottomSheet(view));
        not_add = view.findViewById(R.id.FDevices_group);

        recyclerView = view.findViewById(R.id.FDevices_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // adapter with click listener
        addDeviceAdapter = new RVAddDeviceAdapter(getContext());
        recyclerView.setAdapter(addDeviceAdapter);
        addDeviceAdapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceModel device) {
                navController.navigate(DevicesFragmentDirections.actionDevicesFragmentToSelectedDeviceFragment(device));
            }

            @Override
            public void OnItemLongClick(View view, DeviceModel device) {
                showPopup(view);
                currentDevice = device;
            }
        });

        StartActivity.museViewModel.getAllDevices().observe(getViewLifecycleOwner(), deviceModels -> {
            if (deviceModels.size() != 0) {
                // visibility
                not_add.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                // visibility
                not_add.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            addDeviceAdapter.setDeviceModels(deviceModels);
        });
    }

    public void showBottomSheet(View view) {
        //init
        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_device, view.findViewById(R.id.deviceBotSheet));

        RecyclerView rv = bottom_sheet.findViewById(R.id.deviceBotSheet_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // adapter with click listener
        RVDeviceBotAdapter botAdapter = new RVDeviceBotAdapter(getContext());
        botAdapter.setList(StartActivity.deviceModels);
        rv.setAdapter(botAdapter);

        botAdapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceModel device) {
                //init device
                device.initDevice("50%", 50, true);
                device.setAlertMessage("turn on for more 3h!");
                device.setHasAlert(true);
                SaveState.setNewAlert((SaveState.getLastAlerts()) + 1);

                //add to list & room
                StartActivity.museViewModel.insertDevice(device);
                bottomSheetDialog.dismiss();
            }

            @Override
            public void OnItemLongClick(View view, DeviceModel device) {

            }
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
        popupMenu.inflate(R.menu.menu_popup);
        popupMenu.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.popup_delete:
                StartActivity.museViewModel.deleteDevice(currentDevice);
                Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.popup_settings:
                navController.navigate(DevicesFragmentDirections.actionDevicesFragmentToDeviceSettingFragment(currentDevice));
                return true;

            default:
                return false;
        }
    }
}