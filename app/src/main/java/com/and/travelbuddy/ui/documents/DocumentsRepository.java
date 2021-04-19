package com.and.travelbuddy.ui.documents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DocumentsRepository {
    private static DocumentsRepository instance;
    private MutableLiveData<String> text;

    public DocumentsRepository() {
        text = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return text;
    }

    public void setText() {
        text.setValue("This is documents fragment");
    }

    public static synchronized DocumentsRepository getInstance() {
        if (instance == null) {
            instance = new DocumentsRepository();
        }
        return instance;
    }
}
