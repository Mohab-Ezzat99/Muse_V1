package com.example.muse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.MainActivity;
import com.example.muse.R;
import com.example.muse.adapters.RVAddGoalAdapter;
import com.example.muse.model.DeviceRequestModel;
import com.example.muse.model.GoalModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RVAddGoalAdapter adapter;
    private Group not_add;
    private String[] strings;
    private List<DeviceRequestModel> result_devices;

    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        return inflater.inflate(R.layout.fragment_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);
        not_add = view.findViewById(R.id.FGoals_group);

        //recycleView
        recyclerView = view.findViewById(R.id.FGoals_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RVAddGoalAdapter(getContext());
        recyclerView.setAdapter(adapter);
        setupSwipe();

        getAllDevicesReq(0, 0);
        getAllGoalsReq();

        // fab add
        FloatingActionButton fab_add = view.findViewById(R.id.FGoals_fab_add);
        fab_add.setOnClickListener(v -> {
            if (result_devices.size() == 0)
                Toast.makeText(getContext(), "No device found to set goal", Toast.LENGTH_LONG).show();
            else
                showBottomSheet(view);
        });
    }

    public void showBottomSheet(View view) {
        //init
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        View bottom_sheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_goal, view.findViewById(R.id.goalBotSheet));

        //catch spinner
        Spinner spinner_device = bottom_sheet.findViewById(R.id.goalBotSheet_spinner_devices);
        ArrayAdapter<String> stringsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
        stringsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_device.setAdapter(stringsAdapter);

        Spinner spinner_agg = bottom_sheet.findViewById(R.id.goalBotSheet_spinner_agg);
        Spinner spinner_values = bottom_sheet.findViewById(R.id.goalBotSheet_spinner_values);
        int usage = 0;

        switch (spinner_values.getSelectedItemPosition()) {
            case 0:
                usage = 100;
                break;

            case 1:
                usage = 200;
                break;

            case 2:
                usage = 300;
        }


        Button btn_submit = bottom_sheet.findViewById(R.id.goalBotSheet_btn_submit);
        int finalUsage = usage;
        btn_submit.setOnClickListener(v1 -> {
            // add item to rv
            DeviceRequestModel device = result_devices.get(spinner_device.getSelectedItemPosition());
            GoalModel goalModel = new GoalModel(device.getId(), spinner_agg.getSelectedItemPosition(), finalUsage, 0);
            MainActivity.museViewModel.addGoal(goalModel).enqueue(new Callback<GoalModel>() {
                @Override
                public void onResponse(@NotNull Call<GoalModel> call, @NotNull Response<GoalModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                        getAllGoalsReq();
                        bottomSheetDialog.dismiss();
                    } else Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NotNull Call<GoalModel> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            bottomSheetDialog.dismiss();
        });

        //launch bottom sheet
        bottomSheetDialog.setContentView(bottom_sheet);
        bottomSheetDialog.show();
    }

    private void setupSwipe()
    {
        ItemTouchHelper.SimpleCallback callback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                GoalModel goalModel = adapter.getItemAt(viewHolder.getAdapterPosition());
                MainActivity.displayLoadingDialog();
                MainActivity.museViewModel.deleteGoalById(goalModel.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                            getAllGoalsReq();
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }
                });
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void getAllDevicesReq(int aggregation, int unit) {
        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getAllDevicesRequest(aggregation, unit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            result_devices = result;
                            strings = new String[result.size()];
                            for (int i = 0; i < result.size(); i++)
                                strings[i] = result.get(i).getName();
                            MainActivity.progressDialog.dismiss();
                        },
                        error -> {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }

    public void getAllGoalsReq() {
        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getAllGoalsRequest()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            if (result.size() != 0) {
                                // visibility
                                not_add.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            } else {
                                // visibility
                                not_add.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                            adapter.submitList(result);
                            MainActivity.progressDialog.dismiss();
                        },
                        error -> {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }
}