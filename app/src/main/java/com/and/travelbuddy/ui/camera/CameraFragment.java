package com.and.travelbuddy.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CameraViewModel cameraViewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        final TextView textView = root.findViewById(R.id.text_camera);
        cameraViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        if (checkPermission()) {
            dispatchTakeImageIntent();
        } else {
            requestPermission();
        }
        return root;
    }


    String currentImagePath = null;
    private File getPhotoFile() throws IOException {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String date = DateFormat.getDateInstance().format(new Date());
        String imageName = "jpg_" + date + "_";

        File image = File.createTempFile(imageName, ".jpg", storageDir);
        currentImagePath = image.getAbsolutePath();
        return image;
    }
    private File imageFile;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public void dispatchTakeImageIntent() {
        Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            imageFile = getPhotoFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (takeImageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeImageIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.and.travelbuddy.ui.fileprovider", imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(imageUri);
            getActivity().sendBroadcast(mediaScanIntent);
            System.out.println("hello");
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    // Defining Permission codes
    final int PERMISSION = 2;
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