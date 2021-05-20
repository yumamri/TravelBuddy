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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.data.Checklist;
import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.databinding.FragmentTripChecklistBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TripChecklistFragment extends Fragment implements TripChecklistDialogFragment.ChecklistDialogListener {

    private static final String TAG = "TRIP_CHECKLIST_DATABASE";
    private Trip tripFrag;
    private static final String key = "TRIP_CHECKLIST_KEY";

    private FragmentTripChecklistBinding binding;
    ChildEventListener childEventListener;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trips");

    RecyclerView recyclerView;
    TripChecklistAdapter tripChecklistAdapter;
    ArrayList<Checklist> checklists = new ArrayList<>();
    FloatingActionButton floatingActionButton;

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
            Toast.makeText(getActivity(), tripFrag.getKey(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
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

        tripChecklistAdapter = new TripChecklistAdapter(checklists, this::onListItemClick);
        recyclerView.setAdapter(tripChecklistAdapter);

        floatingActionButton.setOnClickListener(v -> {
            TripChecklistDialogFragment tripChecklistDialogFragment = new TripChecklistDialogFragment();
            tripChecklistDialogFragment.show(getChildFragmentManager(), "Add an item to the checklist");
        });

/*        *//** Swipe to delete *//*
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Checklist checklist = checklists.get(viewHolder.getAdapterPosition());
                String key = checklist.getKey();
                checklists.remove(checklist);
                databaseReference.child(key).removeValue();
            }
        }).attachToRecyclerView(recyclerView);*/

        /*childEventListener = databaseReference.child(tripFrag.getKey()).child("Checklist").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + snapshot.getKey());
                Checklist checklist = snapshot.getValue(Checklist.class);
                checklist.setKey(snapshot.getKey());
                checklists.add(checklist);
                tripChecklistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged:" + snapshot.getKey());
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.getKey());
                checklists.remove(snapshot.getKey());
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
        });*/
        return root;
    }

    public void onListItemClick(int index) {
        Checklist checklist = tripChecklistAdapter.getChecklistArrayList().get(index);
        Toast.makeText(getActivity(), checklist.getItem(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        //databaseReference.removeEventListener(childEventListener);
    }

    @Override
    public void onDialogChecklistPositiveClick(DialogFragment dialog, String string) {
        Checklist checklist = new Checklist(string, false);
        tripFrag.getChecklist().add(checklist);
        checklists.add(checklist);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof TripChecklistDialogFragment) {
            ((TripChecklistDialogFragment) fragment).checklistDialogListener = this;
        }
    }
}