package com.and.travelbuddy.ui.document;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Document;
import com.and.travelbuddy.data.Tag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DocumentFragment extends Fragment implements DocumentDialogFragment.DialogListener {
    RecyclerView recyclerViewDocument;
    DocumentAdapter documentAdapter;
    ArrayList<Document> documentArrayList = new ArrayList<>();

    private FloatingActionButton btnChooseFile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentViewModel documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document, container, false);

        recyclerViewDocument = root.findViewById(R.id.recycler_view_document);
        recyclerViewDocument.hasFixedSize();
        recyclerViewDocument.setLayoutManager(new LinearLayoutManager(getActivity()));

        documentAdapter = new DocumentAdapter(documentArrayList, this::onListItemClick);
        recyclerViewDocument.setAdapter(documentAdapter);

        btnChooseFile = root.findViewById(R.id.fab_document_plus);
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentDialogFragment documentDialogFragment = new DocumentDialogFragment();
                documentDialogFragment.show(getChildFragmentManager(), "Add Document");
            }
        });
        return root;
    }

    public void onListItemClick(int index) {
        int documentIndex = index;
        Document document = documentAdapter.getDocumentArrayList().get(documentIndex);
        Toast.makeText(getActivity(), document.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String fileName, Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            documentArrayList.add(new Document(fileName, bitmap, Tag.IDENTITY));
            documentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof DocumentDialogFragment) {
            ((DocumentDialogFragment) fragment).listener = this;
        }
    }
}