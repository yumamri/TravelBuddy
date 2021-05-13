package com.and.travelbuddy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.ui.trip.TripActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerViewTrip;
    TripAdapter tripAdapter;
    ArrayList<Trip> tripArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = root.findViewById(R.id.home_fragment_fab_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripArrayList.add(new Trip());
                tripAdapter.notifyDataSetChanged();
            }
        });

        recyclerViewTrip = root.findViewById(R.id.home_fragment_recycler_view_trip);
        recyclerViewTrip.hasFixedSize();
        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripAdapter = new TripAdapter(tripArrayList, this::onListItemClick);
        recyclerViewTrip.setAdapter(tripAdapter);

        return root;
    }

    public void onListItemClick(int index) {
        Trip trip = tripAdapter.getTripArrayList().get(index);
        startTripActivity(trip);
    }

    private void startTripActivity(Trip trip) {
        Intent intent = new Intent(getActivity(), TripActivity.class);
//        intent.putExtra("TRIP_KEY", (Parcelable) trip);
        startActivity(intent);
    }
}