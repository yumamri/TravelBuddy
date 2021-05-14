package com.and.travelbuddy.ui.document;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DocumentFragment extends Fragment implements DocumentDialogFragment.DialogListener {
    private static final String TAG = "DOCUMENT_DATABASE";
    RecyclerView recyclerViewDocument;
    DocumentAdapter documentAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Documents");
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://travel-buddy-uwu.appspot.com");
    StorageReference storageReference = firebaseStorage.getReference().child("Photos");

    ArrayList<Document> documentArrayList = new ArrayList<>();

    private FloatingActionButton btnChooseFile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentViewModel documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document, container, false);

        recyclerViewDocument = root.findViewById(R.id.document_fragment_recycler_view);
        recyclerViewDocument.hasFixedSize();
        recyclerViewDocument.setLayoutManager(new LinearLayoutManager(getActivity()));

        documentAdapter = new DocumentAdapter(documentArrayList, this::onListItemClick);
        recyclerViewDocument.setAdapter(documentAdapter);

        /** Dialog */
        btnChooseFile = root.findViewById(R.id.document_fragment_fab_plus);
        btnChooseFile.setOnClickListener(view -> {
            DocumentDialogFragment documentDialogFragment = new DocumentDialogFragment();
            documentDialogFragment.show(getChildFragmentManager(), "Add Document");
        });

        /** Document list */
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new document has been added, add it to the displayed list
                Document document = dataSnapshot.getValue(Document.class);
                documentArrayList.add(document);
                documentAdapter.notifyDataSetChanged();
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A document has changed, use the key to determine if we are displaying this
                // document and if so displayed the changed document.
                Document newDocument = dataSnapshot.getValue(Document.class);
                String documentKey = dataSnapshot.getKey();
                documentAdapter.notifyDataSetChanged();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A document has changed, use the key to determine if we are displaying this
                // document and if so remove it.
                String documentKey = dataSnapshot.getKey();
                documentArrayList.remove(documentKey);
                documentAdapter.notifyDataSetChanged();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A document has changed position, use the key to determine if we are
                // displaying this document and if so move it.
                Document movedDocument = dataSnapshot.getValue(Document.class);
                String documentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postDocuments:onCancelled", databaseError.toException());
                Toast.makeText(getActivity(), "Failed to load documents.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    public void onListItemClick(int index) {
        int documentIndex = index;
        Document document = documentAdapter.getDocumentArrayList().get(documentIndex);
        Toast.makeText(getActivity(), document.getTitle(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Dialog info recieved
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String fileName, Uri uri) {
        StorageReference storageReferenceUpload = storageReference.child(fileName);
        UploadTask uploadTask = storageReferenceUpload.putFile(uri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            if (taskSnapshot.getMetadata() != null) {
                if (taskSnapshot.getMetadata().getReference() != null) {
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(uriSuccess -> {
                        String imageUrl = uriSuccess.toString();
                        Toast.makeText(getActivity(), fileName + " succeeded", Toast.LENGTH_SHORT).show();
                        Document document = new Document(fileName, imageUrl);
                        databaseReference.push().setValue(document);
                    });
                }
            }
        });

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof DocumentDialogFragment) {
            ((DocumentDialogFragment) fragment).listener = this;
        }
    }
}