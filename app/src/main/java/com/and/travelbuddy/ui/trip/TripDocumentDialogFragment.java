package com.and.travelbuddy.ui.trip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Document;
import com.and.travelbuddy.data.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TripDocumentDialogFragment extends DialogFragment {

    public interface TripDocumentListener {
        void onDialogTripDocumentPositiveClick(DialogFragment dialog, Document document);
    }

    private Spinner spinner;
    private ArrayAdapter documentArrayAdapter;
    private ArrayList<Document> documentArrayList;
    public TripDocumentDialogFragment.TripDocumentListener tripDocumentListener;
    private String documentsKey = "DOCUMENTS_KEY";
    private static final String TAG = "TRIP_DOCUMENT_DATABASE";

    public static TripDocumentDialogFragment newInstance(ArrayList<Document> documents) {
        TripDocumentDialogFragment fragment = new TripDocumentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(fragment.documentsKey, documents);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_trip_document, null);

        /** Data handling between dialog and parent */

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.confirm, (dialog, id) -> {
                    tripDocumentListener.onDialogTripDocumentPositiveClick(TripDocumentDialogFragment.this, (Document) spinner.getSelectedItem());
                    TripDocumentDialogFragment.this.getDialog().dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> TripDocumentDialogFragment.this.getDialog().cancel());

        spinner = view.findViewById(R.id.trip_document_dialog_spinner);
        documentArrayList = (ArrayList<Document>) getArguments().getSerializable(documentsKey);
        System.out.println(spinner);

        documentArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, documentArrayList);
        documentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(documentArrayAdapter);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
