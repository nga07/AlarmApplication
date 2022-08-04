package com.ngadt.demo4.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ngadt.demo4.fragments.BamGioFragment;
import com.ngadt.demo4.fragments.BaothucFragments;
import com.ngadt.demo4.fragments.DemgioFragments;


public class MusicPageAdapter extends FragmentStateAdapter {

    public MusicPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new BaothucFragments();
        } else if (position == 1) {
            return new BamGioFragment();
        } else if (position == 2) {
            return new DemgioFragments();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
