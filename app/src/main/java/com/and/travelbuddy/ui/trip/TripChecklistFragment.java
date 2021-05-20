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

import com.and.travelbuddy.data.Checklist;
import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.databinding.FragmentTripChecklistBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class TripChecklistFragment extends Fragment implements TripChecklistDialogFragment.ChecklistDialogListener, TripChecklistAdapter.ChecklistListener {

    private static final String TAG = "TRIP_CHECKLIST_DATABASE";
    private Trip tripFrag;
    private static final String key = "TRIP_CHECKLIST_KEY";

    private FragmentTripChecklistBinding binding;
    private ChildEventListener childEventListener;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trips");

    private RecyclerView recyclerView;
    private TripChecklistAdapter tripChecklistAdapter;
    private ArrayList<String> keys = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private String tripKey;
    private ValueEventListener valueEventListener;

    public static TripChecklistFragment newInstance(Trip trip) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, trip);
        TripChecklistFragment tripChecklistFragment = new TripChecklistFragment();
        tripChecklistFragment.setArguments(bundle);
        return tripChecklistFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripFrag = new Trip();
        if (getArguments() != null) {
            tripFrag = (Trip) getArguments().getSerializable(key);
            tripKey = tripFrag.getKey();
        } else {
            Toast.makeText(getActivity(), "Trip error", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentTripChecklistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.tripChecklistFragmentRecyclerView;
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        floatingActionButton = binding.tripChecklistFragmentFabPlus;

        tripChecklistAdapter = new TripChecklistAdapter(tripFrag.getChecklist(), this::handleCheckChanged);
        recyclerView.setAdapter(tripChecklistAdapter);

        for (Checklist checklist : tripFrag.getChecklist()) {
            keys.add(checklist.getKey());
        }

        floatingActionButton.setOnClickListener(v -> {
            TripChecklistDialogFragment tripChecklistDialogFragment = new TripChecklistDialogFragment();
            tripChecklistDialogFragment.show(getChildFragmentManager(), "Add an item to the checklist");
        });


        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Checklist checklist = tripFrag.getChecklist().get(viewHolder.getAdapterPosition());
                String key = checklist.getKey();
                tripFrag.getChecklist().remove(checklist);
                databaseReference.child(tripKey).child("Checklists").child(key).removeValue();
            }
        }).attachToRecyclerView(recyclerView);

        childEventListener = databaseReference.child(tripKey).child("Checklists").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + snapshot.getKey());
                String key = snapshot.getKey();
                Checklist checklist = snapshot.getValue(Checklist.class);
                checklist.setKey(key);
                tripFrag.getChecklist().add(checklist);
                keys.add(key);
                tripChecklistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged:" + snapshot.getKey());
                Checklist checklist = snapshot.getValue(Checklist.class);
                int index = keys.indexOf(snapshot.getKey());
                tripFrag.getChecklist().set(index, checklist);
                tripChecklistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.getKey());
                tripChecklistAdapter.notifyDataSetChanged();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        databaseReference.child(tripKey).child("Checklists").removeEventListener(childEventListener);
    }

    @Override
    public void onDialogChecklistPositiveClick(DialogFragment dialog, String string) {
        Checklist checklist = new Checklist(string, Boolean.FALSE);
        databaseReference.child(tripKey).child("Checklists").push().setValue(checklist);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof TripChecklistDialogFragment) {
            ((TripChecklistDialogFragment) fragment).checklistDialogListener = this;
        }
    }

    @Override
    public void handleCheckChanged(Checklist checklist, Boolean isChecked) {
        Toast.makeText(getActivity(), isChecked.toString(), Toast.LENGTH_SHORT).show();
        valueEventListener = databaseReference.child(tripKey).child("Checklists").child(checklist.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange:" + dataSnapshot.getKey());
                Map<String, Object> postValues = new HashMap<String, Object>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    postValues.put(snapshot.getKey(), snapshot.getValue());
                }
                postValues.put("aBoolean", isChecked);
                databaseReference.child(tripKey).child("Checklists").child(checklist.getKey()).updateChildren(postValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled:" + databaseError);
            }
        });
    }

}