package com.and.travelbuddy.ui.trip;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.and.travelbuddy.data.Document;

import java.util.ArrayList;

public class TripDocumentArrayAdapter extends ArrayAdapter<Document> {
    public TripDocumentArrayAdapter(@NonNull Context context, int resource, ArrayList<Document> documentArrayList) {
        super(context, resource, documentArrayList);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


}
