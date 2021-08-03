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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.musev1.MainActivity;
import com.example.musev1.R;
import com.example.musev1.databinding.FragmentLoginBinding;
import com.example.musev1.model.LoginResponseModel;
import com.example.musev1.utility.SaveState;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;

import static com.example.musev1.MainActivity.mAuth;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private String email, password;
    private NavController navController;
    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // hide action bar
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

        // init nav graph
        navController = Navigation.findNavController(requireActivity(), R.id.start_fragment);
        if (mAuth.getCurrentUser() != null) {
            if (SaveState.getShownOnBoarding())
                navController.navigate(R.id.action_loginFragment_to_mainFragment);
            else
                navController.navigate(R.id.action_loginFragment_to_onBoardFragment);
        }

        //StatusBar color
        if (SaveState.getDarkModeState())
            MainActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            MainActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentLoginBinding.bind(view);

        binding.loginTvForgot.setOnClickListener(this);
        binding.loginTvSignUp.setOnClickListener(this);
        binding.loginBtnLogin.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_tv_forgot:
                setupForgotPassword();
                break;

            case R.id.login_tv_signUp:
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
                break;

            case R.id.login_btn_login:
                setupLogin();
        }
    }

    public void setupForgotPassword() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(),R.style.DialogStyle);
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_forgot, null, false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final TextInputEditText et_resetEmail = view.findViewById(R.id.dialog_et_email);
        final TextView tv_submit = view.findViewById(R.id.dialog_tv_submit);
        final TextView tv_cancel = view.findViewById(R.id.dialog_tv_cancel);
        final ProgressBar pb = view.findViewById(R.id.dialog_pb);

        tv_submit.setOnClickListener(v1 -> {
            String reset_email = Objects.requireNonNull(et_resetEmail.getText()).toString();
            if (TextUtils.isEmpty(reset_email)) {
                Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (SaveState.checkConnection(requireContext())) {
                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            } else {

                pb.setVisibility(View.VISIBLE);
                tv_submit.setVisibility(View.INVISIBLE);
                tv_cancel.setVisibility(View.INVISIBLE);

                mAuth.fetchSignInMethodsForEmail(reset_email).addOnSuccessListener(task -> mAuth.sendPasswordResetEmail(reset_email)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Reset link sent to your email", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                            tv_submit.setVisibility(View.VISIBLE);
                            tv_cancel.setVisibility(View.VISIBLE);
                        })).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    tv_submit.setVisibility(View.VISIBLE);
                    tv_cancel.setVisibility(View.VISIBLE);
                });
            }
        });
        tv_cancel.setOnClickListener(v12 -> alertDialog.dismiss());
    }

    public void setupLogin() {
        email = Objects.requireNonNull(binding.loginEtEmail.getText()).toString().trim();
        password = Objects.requireNonNull(binding.loginEtPassword.getText()).toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.itlEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.itlPassword.setError("Password is required");
            binding.itlEmail.setError(null);
            return;
        }

        if (!SaveState.checkConnection(requireContext())) {
            // no connection
            binding.itlEmail.setError(null);
            Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        MainActivity.displayLoadingDialog();
        mAuth.fetchSignInMethodsForEmail(email).addOnSuccessListener(task -> mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(task1 -> {
            if (SaveState.getShownOnBoarding())
                navController.navigate(R.id.action_loginFragment_to_mainFragment);
            else
                navController.navigate(R.id.action_loginFragment_to_onBoardFragment);
            MainActivity.hideKeyboardFrom(getContext(),binding.loginBtnLogin);
            MainActivity.progressDialog.dismiss();
        })
                .addOnFailureListener(e -> {
                    //email does not exist
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        binding.itlEmail.setError("Invalid email");
                        binding.itlPassword.setError(null);
                    }

                    //wrong password
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        binding.itlPassword.setError("Password is wrong");
                        binding.itlEmail.setError(null);
                    }
                    MainActivity.progressDialog.dismiss();
                })).addOnFailureListener(e -> {

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                binding.itlEmail.setError("Badly format");
                binding.itlPassword.setError(null);
            } else
                Toast.makeText(requireContext(), "Error! " + e.getCause(), Toast.LENGTH_SHORT).show();
            MainActivity.progressDialog.dismiss();
        });
    }
}