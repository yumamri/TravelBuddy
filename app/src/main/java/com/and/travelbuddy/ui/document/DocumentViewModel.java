package com.and.travelbuddy.ui.document;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DocumentViewModel extends ViewModel {

    private DocumentRepository repository;

    public DocumentViewModel() {
        repository = DocumentRepository.getInstance();
    }

    public LiveData<String> text() {
        repository.setText();
        return repository.getText();
    }
}