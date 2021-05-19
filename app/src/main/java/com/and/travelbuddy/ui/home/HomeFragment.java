package com.and.travelbuddy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.ui.trip.TripActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "TRIP_DATABASE";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> keysArrayList = new ArrayList<>();

    ChildEventListener childEventListener;

    RecyclerView recyclerViewTrip;
    TripAdapter tripAdapter;
    ArrayList<Trip> tripArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().child("Trips");

        FloatingActionButton fab = root.findViewById(R.id.home_fragment_fab_plus);
        fab.setOnClickListener(view -> {
            Trip trip = new Trip(getString(R.string.edit_me), getString(R.string.edit_me), "https://images.pexels.com/photos/358319/pexels-photo-358319.jpeg");
            databaseReference.push().setValue(trip);
            tripAdapter.notifyDataSetChanged();
        });

        recyclerViewTrip = root.findViewById(R.id.home_fragment_recycler_view_trip);
        recyclerViewTrip.hasFixedSize();
        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(getActivity()));

        tripAdapter = new TripAdapter(tripArrayList, this::onListItemClick);
        recyclerViewTrip.setAdapter(tripAdapter);

        /** Swipe to delete */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Trip swipedTrip = tripArrayList.get(viewHolder.getAdapterPosition());
                String key = swipedTrip.getKey();
                tripArrayList.remove(swipedTrip);
                databaseReference.child(key).removeValue();
            }
        }).attachToRecyclerView(recyclerViewTrip);

/** Trip list */
        childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + snapshot.getKey());
                String key = snapshot.getKey();
                Trip trip = snapshot.getValue(Trip.class);
                trip.setKey(key);
                tripArrayList.add(trip);
                keysArrayList.add(key);
                tripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged:" + snapshot.getKey());
                Trip newTrip = snapshot.getValue(Trip.class);
                int index = keysArrayList.indexOf(snapshot.getKey());
                tripArrayList.set(index, newTrip);
                tripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.getKey());
                tripArrayList.remove(snapshot.getKey());
                tripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.d(TAG, "onChildMoved:" + snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "postTrips:onCancelled", error.toException());
            }
        });
        return root;
    }

    public void onListItemClick(int index) {
        Trip trip = tripAdapter.getTripArrayList().get(index);
        startTripActivity(trip);
    }

    private void startTripActivity(Trip trip) {
        Intent intent = new Intent(getActivity(), TripActivity.class);
        intent.putExtra("TRIP_KEY", trip);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        databaseReference.child("Trips").removeEventListener(childEventListener);

    }


}