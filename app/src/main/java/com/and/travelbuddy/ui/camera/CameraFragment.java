package com.and.travelbuddy.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class CameraFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CameraViewModel cameraViewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        final TextView textView = root.findViewById(R.id.text_camera);
        cameraViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        if (checkPermission()) {
            dispatchTakePictureIntent();
        } else {
            requestPermission();
        }
        return root;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode " + resultCode);
        getActivity().setResult(resultCode,data);
        System.out.println("resultCode after setResult " + resultCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            System.out.println("activity result?");
            galleryAddPic();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String date = DateFormat.getDateTimeInstance().format(new Date());
        File image = File.createTempFile(
                date,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (photoFile != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
    // Defining Permission codes
    private static final int PERMISSION = 2;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    // Function to check and request permission
    public void requestPermission() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        "Permission already granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION);
            }
        }
    }

    // Function to check permission
    public boolean checkPermission() {
        for (String permission : PERMISSIONS) {
            return ActivityCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

}