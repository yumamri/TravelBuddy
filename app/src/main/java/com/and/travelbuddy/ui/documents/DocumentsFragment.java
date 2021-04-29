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
    RecyclerView recyclerViewDocument;
    DocumentAdapter documentAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentsViewModel documentsViewModel = new ViewModelProvider(this).get(DocumentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        recyclerViewDocument = root.findViewById(R.id.recycler_view_document);
        recyclerViewDocument.hasFixedSize();
        recyclerViewDocument.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Document> documentArrayList = new ArrayList<>();
        documentAdapter = new DocumentAdapter(documentArrayList, this::onListItemClick);
        recyclerViewDocument.setAdapter(documentAdapter);

        return root;
    }

    public void onListItemClick(int index) {
        int documentIndex = index;
        Document document = documentAdapter.getDocumentArrayList().get(documentIndex);
        Toast.makeText(getActivity(), document.getTitle(), Toast.LENGTH_SHORT).show();
    }
}