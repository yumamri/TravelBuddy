package com.and.travelbuddy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;
import com.and.travelbuddy.ui.main.MainActivity;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final Button signOut = root.findViewById(R.id.button_sign_out);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        signOut.setOnClickListener(v -> ((MainActivity)getActivity()).signOut());
        return root;
    }
}