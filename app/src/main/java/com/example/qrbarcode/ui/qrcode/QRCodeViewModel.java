package com.example.qrbarcode.ui.qrcode;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrbarcode.QRCodeGenerator;

public class QRCodeViewModel extends ViewModel {
    private final MutableLiveData<Bitmap> qrCodeBitmap = new MutableLiveData<>();

    public QRCodeViewModel() { }

    public void generateQRCode(String content, int width, int height) {
        Bitmap bitmap = QRCodeGenerator.generateQRCode(content, width, height);
        qrCodeBitmap.setValue(bitmap);
    }

    public LiveData<Bitmap> getQRCodeBitmap() {
        return qrCodeBitmap;
    }
}