package com.example.myruns3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ArrayList<Fragment> fragmentArrayList;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new StartFragment());
        fragmentArrayList.add(new HistoryFragment(this));
        fragmentArrayList.add(new SettingsFragment());

        pagerAdapter = new ImpFragmentPagerAdapter(getSupportFragmentManager(), fragmentArrayList);


        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }



}
