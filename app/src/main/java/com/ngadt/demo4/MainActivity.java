package com.ngadt.demo4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ngadt.demo4.adapter.MusicPageAdapter;
import com.ngadt.demo4.databinding.ActivityMainBinding;
import com.ngadt.demo4.fragments.BamGioFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.vpMusic.setAdapter(new MusicPageAdapter(this));
        //  ArrayList<String> tabTitles = new ArrayList<>();
        String tabTiles[] = {"Báo thức", "Bấm giờ", "Đếm giờ"};
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabMain, binding.vpMusic, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                // position of the current tab and that tab
                tab.setText(tabTiles[position]);
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.ic__alaram);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_baseline_timer_24);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_baseline_access_time_24);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}