package com.example.muse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muse.MainActivity;
import com.example.muse.R;
import com.example.muse.adapters.OnDeviceItemListener;
import com.example.muse.adapters.RVAlertAdapter;
import com.example.muse.model.AlertModel;
import com.example.muse.model.DeviceModel;
import com.example.muse.model.DeviceRequestModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Group not_add;
    private RVAlertAdapter adapter;
    private NavController navController;

    public AlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        navController= Navigation.findNavController(requireActivity(),R.id.main_fragment);
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        MainActivity.setupBackgroundStatusBar(MainActivity.colorPrimaryVariant);

        //init
        not_add = view.findViewById(R.id.FAlerts_group);
        recyclerView = view.findViewById(R.id.FAlerts_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //recycleView
        adapter = new RVAlertAdapter(getContext());
        recyclerView.setAdapter(adapter);
        setupSwipe();

        getAllAlertsReq();

        adapter.setListener(new OnDeviceItemListener() {
            @Override
            public void OnItemClick(DeviceRequestModel device) {
            }

            @Override
            public void OnItemClick(AlertModel alertModel) {
                navController.navigate(AlertsFragmentDirections
                        .actionAlertsFragmentToSelectedDeviceFragment(alertModel.getDeviceId()));
            }

            @Override
            public void OnBottomSheetItemClick(DeviceModel device, int position) {

            }

            @Override
            public void OnItemLongClick(View view, DeviceRequestModel device) {

            }
        });
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

                AlertModel alertModel = adapter.getItemAt(viewHolder.getAdapterPosition());
                MainActivity.displayLoadingDialog();
                MainActivity.museViewModel.deleteAlertById(alertModel.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                            getAllAlertsReq();
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

    public void getAllAlertsReq() {
        MainActivity.displayLoadingDialog();
        MainActivity.museViewModel.getAllAlertsRequest()
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
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            MainActivity.progressDialog.dismiss();
                        });
    }
}