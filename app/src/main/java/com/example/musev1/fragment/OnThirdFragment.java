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

        btn_submit.setOnClickListener(v -> viewPager.setCurrentItem(3));
    }
}