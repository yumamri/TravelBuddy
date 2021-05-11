package com.and.travelbuddy.ui.trip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.and.travelbuddy.R;

public class TripCountryDialogFragment extends DialogFragment {

    private EditText editText;

    public interface DialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String string);
    }

    public DialogListener listener;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_trip_country, null);

        /** Data handling between dialog and parent */
        editText = view.findViewById(R.id.trip_dialog_edit_country);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, (dialog, id) -> {
                    listener.onDialogPositiveClick(TripCountryDialogFragment.this, editText.getText().toString());
                    TripCountryDialogFragment.this.getDialog().dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> TripCountryDialogFragment.this.getDialog().cancel());

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
