package com.and.travelbuddy.ui.document;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Document;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DocumentFragment extends Fragment implements DocumentDialogFragment.DialogListener {
    private static final String TAG = "DOCUMENT_DATABASE";
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Documents");
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://travel-buddy-uwu.appspot.com");
    StorageReference storageReference = firebaseStorage.getReference().child("Photos");

    ChildEventListener childEventListener;

    RecyclerView recyclerViewDocument;
    DocumentAdapter documentAdapter;
    ArrayList<Document> documentArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocumentViewModel documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document, container, false);

        recyclerViewDocument = root.findViewById(R.id.document_fragment_recycler_view);
        recyclerViewDocument.hasFixedSize();
        recyclerViewDocument.setLayoutManager(new LinearLayoutManager(getActivity()));

        documentAdapter = new DocumentAdapter(documentArrayList, this::onListItemClick);
        recyclerViewDocument.setAdapter(documentAdapter);

        /** Swipe to delete */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Document swipedDocument = documentArrayList.get(viewHolder.getAdapterPosition());
                String key = swipedDocument.getKey();
                documentArrayList.remove(swipedDocument);
                databaseReference.child(key).removeValue();
            }
        }).attachToRecyclerView(recyclerViewDocument);

        /** Dialog */
        FloatingActionButton btnChooseFile = root.findViewById(R.id.document_fragment_fab_plus);
        btnChooseFile.setOnClickListener(view -> {
            DocumentDialogFragment documentDialogFragment = new DocumentDialogFragment();
            documentDialogFragment.show(getChildFragmentManager(), "Add Document");
        });

        /** Document list */
        childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                // A new document has been added, add it to the displayed list
                Document document = dataSnapshot.getValue(Document.class);
                document.setKey(dataSnapshot.getKey());
                documentArrayList.add(document);
                documentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                documentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                // A document has changed, use the key to determine if we are displaying this
                // document and if so remove it.
                documentArrayList.remove(dataSnapshot.getKey());
                documentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postDocuments:onCancelled", databaseError.toException());
                Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.failed_documents, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        return root;
    }

    public void onListItemClick(int index) {
        Document document = documentAdapter.getDocumentArrayList().get(index);
        Toast.makeText(getActivity(), document.getTitle(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Dialog info recieved
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String fileName, Uri uri, String category) {
        StorageReference storageReferenceUpload = storageReference.child(fileName);
        UploadTask uploadTask = storageReferenceUpload.putFile(uri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            if (taskSnapshot.getMetadata() != null) {
                if (taskSnapshot.getMetadata().getReference() != null) {
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(uriSuccess -> {
                        String imageUrl = uriSuccess.toString();
                        Snackbar.make(getView(), fileName + " uploaded successfully", Snackbar.LENGTH_SHORT)
                                .show();
                        Document document = new Document(fileName, imageUrl, category);
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

    @Override
    public void onPause() {
        databaseReference.removeEventListener(childEventListener);
        super.onPause();
    }
}