package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.databinding.ActivityTripBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class TripActivity extends AppCompatActivity implements TripCountryDialogFragment.DialogListener {

    private ActivityTripBinding binding;
    private TextView country;
    private TextView date;
    private Trip trip;
    private Map<String, Object> values;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://travel-buddy-uwu-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trips");

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
        ExtendedFloatingActionButton efab = binding.tripFabPlus;

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
                trip.setDate(dateString);
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

        efab.setOnClickListener(view -> {
            /** Save information */
            String key = trip.getKey();
            values = trip.toMap();
            Map<String, Object> tripUpdates = new HashMap<>();
            tripUpdates.put(key, values);
            tripUpdates.put(key, values);
            databaseReference.updateChildren(tripUpdates);
            finish();
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String string) {
        country.setText(string);
        trip.setCountry(string);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof TripCountryDialogFragment) {
            ((TripCountryDialogFragment) fragment).listener = this;
        }
    }
}