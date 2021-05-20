package com.and.travelbuddy.ui.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.and.travelbuddy.data.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private MutableLiveData<String> mText;

    public ProfileViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }
    public LiveData<String> getText() {
        return mText;
    }
    public void signOut() {
        userRepository.signOut();
    }
}