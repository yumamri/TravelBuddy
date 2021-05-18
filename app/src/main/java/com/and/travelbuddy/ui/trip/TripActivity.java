package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.databinding.ActivityTripBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class TripActivity extends AppCompatActivity implements TripCountryDialogFragment.DialogCountryListener, TripImageDialogFragment.DialogImageListener {

    private static final String TAG = "TRIP_UPDATE";
    private ActivityTripBinding binding;
    private TextView country;
    private TextView date;
    private ImageView image;
    private TextView imageUrl;
    private ExtendedFloatingActionButton efab;
    private FloatingActionButton fab;
    private Trip trip;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trips");

    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trip = (Trip) getIntent().getSerializableExtra("TRIP_KEY");
        /** View creation */
        binding = ActivityTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TripSectionsPagerAdapter tripSectionsPagerAdapter = new TripSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.tripActivityViewPager;
        viewPager.setAdapter(tripSectionsPagerAdapter);
        TabLayout tabs = binding.tripActivityTabs;
        tabs.setupWithViewPager(viewPager);

        /** Date picker */
        date = binding.tripActivityTextDate;
        date.setText(trip.getDate());
        date.setOnClickListener(view -> {
            MaterialDatePicker dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    .setSelection(new Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                    .build();
            dateRangePicker.addOnPositiveButtonClickListener(selection -> {
                Long start = (Long) ((Pair<?, ?>) selection).first;
                Long end = (Long) ((Pair<?, ?>) selection).second;

                SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy");
                Calendar startCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

                startCalendar.setTimeInMillis(start);
                endCalendar.setTimeInMillis(end);


                String startDate = format.format(startCalendar.getTime());
                String endDate = format.format(endCalendar.getTime());

                String dateString = startDate + " - " + endDate;

                date.setText(dateString);
            });
            dateRangePicker.show(getSupportFragmentManager(), "Select dates");
        });

        /** Edit country */
        country = binding.tripActivityTextCountry;
        country.setText(trip.getCountry());
        country.setOnClickListener(view -> {
            TripCountryDialogFragment tripCountryDialogFragment = new TripCountryDialogFragment();
            tripCountryDialogFragment.show(getSupportFragmentManager(), "Edit country");
        });

        /** Edit Image */
        image = binding.tripActivityImage;
        imageUrl = binding.tripActivityTextImage;
        imageUrl.setText(trip.getImage());
        fab = binding.tripFabImage;
        Picasso.get().load(trip.getImage()).fit().centerInside().into(image);
        fab.setOnClickListener(view -> {
            TripImageDialogFragment tripImageDialogFragment = new TripImageDialogFragment(view);
            tripImageDialogFragment.show(getSupportFragmentManager(), "Edit Image");
        });

        efab = binding.tripEfabSave;
        efab.setOnClickListener(view -> {
            /** Save new information */
            updateUser();
            finish();
        });
    }

    private void updateUser() {
        valueEventListener = databaseReference.child(trip.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> postValues = new HashMap<String, Object>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    postValues.put(snapshot.getKey(), snapshot.getValue());
                }
                postValues.put("country", country.getText().toString());
                postValues.put("date", date.getText().toString());
                postValues.put("image", imageUrl.getText().toString());
                databaseReference.child(trip.getKey()).updateChildren(postValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled:" + databaseError);
            }
        });
    }

    @Override
    public void onDialogCountryPositiveClick(DialogFragment dialog, String string) {
        country.setText(string);
    }

    @Override
    public void onDialogImagePositiveClick(DialogFragment dialog, String string) {
        Picasso.get().load(string).fit().centerInside().into(image);
        imageUrl.setText(string);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof TripCountryDialogFragment) {
            ((TripCountryDialogFragment) fragment).countryListener = this;
        }
        if (fragment instanceof TripImageDialogFragment) {
            ((TripImageDialogFragment) fragment).imageListener = this;
        }
    }

    @Override
    public void onDestroy() {
        databaseReference.removeEventListener(valueEventListener);
        super.onDestroy();
    }
}