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
import com.example.musev1.databinding.FragmentRegisterBinding;
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

    private String full_name,email,password,confirm_password;
    private ProgressDialog progressDialog;
    private NavController navController;
    private FragmentRegisterBinding binding;

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
        binding=FragmentRegisterBinding.bind(view);
        progressDialog=new ProgressDialog(getContext());

        binding.registerTvLogin.setOnClickListener(this);
        binding.registerBtnRegister.setOnClickListener(this);

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
        full_name = Objects.requireNonNull(binding.registerEtFullName.getText()).toString().trim();
        email = Objects.requireNonNull(binding.registerEtEmail.getText()).toString().trim();
        password = Objects.requireNonNull(binding.registerEtPassword.getText()).toString().trim();
        confirm_password = Objects.requireNonNull(binding.registerEtConfPassword.getText()).toString().trim();

        if (TextUtils.isEmpty(full_name)) {
            binding.registerItlFullName.setError("Full Name is required");
            binding.registerItlFullName.requestFocus();
            return;
        } else
            binding.registerItlFullName.setError(null);

        if (TextUtils.isEmpty(email)) {
            binding.registerItlEmail.setError("Email is required");
            return;
        } else
            binding.registerItlEmail.setError(null);

        if (TextUtils.isEmpty(password)) {
            binding.registerItlPassword.setError("Password is required");
            return;
        } else
            binding.registerItlPassword.setError(null);

        if (TextUtils.isEmpty(confirm_password)) {
            binding.registerItlConfPassword.setError("Confirm Password is required");
            return;
        } else
            binding.registerItlConfPassword.setError(null);

        if (!confirm_password.equals(password)) {
            binding.registerItlConfPassword.setError("Password not match");
            return;
        } else
            binding.registerItlConfPassword.setError(null);

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