package com.and.travelbuddy.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;
import com.and.travelbuddy.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    TextView name;
    ImageView pp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();

        Button buttonSignOut = root.findViewById(R.id.profile_fragment_button_signout);
        buttonSignOut.setOnClickListener(v -> auth.signOut());

        user = auth.getCurrentUser();
        name = root.findViewById(R.id.profile_fragment_text_name);
        pp = root.findViewById(R.id.profile_fragment_image_pp);

        name.setText(user.getDisplayName());
        Picasso.get().load(user.getPhotoUrl()).fit().centerInside().into(pp);
        return root;
    }
}