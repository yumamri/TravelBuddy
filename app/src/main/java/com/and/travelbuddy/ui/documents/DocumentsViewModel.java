package com.and.travelbuddy.ui.documents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocumentsViewModel extends ViewModel {

    DocumentsRepository repository;

    public DocumentsViewModel() {
        repository = DocumentsRepository.getInstance();
    }

    public LiveData<String> text() {
        repository.setText();
        return repository.getText();
    }
}