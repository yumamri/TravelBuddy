package com.and.travelbuddy.ui.documents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;

public class DocumentsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentsViewModel documentsViewModel = new ViewModelProvider(this).get(DocumentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_documents, container, false);
        final TextView textView = root.findViewById(R.id.text_documents);
        documentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}