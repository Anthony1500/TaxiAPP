package com.example.taxiapp.ui.ayuda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ayudaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ayudaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Help fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}