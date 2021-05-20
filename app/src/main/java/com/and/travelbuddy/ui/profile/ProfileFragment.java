package com.and.travelbuddy.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.and.travelbuddy.R;
import com.and.travelbuddy.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    private LiveData<FirebaseUser> user;
    private TextView name;
    private ImageView pp;
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Button buttonSignOut = root.findViewById(R.id.profile_fragment_button_signout);
        buttonSignOut.setOnClickListener(v -> signOut());

        user = profileViewModel.getCurrentUser();
        name = root.findViewById(R.id.profile_fragment_text_name);
        pp = root.findViewById(R.id.profile_fragment_image_pp);

        name.setText(user.getValue().getDisplayName());
        Picasso.get().load(user.getValue().getPhotoUrl()).fit().centerInside().into(pp);
        return root;
    }

    public void signOut() {
        profileViewModel.signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}