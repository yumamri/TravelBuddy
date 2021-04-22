package com.and.travelbuddy.ui.documents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.Document;
import com.and.travelbuddy.R;

import java.util.ArrayList;

public class DocumentsFragment extends Fragment {
    RecyclerView recyclerViewAgent;
    DocumentAdapter documentAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentsViewModel documentsViewModel = new ViewModelProvider(this).get(DocumentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_documents, container, false);
        final TextView textView = root.findViewById(R.id.text_document_title);
        documentsViewModel.text().observe(getViewLifecycleOwner(), textView::setText);
        return root;

//
//        recyclerViewAgent = root.findViewById(R.id.recycler_view_document);
//        recyclerViewAgent.hasFixedSize();
//        recyclerViewAgent.setLayoutManager(new LinearLayoutManager(getActivity()));

//        ArrayList<Document> documentArrayList = new ArrayList<>();
//        documentAdapter = new DocumentAdapter(documentArrayList, this);
//        recyclerViewAgent.setAdapter(documentAdapter);
    }

//    @Override
//    public void onListItemClick(int index) {
//        int documentIndex = index;
//        Document document = documentAdapter.getDocumentArrayList().get(documentIndex);
//        Toast.makeText(getContext(), document.getTitle(), Toast.LENGTH_SHORT).show();
//    }
}