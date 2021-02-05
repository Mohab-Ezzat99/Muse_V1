package com.example.muse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.muse.adapters.VPOnBoardAdapter;
import com.example.muse.fragment.OnFirstFragment;
import com.example.muse.fragment.OnFourthFragment;
import com.example.muse.fragment.OnSecondFragment;
import com.example.muse.fragment.OnThirdFragment;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        ViewPager viewPager=findViewById(R.id.onBoard_vp);
        ArrayList<Fragment> screens=new ArrayList<>();
        screens.add(new OnFirstFragment());
        screens.add(new OnSecondFragment());
        screens.add(new OnThirdFragment());
        screens.add(new OnFourthFragment());
        VPOnBoardAdapter adapter=new VPOnBoardAdapter(getSupportFragmentManager(),0);
        adapter.setScreens(screens);
        viewPager.setAdapter(adapter);
    }
}