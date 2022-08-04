package com.ngadt.demo4.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ngadt.demo4.databinding.FragmentDemgioBinding;

public class DemgioFragments extends Fragment {
    FragmentDemgioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDemgioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
