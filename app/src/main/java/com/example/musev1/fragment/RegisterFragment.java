package com.example.musev1.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.model.AuthModel;
import com.example.musev1.utility.SaveState;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private TextView tv_login;
    private Button btn_register;
    private String full_name, email, password, confirm_password;
    private ProgressDialog progressDialog;
    private TextInputLayout itl_fullName, itl_email, itl_password, itl_confirm;
    private TextInputEditText et_fullName, et_email, et_password, et_confirm;
    private NavController navController;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        navController=Navigation.findNavController(requireActivity(),R.id.start_fragment);

        //StatusBar color
        if (SaveState.getDarkModeState())
            MainActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            MainActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_fullName = view.findViewById(R.id.register_et_fullName);
        et_email = view.findViewById(R.id.register_et_email);
        et_password = view.findViewById(R.id.register_et_password);
        et_confirm = view.findViewById(R.id.register_et_confPassword);
        itl_fullName = view.findViewById(R.id.register_itl_fullName);
        itl_email = view.findViewById(R.id.register_itl_email);
        itl_password = view.findViewById(R.id.register_itl_password);
        itl_confirm = view.findViewById(R.id.register_itl_confPassword);

        tv_login = view.findViewById(R.id.register_tv_login);
        btn_register = view.findViewById(R.id.register_btn_register);
        progressDialog=new ProgressDialog(getContext());

        tv_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        //back pressed
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        navController.navigate(R.id.action_registerFragment_to_loginFragment);
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_tv_login:
                if (isAdded())
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                break;
            case R.id.register_btn_register:
                setupRegister();
        }
    }

    public void  setupRegister(){
        full_name = Objects.requireNonNull(et_fullName.getText()).toString().trim();
        email = Objects.requireNonNull(et_email.getText()).toString().trim();
        password = Objects.requireNonNull(et_password.getText()).toString().trim();
        confirm_password = Objects.requireNonNull(et_confirm.getText()).toString().trim();

        if (TextUtils.isEmpty(full_name)) {
            itl_fullName.setError("Full Name is required");
            itl_fullName.requestFocus();
            return;
        } else
            itl_fullName.setError(null);

        if (TextUtils.isEmpty(email)) {
            itl_email.setError("Email is required");
            return;
        } else
            itl_email.setError(null);

        if (TextUtils.isEmpty(password)) {
            itl_password.setError("Password is required");
            return;
        } else
            itl_password.setError(null);

        if (TextUtils.isEmpty(confirm_password)) {
            itl_confirm.setError("Confirm Password is required");
            return;
        } else
            itl_confirm.setError(null);

        if (!confirm_password.equals(password)) {
            itl_confirm.setError("Password not match");
            return;
        } else
            itl_confirm.setError(null);

        if (SaveState.checkConnection(requireContext())) {
            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ((ProgressBar)progressDialog.findViewById(android.R.id.progress))
                .getIndeterminateDrawable()
                .setColorFilter(MainActivity.colorPrimaryVariant, android.graphics.PorterDuff.Mode.SRC_IN);
        progressDialog.setCanceledOnTouchOutside(false);

        Call<ResponseBody> call= MainActivity.museViewModel.register(new AuthModel(full_name,email,password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if(response.body()==null)
                    Toast.makeText(getContext(), "Error! check fields", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}