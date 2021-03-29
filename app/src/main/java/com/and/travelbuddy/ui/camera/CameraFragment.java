package com.and.travelbuddy.ui.camera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;

public class CameraFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CameraViewModel cameraViewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        final TextView textView = root.findViewById(R.id.text_camera);
        cameraViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}