package com.ngadt.demo4.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ngadt.demo4.databinding.FragmentBamgioBinding;

public class BamGioFragment extends Fragment {
    FragmentBamgioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBamgioBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
