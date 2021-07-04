package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.MainActivity;
import com.example.muse.R;
import com.example.muse.adapters.OnDeviceItemListener;
import com.example.muse.adapters.RVAddDeviceAdapter;
import com.example.muse.adapters.RVDeviceBotAdapter;
import com.example.muse.model.AlertModel;
import com.example.muse.model.DeviceModel;
import com.example.muse.model.DeviceRequestModel;
import com.example.muse.model.DeviceResponseModel;
import com.example.muse.utility.SaveState;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevicesFragment extends Fragment implements MenuItem.OnMenuItemClickListener {

    private RecyclerView recyclerView;
    private RVAddDeviceAdapter addDeviceAdapter;
    private Group not_add;
    private CardView cv_aggregation, cv_unit;
    private FloatingActionButton fab_add;
    private BottomSheetDialog bottomSheetDialog;
    private NavController navController;
    private DeviceRequestModel currentDevice;

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
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);

        not_add = view.findViewById(R.id.FDevices_group);
        cv_aggregation = view.findViewById(R.id.FDevices_cv_aggregation);
        cv_unit = view.findViewById(R.id.FDevices_cv_unit);

        // recycleView
        recyclerView = view.findViewById(R.id.FDevices_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        addDeviceAdapter = new RVAddDeviceAdapter(getContext());
        recyclerView.setAdapter(addDeviceAdapter);

        fab_add = view.findViewById(R.id.FDevices_fab_add);
        fab_add.setOnClickListener(v -> displayPlug());

        getAllDevicesReq(0, 0);

        addDeviceAdapter.setSwitchListener((device, state) -> {
            MainActivity.displayLoadingDialog();
            MainActivity.museViewModel.setState(device.getId(), state).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful())
                        Toast.makeText(getContext(), "Updated state", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    MainActivity.progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    MainActivity.progressDialog.dismiss();
                }
            });
        });

        addDeviceAdapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceRequestModel device) {
                navController.navigate(DevicesFragmentDirections.actionDevicesFragmentToSelectedDeviceFragment(device.getId()));
            }

            @Override
            public void OnItemClick(AlertModel alertModel) {

            }

            @Override
            public void OnBottomSheetItemClick(DeviceModel device, int position) {

            }

            @Override
            public void OnItemLongClick(View view, DeviceRequestModel device) {
                showPopup(view);
                currentDevice = device;
            }
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
            MainActivity.displayLoadingDialog();
            MainActivity.museViewModel.deleteDeviceById(currentDevice.getId()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                        getAllDevicesReq(0,0);
                    }
                    else
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    MainActivity.progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    MainActivity.progressDialog.dismiss();
                }
            });
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

    public void displayWifi(){
        final AlertDialog.Builder builderWifi = new AlertDialog.Builder(requireContext(),R.style.DialogStyle);
        final View viewWifi = LayoutInflater.from(getContext()).inflate(R.layout.dialog_wifi, null, false);
        builderWifi.setView(viewWifi);
        builderWifi.setCancelable(false);
        final AlertDialog alertDialogWifi = builderWifi.create();
        alertDialogWifi.show();

        final TextView tv_submit = viewWifi.findViewById(R.id.dialogWifi_tv_submit);
        final TextView tv_wifiCancel = viewWifi.findViewById(R.id.dialogWifi_tv_cancel);

        tv_submit.setOnClickListener(v -> {
            showBottomSheet(fab_add);
            alertDialogWifi.dismiss();
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
        RVDeviceBotAdapter botAdapter = new RVDeviceBotAdapter(getContext());
        botAdapter.setList(MainActivity.modelArrayList);
        rv.setAdapter(botAdapter);

        botAdapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceRequestModel device) {

            }

            @Override
            public void OnItemClick(AlertModel alertModel) {

            }

            @Override
            public void OnBottomSheetItemClick(DeviceModel device, int position) {
                MainActivity.displayLoadingDialog();
                //init device
//                SaveState.setNewAlert((SaveState.getLastAlerts()) + 1);
                DeviceRequestModel requestModel = new DeviceRequestModel(position, device.getName(), "MMM");
                MainActivity.museViewModel.addDevice(requestModel).enqueue(new Callback<DeviceResponseModel>() {
                    @Override
                    public void onResponse(@NotNull Call<DeviceResponseModel> call, @NotNull Response<DeviceResponseModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                            getAllDevicesReq(0,0);
                        }
                        else
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<DeviceResponseModel> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }
                });
                bottomSheetDialog.dismiss();
            }

            @Override
            public void OnItemLongClick(View view, DeviceRequestModel device) {

            }
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    public void getAllDevicesReq(int aggregation, int unit) {
        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getAllDevicesRequest(aggregation, unit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            if (result.size() != 0) {
                                // visibility
                                not_add.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                cv_aggregation.setVisibility(View.VISIBLE);
                                cv_unit.setVisibility(View.VISIBLE);
                            } else {
                                // visibility
                                not_add.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                cv_aggregation.setVisibility(View.GONE);
                                cv_unit.setVisibility(View.GONE);
                            }
                            addDeviceAdapter.setDeviceRequestModels(result);
                            MainActivity.progressDialog.dismiss();
                        },
                        error -> {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }
}