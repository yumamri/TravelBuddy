package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.databinding.FragmentTripBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class TripFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private TripPageViewModel tripPageViewModel;
    private FragmentTripBinding binding;

    public static TripFragment newInstance(int index) {
        TripFragment fragment = new TripFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripPageViewModel = new ViewModelProvider(this).get(TripPageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        tripPageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentTripBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.tripFragmentRecyclerView;
        FloatingActionButton floatingActionButton = binding.tripFragmentFabPlus;


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}