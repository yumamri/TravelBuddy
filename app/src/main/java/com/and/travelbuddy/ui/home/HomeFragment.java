package com.and.travelbuddy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = root.findViewById(R.id.fab_home_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTripActivity();
            }
        });

        recyclerViewTrip = root.findViewById(R.id.recycler_view_trip);
        recyclerViewTrip.hasFixedSize();
        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Trip> tripArrayList = new ArrayList<>();
        tripArrayList.add(new Trip("France"));
        tripArrayList.add(new Trip("Cambodia"));
        tripArrayList.add(new Trip("Japan"));
        tripArrayList.add(new Trip("Belgium"));
        tripAdapter = new TripAdapter(tripArrayList, this::onListItemClick);
        recyclerViewTrip.setAdapter(tripAdapter);

        return root;
    }

    public void onListItemClick(int index) {
        int tripIndex = index;
        Trip trip = tripAdapter.getTripArrayList().get(tripIndex);
        Toast.makeText(getActivity(), trip.getCity(), Toast.LENGTH_SHORT).show();
    }

    private void startTripActivity() {
        startActivity(new Intent(getActivity(), TripActivity.class));
    }
}