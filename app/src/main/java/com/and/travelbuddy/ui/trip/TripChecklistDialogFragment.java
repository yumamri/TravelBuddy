package com.and.travelbuddy.ui.trip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.and.travelbuddy.R;

public class TripChecklistDialogFragment extends DialogFragment {
    private EditText editText;

    public interface ChecklistDialogListener {
        void onDialogChecklistPositiveClick(DialogFragment dialog, String string);
    }

    public TripChecklistDialogFragment.ChecklistDialogListener checklistDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_trip_checklist, null);

        /** Data handling between dialog and parent */
        editText = view.findViewById(R.id.checklist_dialog_edit);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.confirm, (dialog, id) -> {
                    checklistDialogListener.onDialogChecklistPositiveClick(TripChecklistDialogFragment.this, editText.getText().toString());
                    TripChecklistDialogFragment.this.getDialog().dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> TripChecklistDialogFragment.this.getDialog().cancel());

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
