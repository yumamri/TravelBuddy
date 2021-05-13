package com.and.travelbuddy.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TripRepository {
    private static TripRepository instance;
    private DatabaseReference myRef;
    private TripLiveData trip;

    private TripRepository() {
    }

    public static synchronized TripRepository getInstance() {
        if (instance == null)
            instance = new TripRepository();
        return instance;
    }

    public void init(String userId) {
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        trip = new TripLiveData(myRef);
    }

    public void saveTrip(String trip) {
        //myRef.setValue(new Trip(trip));
    }

    public TripLiveData getTrip() {
        return trip;
    }
}

