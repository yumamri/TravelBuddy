package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.data.Checklist;
import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.databinding.FragmentTripChecklistBinding;
import com.and.travelbuddy.databinding.FragmentTripDocumentBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TripDocumentFragment extends Fragment {

    private static final String TAG = "TRIP_CHECKLIST_DATABASE";
    private Trip tripFrag;
    private static final String key = "TRIP_CHECKLIST_KEY";

    private FragmentTripDocumentBinding binding;
    ChildEventListener childEventListener;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trips");

    RecyclerView recyclerView;
    TripChecklistAdapter tripChecklistAdapter;
    ArrayList<Checklist> checklists = new ArrayList<>();
    FloatingActionButton floatingActionButton;

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
            Toast.makeText(getActivity(), tripFrag.getKey(), Toast.LENGTH_SHORT).show();
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
