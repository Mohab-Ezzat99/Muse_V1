package com.example.musev1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.musev1.R;
import com.example.musev1.utility.SaveState;

public class OnFourthFragment extends Fragment {
    public OnFourthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_fourth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.onFourth_tv_finish).setOnClickListener(v -> {
            SaveState.setShownOnBoarding(true);
            Navigation.findNavController(requireActivity(),R.id.start_fragment).navigate(R.id.action_onBoardFragment_to_mainFragment);
        });
    }
}