package com.example.taxiapp.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class mapsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public mapsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Maps fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
