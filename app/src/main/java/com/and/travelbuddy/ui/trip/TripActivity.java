package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.and.travelbuddy.data.Trip;
import com.and.travelbuddy.databinding.ActivityTripBinding;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TripActivity extends AppCompatActivity implements TripCountryDialogFragment.DialogListener {

    private ActivityTripBinding binding;
    Trip trip = getIntent().getExtras().getParcelable("TRIP_KEY");
    private TextView country;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** View creation */
        binding = ActivityTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TripSectionsPagerAdapter tripSectionsPagerAdapter = new TripSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.tripActivityViewPager;
        viewPager.setAdapter(tripSectionsPagerAdapter);
        TabLayout tabs = binding.tripActivityTabs;
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = binding.tripFabPlus;

        date = binding.tripActivityTextDate;
        date.setText(trip.getDate());
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .setSelection(new Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                        .build();
                dateRangePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
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
                    }
                });
                dateRangePicker.show(getSupportFragmentManager(), "Select dates");
            }
        });

        country = binding.tripActivityTextCountry;
        country.setText(trip.getCountry());
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCountryDialogFragment tripCountryDialogFragment = new TripCountryDialogFragment();
                tripCountryDialogFragment.show(getSupportFragmentManager(), "Edit country");
            }
        });

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String string) {
        country.setText(string);
        trip.setCity(string);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof TripCountryDialogFragment) {
            ((TripCountryDialogFragment) fragment).listener = this;
        }
    }
}