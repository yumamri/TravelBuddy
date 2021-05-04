package com.and.travelbuddy.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;
import com.and.travelbuddy.ui.main.MainActivity;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        Button buttonSignOut = root.findViewById(R.id.button_sign_out);
        buttonSignOut.setOnClickListener(v -> ((MainActivity)getActivity()).signOut());
        return root;
    }
}