package com.example.musev1.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.adapters.RVAddDeviceAdapter;
import com.example.musev1.adapters.RVDeviceBotAdapter;
import com.example.musev1.databinding.FragmentDevicesBinding;
import com.example.musev1.interfaces.OnDeviceItemListener;
import com.example.musev1.model.AlertModel;
import com.example.musev1.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class DevicesFragment extends Fragment implements MenuItem.OnMenuItemClickListener {

    private RVAddDeviceAdapter addDeviceAdapter;
    private BottomSheetDialog bottomSheetDialog;
    private NavController navController;
    private DeviceModel currentDevice;
    private FragmentDevicesBinding binding;

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
        binding = FragmentDevicesBinding.bind(view);

        //StatusBar color
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);

        // recycleView
        binding.FDevicesRv.setHasFixedSize(true);
        binding.FDevicesRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        addDeviceAdapter = new RVAddDeviceAdapter(getContext());
        binding.FDevicesRv.setAdapter(addDeviceAdapter);

        binding.FDevicesFabAdd.setOnClickListener(v -> displayPlug());

        MainActivity.museViewModel.getAllDevices().observe(getViewLifecycleOwner(), deviceModels -> {
            if (deviceModels.size() != 0) {
                // visibility
                binding.FDevicesGroup.setVisibility(View.GONE);
                binding.FDevicesRv.setVisibility(View.VISIBLE);
            } else {
                // visibility
                binding.FDevicesGroup.setVisibility(View.VISIBLE);
                binding.FDevicesRv.setVisibility(View.GONE);
            }
            addDeviceAdapter.setDeviceModels(deviceModels);
        });

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

        addDeviceAdapter.setSwitchListener((device, isOn) -> {
            device.setOn(isOn);
            MainActivity.museViewModel.updateDevice(device);
        });
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
        if (item.getItemId() == R.id.popup_delete) {
            MainActivity.museViewModel.deleteDevice(currentDevice);
            return true;
        }
        return false;
    }

    public void displayPlug(){
        final AlertDialog.Builder builderPlug = new AlertDialog.Builder(requireContext(),R.style.DialogStyle);
        final View viewPlug = LayoutInflater.from(getContext()).inflate(R.layout.dialog_plug, null, false);
        builderPlug.setView(viewPlug);
        builderPlug.setCancelable(false);
        final AlertDialog alertDialogPlug = builderPlug.create();
        alertDialogPlug.show();

        final TextView tv_next = viewPlug.findViewById(R.id.dialogPlug_tv_next);
        final TextView tv_plugCancel = viewPlug.findViewById(R.id.dialogPlug_tv_cancel);

        tv_next.setOnClickListener(v -> {
            displayWifi();
            alertDialogPlug.dismiss();
        });

        tv_plugCancel.setOnClickListener(v -> alertDialogPlug.dismiss());

    }

    public void displayWifi() {
        final AlertDialog.Builder builderWifi = new AlertDialog.Builder(requireContext(), R.style.DialogStyle);
        final View viewWifi = LayoutInflater.from(getContext()).inflate(R.layout.dialog_wifi, null, false);
        builderWifi.setView(viewWifi);
        builderWifi.setCancelable(false);
        final AlertDialog alertDialogWifi = builderWifi.create();
        alertDialogWifi.show();

        final TextView tv_submit = viewWifi.findViewById(R.id.dialogWifi_tv_submit);
        final TextView tv_wifiCancel = viewWifi.findViewById(R.id.dialogWifi_tv_cancel);

        tv_submit.setOnClickListener(v -> {
            MainActivity.hideKeyboardFrom(requireContext(), binding.FDevicesFabAdd);
            alertDialogWifi.dismiss();
            showBottomSheet(binding.FDevicesFabAdd);
        });

        tv_wifiCancel.setOnClickListener(v -> alertDialogWifi.dismiss());

    }

    public void showBottomSheet(View view) {
        //init
        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_device, view.findViewById(R.id.deviceBotSheet));

        // recycleView
        RecyclerView rv = bottom_sheet.findViewById(R.id.deviceBotSheet_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        RVDeviceBotAdapter botAdapter = new RVDeviceBotAdapter();
        botAdapter.setList(MainActivity.modelArrayList);
        rv.setAdapter(botAdapter);

        botAdapter.setListener((device, position) -> {
            //init device
            Long deviceId=MainActivity.museViewModel.insertDevice(device);
            MainActivity.museViewModel.insertAlert(new AlertModel(deviceId, device.getName()
                    , device.getIcon(), "Added successfully"));
            bottomSheetDialog.dismiss();
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }
}