package com.and.travelbuddy.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Trip;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TripSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_checklist, R.string.tab_text_document};
    private final Context mContext;

    private Trip tripSend;

    public TripSectionsPagerAdapter(Context context, FragmentManager fm, Trip trip) {
        super(fm);
        mContext = context;
        tripSend = trip;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 1:
                return TripDocumentFragment.newInstance(tripSend);
            default:
                return TripChecklistFragment.newInstance(tripSend);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}