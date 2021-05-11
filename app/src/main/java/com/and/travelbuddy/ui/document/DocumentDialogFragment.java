package com.and.travelbuddy.ui.document;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.and.travelbuddy.R;

public class DocumentDialogFragment extends DialogFragment {

    private TextView textView;
    private ImageButton button;

    public interface DialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String string, Uri uri);
    }

    public String fileName;
    public Uri uri;

    public DialogListener listener;

    public static final int PICKFILE_RESULT_CODE = 1;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_document, null);
        // TODO: add category
        Spinner spinner = view.findViewById(R.id.document_dialog_spinner_category);

        /** Data handling between dialog and parent */
        textView = view.findViewById(R.id.document_dialog_text_name);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, (dialog, id) -> {
                    listener.onDialogPositiveClick(DocumentDialogFragment.this, fileName, uri);
                    DocumentDialogFragment.this.getDialog().dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> DocumentDialogFragment.this.getDialog().cancel());

        /** File chooser to add the documents */
        button = view.findViewById(R.id.document_dialog_button_plus);
        button.setOnClickListener (view1 -> {
            Intent chooseFile = new Intent (Intent.ACTION_GET_CONTENT);
            chooseFile.setType("*/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /** File name and Uri to send back */
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    uri = data.getData();
                    Cursor returnCursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    fileName = returnCursor.getString(nameIndex);

                    textView.setText(fileName);
                }
                break;
        }
    }

}