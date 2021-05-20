package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.data.Document;
import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.databinding.FragmentTripDocumentBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TripDocumentFragment extends Fragment implements TripDocumentDialogFragment.TripDocumentListener {

    private static final String TAG = "TRIP_DOCUMENT_DATABASE";
    private Trip tripFrag;
    private static final String key = "TRIP_DOCUMENT_KEY";

    private FragmentTripDocumentBinding binding;
    private ChildEventListener childEventListener;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trips");
    private DatabaseReference databaseReferenceDialog = firebaseDatabase.getReference().child("Documents");

    private RecyclerView recyclerView;
    private TripDocumentAdapter tripDocumentAdapter;
    private ArrayList<Document> documents = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private String tripKey;
    private ArrayList<Document> documentsDialog = new ArrayList<>();
    private ChildEventListener childEventListenerDialog;

    public static Fragment newInstance(Trip trip) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, trip);
        TripDocumentFragment tripDocumentFragment = new TripDocumentFragment();
        tripDocumentFragment.setArguments(bundle);
        return tripDocumentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripFrag = new Trip();
        if (getArguments() != null) {
            tripFrag = (Trip) getArguments().getSerializable(key);
        } else {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentTripDocumentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.tripDocumentFragmentRecyclerView;
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        floatingActionButton = binding.tripDocumentFragmentFabPlus;

        tripDocumentAdapter = new TripDocumentAdapter(tripFrag.getDocuments(), this::onListItemClick);
        recyclerView.setAdapter(tripDocumentAdapter);

        tripKey = tripFrag.getKey();

        childEventListenerDialog = databaseReferenceDialog.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Document document = snapshot.getValue(Document.class);
                documentsDialog.add(document);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        floatingActionButton.setOnClickListener(v -> {
            TripDocumentDialogFragment tripDocumentDialogFragment = TripDocumentDialogFragment.newInstance(documentsDialog);
            tripDocumentDialogFragment.show(getChildFragmentManager(), "Add a document to the list");
        });


        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Document document = tripFrag.getDocuments().get(viewHolder.getAdapterPosition());
                String key = document.getKey();
                tripFrag.getDocuments().remove(document);
                databaseReference.child(tripKey).child("Documents").child(key).removeValue();
            }
        }).attachToRecyclerView(recyclerView);

        childEventListener = databaseReference.child(tripKey).child("Documents").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + snapshot.getKey());
                String key = snapshot.getKey();
                Document document = snapshot.getValue(Document.class);
                document.setKey(key);
                tripFrag.getDocuments().add(document);
                tripDocumentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged:" + snapshot.getKey());
                tripDocumentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.getKey());
                tripDocumentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildMoved:" + snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "postDocuments:onCancelled", error.toException());
            }
        });
        return root;
    }
    public void onListItemClick(int index) {
        Document document = tripDocumentAdapter.getDocumentArrayList().get(index);
        Toast.makeText(getActivity(), document.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        databaseReference.child(tripKey).child("Documents").removeEventListener(childEventListener);
    }

    @Override
    public void onDialogTripDocumentPositiveClick(DialogFragment dialog, Document document) {
        documents.add(document);
        databaseReference.child(tripKey).child("Documents").push().setValue(document);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof TripDocumentDialogFragment) {
            ((TripDocumentDialogFragment) fragment).tripDocumentListener = this;
        }
    }
}
