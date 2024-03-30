package com.example.qrbarcode.ui.barcode;

import android.graphics.Bitmap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.qrbarcode.BarCodeGenerator;

public class BarCodeViewModel extends ViewModel {
    private final MutableLiveData<Bitmap> barCodeBitmap = new MutableLiveData<>();

    public BarCodeViewModel() { }

    public void generateBarCode(String content, int width, int height) {
        Bitmap bitmap = BarCodeGenerator.generateBarcode(content, width, height);
        barCodeBitmap.setValue(bitmap);
    }

    public LiveData<Bitmap> getBarCodeBitmap() {
        return barCodeBitmap;
    }
}