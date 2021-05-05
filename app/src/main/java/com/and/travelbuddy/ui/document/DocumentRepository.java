package com.and.travelbuddy.ui.document;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DocumentRepository {
    private static DocumentRepository instance;
    private MutableLiveData<String> text;

    public DocumentRepository() {
        text = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return text;
    }

    public void setText() {
        text.setValue("This is documents fragment");
    }

    public static synchronized DocumentRepository getInstance() {
        if (instance == null) {
            instance = new DocumentRepository();
        }
        return instance;
    }
}
