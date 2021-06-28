package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.adapters.OnDeviceItemListener;
import com.example.muse.adapters.RVDeviceBotAdapter;
import com.example.muse.model.DeviceModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class DeviceSettingFragment extends Fragment {

    private ImageView iv_icon;
    private TextInputEditText et_deviceName;
    private TextView tv_name;
    private DeviceModel device;
    private NavController navController;
    private BottomSheetDialog bottomSheetDialog;

    public DeviceSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            DeviceSettingFragmentArgs args = DeviceSettingFragmentArgs.fromBundle(getArguments());
            device=args.getDeviceSetting();
        }
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_device_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController= Navigation.findNavController(requireActivity(),R.id.main_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_icon=view.findViewById(R.id.DeviceSetting_iv_icon);
        et_deviceName=view.findViewById(R.id.DeviceSetting_et_deviceName);
        tv_name=view.findViewById(R.id.DeviceSetting_tv_name);

        iv_icon.setImageResource(device.getIcon());
        tv_name.setText(device.getName());
        et_deviceName.setText(device.getName());
        et_deviceName.requestFocus();
        iv_icon.setOnClickListener(this::showBottomSheet);
    }

    @SuppressLint("SetTextI18n")
    public void showBottomSheet(View view) {
        //init
        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_device, view.findViewById(R.id.deviceBotSheet));

        TextView tv_title = bottom_sheet.findViewById(R.id.deviceBotSheet_tv_title);
        tv_title.setText("Select icon");

        RecyclerView rv = bottom_sheet.findViewById(R.id.deviceBotSheet_rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // adapter with click listener
        RVDeviceBotAdapter botAdapter = new RVDeviceBotAdapter(getContext());
        StartActivity.museViewModel.getAllDevices().observe(getViewLifecycleOwner(), deviceModels -> botAdapter.setList((ArrayList<DeviceModel>) deviceModels));
        rv.setAdapter(botAdapter);

        botAdapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceModel device1) {
                iv_icon.setImageResource(device1.getIcon());
                device.setIcon(device1.getIcon());
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_device_setting,menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_delete:
                StartActivity.museViewModel.deleteDevice(device);
                navController.navigate(R.id.devicesFragment);
                Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_save:
                device.setName(Objects.requireNonNull(et_deviceName.getText()).toString());
                StartActivity.museViewModel.updateDevice(device);
                navController.popBackStack();
                Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                navController.popBackStack();
                break;

        }
        return true;
    }
}