package com.example.musev1.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.model.DeviceRequestModel;
import com.example.musev1.model.DeviceResponseModel;
import com.example.musev1.network.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnThirdFragment extends Fragment {
    public static final String MQTT_SERVER="broker.hivemq.com:1883";
    private TextInputEditText et_ssid,et_password;
    private Button btn_submit;
    private ApiService apiService;
    private Call<JSONObject> call;
    private String SSID,password;
    private ProgressDialog progressDialog;

    public OnThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_on_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager= requireActivity().findViewById(R.id.onBoard_vp);
        et_ssid=view.findViewById(R.id.onThird_et_ssid);
        et_password=view.findViewById(R.id.onThird_et_password);
        btn_submit=view.findViewById(R.id.onThird_btn_submit);
        progressDialog = new ProgressDialog(getContext());

        btn_submit.setOnClickListener(v -> {
            Call<DeviceResponseModel> call=MainActivity.museViewModel.addHouse(new DeviceRequestModel(0,"House","Mohab Ezzat"));
            call.enqueue(new Callback<DeviceResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<DeviceResponseModel> call, @NotNull Response<DeviceResponseModel> response) {
                    Toast.makeText(getContext(), response.body().getName(), Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(3);
//                    progressDialog.setMessage("Please wait...");
//                    progressDialog.show();
//                    ((ProgressBar)progressDialog.findViewById(android.R.id.progress))
//                            .getIndeterminateDrawable()
//                            .setColorFilter(MainActivity.colorPrimaryVariant, android.graphics.PorterDuff.Mode.SRC_IN);
//                    progressDialog.setCanceledOnTouchOutside(false);
//
//                    SSID=et_ssid.getText().toString().trim();
//                    password=et_password.getText().toString().trim();
//
//                    apiService= RetrofitBuilder.getPlugInstance().create(ApiService.class);
//                    apiService.setMqtt(1,MQTT_SERVER);
//                    Call<JSONObject> call2=apiService.setWifi(1,SSID,password);
//                    call2.enqueue(new Callback<JSONObject>() {
//                        @Override
//                        public void onResponse(@NotNull Call<JSONObject> call, @NotNull Response<JSONObject> response) {
//                            Toast.makeText(getContext(), "Connected Successfully", Toast.LENGTH_SHORT).show();
//                            viewPager.setCurrentItem(3);
//                            progressDialog.dismiss();
//                        }
//
//                        @Override
//                        public void onFailure(@NotNull Call<JSONObject> call, @NotNull Throwable t) {
//                            Toast.makeText(getContext(), "WIFI Failed", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        }
//                    });
                }

                @Override
                public void onFailure(@NotNull Call<DeviceResponseModel> call, @NotNull Throwable t) {

                }
            });
        });
    }
}