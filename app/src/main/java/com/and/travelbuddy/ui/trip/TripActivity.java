package com.and.travelbuddy.ui.trip;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.and.travelbuddy.R;
import com.and.travelbuddy.databinding.ActivityTripBinding;
import com.and.travelbuddy.ui.document.DocumentDialogFragment;
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

        /** View creation */
        binding = ActivityTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TripSectionsPagerAdapter tripSectionsPagerAdapter = new TripSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.tripActivityViewPager;
        viewPager.setAdapter(tripSectionsPagerAdapter);
        TabLayout tabs = binding.tripActivityTabs;
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = binding.tripFabPlus;
        country = binding.tripActivityTextCountry;

        Toast.makeText(this, country.toString(), Toast.LENGTH_SHORT).show();

//        country.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TripCountryDialogFragment tripCountryDialogFragment = new TripCountryDialogFragment();
//                tripCountryDialogFragment.show(getSupportFragmentManager(), "Edit country");
//            }
//        });

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String string) {
//        country.setText(string);
    }
}