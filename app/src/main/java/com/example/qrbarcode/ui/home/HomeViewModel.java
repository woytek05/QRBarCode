package com.example.qrbarcode.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> scannedResult = new MutableLiveData<>();

    public void setScannedResult(String result) {
        scannedResult.setValue(result);
    }

    public LiveData<String> getScannedResult() {
        return scannedResult;
    }
}