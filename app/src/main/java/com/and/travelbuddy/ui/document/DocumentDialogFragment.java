package com.and.travelbuddy.ui.document;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.and.travelbuddy.R;

public class DocumentDialogFragment extends DialogFragment {

    private TextView textView;
    private Button button;

//    public interface OnInputListener {
//        void sendInput(String input);
//    }
//
//    public OnInputListener onInputListener;

    public static final int PICKFILE_RESULT_CODE = 1;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_document, null);
        builder.setView(view)
                .setTitle(R.string.dialog_document_header)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog, int id) {
                        DocumentDialogFragment.this.getDialog().cancel();
                    }
                });
        Spinner spinner = view.findViewById(R.id.dialog_spinner);
        textView = view.findViewById(R.id.dialog_document_name);

        button = view.findViewById(R.id.dialog_document_button);
        button.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent chooseFile = new Intent (Intent.ACTION_GET_CONTENT);
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    String filePath = fileUri.getPath();
                    textView.setText(filePath);
                }
                break;
        }
    }

//    @Override
//    public void onAttach (Context context) {
//        super.onAttach(context);
//        try {
//            onInputListener = (OnInputListener) getActivity();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}