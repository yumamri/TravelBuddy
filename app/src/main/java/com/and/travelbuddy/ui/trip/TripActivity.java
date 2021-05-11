package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.and.travelbuddy.R;
import com.and.travelbuddy.databinding.ActivityTripBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class TripActivity extends AppCompatActivity implements TripCountryDialogFragment.DialogListener {

    private ActivityTripBinding binding;
    //Trip trip = getIntent().getParcelableExtra("TRIP");
    private TextView country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        country = findViewById(R.id.trip_item_text_country);

        /** View creation */
        binding = ActivityTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabsTrips;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fabTripPlus;

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String string) {
        country.setText(string);
    }
}