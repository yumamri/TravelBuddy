package com.and.travelbuddy.ui.document;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.data.Document;
import com.and.travelbuddy.R;

import java.util.ArrayList;

public class DocumentFragment extends Fragment {
    RecyclerView recyclerViewDocument;
    DocumentAdapter documentAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentViewModel documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document, container, false);

        recyclerViewDocument = root.findViewById(R.id.recycler_view_document);
        recyclerViewDocument.hasFixedSize();
        recyclerViewDocument.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Document> documentArrayList = new ArrayList<>();
        documentArrayList.add(new Document("Doc1"));
        documentArrayList.add(new Document("Doc2"));
        documentArrayList.add(new Document("Doc3"));
        documentArrayList.add(new Document("Doc4"));
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