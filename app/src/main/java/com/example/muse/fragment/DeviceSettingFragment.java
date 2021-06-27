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

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.model.DeviceModel;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DeviceSettingFragment extends Fragment {

    private ImageView iv_icon;
    private TextInputEditText et_deviceName;
    private TextView tv_name;
    private DeviceModel device;
    private NavController navController;

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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_icon=view.findViewById(R.id.DeviceSetting_iv_icon);
        et_deviceName=view.findViewById(R.id.DeviceSetting_et_deviceName);
        tv_name=view.findViewById(R.id.DeviceSetting_tv_name);

        iv_icon.setImageDrawable(getResources().getDrawable(device.getIcon(),null));
        tv_name.setText(device.getName());
        et_deviceName.setText(device.getName());
        et_deviceName.requestFocus();
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