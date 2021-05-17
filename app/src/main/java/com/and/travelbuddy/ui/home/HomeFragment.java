package com.and.travelbuddy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "TRIP_DATABASE";
    RecyclerView recyclerViewTrip;
    TripAdapter tripAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trips");
    ArrayList<Trip> tripArrayList = new ArrayList<>();
    ArrayList<String> keysArrayList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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

        /** Trip list */
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                Trip trip = dataSnapshot.getValue(Trip.class);
                trip.setKey(key);
                tripArrayList.add(trip);
                tripAdapter.notifyDataSetChanged();
                keysArrayList.add(key);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                Trip newTrip = dataSnapshot.getValue(Trip.class);
                String key = dataSnapshot.getKey();
                int index = keysArrayList.indexOf(key);
                tripArrayList.set(index, newTrip);
                tripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                tripArrayList.remove(dataSnapshot.getKey());
                tripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postTrips:onCancelled", databaseError.toException());
                Snackbar.make(getView(), R.string.failed_trips, Snackbar.LENGTH_SHORT)
                        .show();

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
}