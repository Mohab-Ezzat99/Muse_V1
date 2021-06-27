package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.network.ApiService;
import com.example.muse.network.RetrofitBuilder;
import com.example.muse.utility.SaveState;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Calendar;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private ChipNavigationBar chipNavigationBar;
    private NavController navControllerChart;
    private ImageView iv_custom;
    private int day, month, year;
    private ConstraintLayout constLayout_expand;
    private CardView cv_insight;
    private ImageView iv_arrow;
    private Spinner spinner;
    private TextView tv_current,tv_average,tv_per,tv_estimation;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navControllerChart = Navigation.findNavController(requireActivity(), R.id.FHome_fragment);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //StatusBar color
        if (SaveState.getDarkModeState())
            StartActivity.setupBackgroundStatusBar(getResources().getColor(R.color.nice_black, null));
        else
            StartActivity.setupLightStatusBar(getResources().getColor(R.color.white_muse, null));

        //iv_custom
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        iv_custom = view.findViewById(R.id.FHome_iv_custom);
        iv_custom.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext()
                    , android.R.style.Theme_Holo_Light_Dialog_MinWidth, (view1, year, month, dayOfMonth) -> Toast.makeText(getContext(), dayOfMonth + "/" + (++month) + "/" + year, Toast.LENGTH_SHORT).show(), year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
//            datePickerDialog.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackground(null);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackground(null);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackground(null);
        });

        //chipNav
        chipNavigationBar = view.findViewById(R.id.FHome_chipNav);
        chipNavigationBar.setItemSelected(R.id.dayFragment, true);
        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i) {
                case R.id.dayFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartDayFragment);
                    return;

                case R.id.weekFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartWeekFragment);
                    return;

                case R.id.monthFragment :
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartMonthFragment);
                    return;

                case R.id.yearFragment:
                    navControllerChart.popBackStack();
                    navControllerChart.navigate(R.id.chartYearFragment);
            }
        });

        spinner=view.findViewById(R.id.home_spinner_unit);
        tv_current=view.findViewById(R.id.home_tv_currentV);
        tv_average=view.findViewById(R.id.home_tv_averageV);
        tv_per=view.findViewById(R.id.home_tv_perMV);
        tv_estimation=view.findViewById(R.id.home_tv_estV);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    tv_current.setText("20 W");
                    tv_average.setText("200 W");
                    tv_per.setText("30 KW");
                    tv_estimation.setText("20 KW");
                }
                else
                {
                    tv_current.setText("12 EGP");
                    tv_average.setText("120 EGP");
                    tv_per.setText("390 EGP");
                    tv_estimation.setText("260 EGP");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        constLayout_expand=view.findViewById(R.id.constLayoutExpanded);
        iv_arrow=view.findViewById(R.id.home_iv_arrow);
        cv_insight=view.findViewById(R.id.home_cv_insight);

        iv_arrow.setOnClickListener(v -> {
            if(constLayout_expand.getVisibility()== View.GONE){
                TransitionManager.beginDelayedTransition(cv_insight, new AutoTransition());
                constLayout_expand.setVisibility(View.VISIBLE);
                iv_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }
            else
            {
                TransitionManager.beginDelayedTransition(cv_insight, new Fade());
                constLayout_expand.setVisibility(View.GONE);
                iv_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
            }
        });
    }
}