package com.and.travelbuddy.ui.document;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Document;
import com.and.travelbuddy.data.Tag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DocumentFragment extends Fragment {
    RecyclerView recyclerViewDocument;
    DocumentAdapter documentAdapter;
    ArrayList<Document> documentArrayList;

    private FloatingActionButton btnChooseFile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentViewModel documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document, container, false);

        recyclerViewDocument = root.findViewById(R.id.recycler_view_document);
        recyclerViewDocument.hasFixedSize();
        recyclerViewDocument.setLayoutManager(new LinearLayoutManager(getActivity()));

        documentArrayList = new ArrayList<>();
        documentAdapter = new DocumentAdapter(documentArrayList, this::onListItemClick);
        recyclerViewDocument.setAdapter(documentAdapter);

        btnChooseFile = root.findViewById(R.id.fab_document_plus);
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentDialogFragment documentDialogFragment = new DocumentDialogFragment();
                documentDialogFragment.show(getFragmentManager(), "Add Document");
            }
        });
        return root;
    }

    public void onListItemClick(int index) {
        int documentIndex = index;
        Document document = documentAdapter.getDocumentArrayList().get(documentIndex);
        Toast.makeText(getActivity(), document.getTitle(), Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void sendInput(String input) {
//        Bitmap bitmap = BitmapFactory.decodeFile(input);
//        documentArrayList.add(new Document(input, bitmap, Tag.IDENTITY));
//    }
}