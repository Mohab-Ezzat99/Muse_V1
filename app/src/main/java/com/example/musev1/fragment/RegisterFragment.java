package com.example.musev1.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.musev1.utility.SaveState;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.musev1.MainActivity.mAuth;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private String full_name, email, password, confirm_password, user_id;
    private ProgressDialog progressDialog;
    private NavController navController;
    private FragmentRegisterBinding binding;

    private Map<String, Object> map;
    private FirebaseFirestore db;
    private DocumentReference documentReference;


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
        binding = FragmentRegisterBinding.bind(view);
        progressDialog = new ProgressDialog(getContext());
        db = FirebaseFirestore.getInstance();
        map = new HashMap<>();


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

        if (!SaveState.checkConnection(requireContext())) {
            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        MainActivity.displayLoadingDialog();
        mAuth.fetchSignInMethodsForEmail(email).addOnSuccessListener(signInMethodQueryResult -> mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                documentReference = db.collection(SaveState.USERS).document(user_id);
                map.put(SaveState.USER_ID, user_id);
                map.put(SaveState.FULL_NAME, full_name);
                map.put(SaveState.EMAIL, email);
                documentReference.set(map).addOnCompleteListener(task -> {
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                    Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                });
            } else {
                if (task1.getException() instanceof FirebaseAuthUserCollisionException) {
                    binding.registerItlEmail.setError("Already exist");
                    binding.registerItlPassword.setError(null);
                } else if (task1.getException() instanceof FirebaseAuthWeakPasswordException) {
                    binding.registerItlPassword.setError("Weak password");
                    binding.registerItlEmail.setError(null);
                } else
                    Toast.makeText(getContext(), "Error! " + task1.getException(), Toast.LENGTH_LONG).show();
            }
            MainActivity.progressDialog.dismiss();
        })).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthInvalidCredentialsException)
                binding.registerItlEmail.setError("Badly format");
            else
                Toast.makeText(getContext(), "Error! " + e.getCause(), Toast.LENGTH_SHORT).show();
            MainActivity.progressDialog.dismiss();
        });
    }
}