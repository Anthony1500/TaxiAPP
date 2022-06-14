package com.example.taxiapp.ui.informacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class informacionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public informacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is information fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}