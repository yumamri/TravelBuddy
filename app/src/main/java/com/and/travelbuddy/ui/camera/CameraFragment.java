package com.and.travelbuddy.ui.camera;

import android.app.Activity;
import android.content.ContentResolver;
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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.and.travelbuddy.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class CameraFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        requestPermission();
        return root;
    }

    private String currentImagePath = null;
    private File imageFile;
    private String fileName;
    private File storageDir;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    // Defining Permission codes
    final int PERMISSION = 2;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    /** Launch camera */
    public void dispatchTakePictureIntent() {
        Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            imageFile = getPhotoFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (imageFile != null) {
            if (takeImageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.and.travelbuddy.ui.fileprovider", imageFile);
                takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takeImageIntent, REQUEST_IMAGE_CAPTURE);
                getActivity().setResult(Activity.RESULT_OK, takeImageIntent);
            }
        } else {
            System.out.println("File empty");
        }

    }

    /** Create a File for saving an image or video */
    private File getPhotoFile() throws IOException {
        storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String date = DateFormat.getDateInstance().format(new Date());
        fileName = "jpg_" + date + "_";
        if(!storageDir.exists())
        {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(fileName, ".jpg", storageDir);
        currentImagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(currentImagePath);
            Uri imageUri = Uri.fromFile(file);
            mediaScanIntent.setData(imageUri);
            getActivity().sendBroadcast(mediaScanIntent);

            ImageView imageView = getActivity().findViewById(R.id.image_document);
            Bitmap bitmap = BitmapFactory.decodeFile(currentImagePath);
            imageView.setImageBitmap(bitmap);

            ContentResolver cr = getActivity().getContentResolver();
            MediaStore.Images.Media.insertImage(cr, bitmap, fileName, fileName);
        }
    }

    /** Function to check and request permission */
    public void requestPermission() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        "Permission already granted",
                        Toast.LENGTH_SHORT)
                        .show();
                dispatchTakePictureIntent();
            }
            else {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION);
            }
        }
    }

}