package com.example.qrbarcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public class BarCodeGenerator {
    public static Bitmap generateBarcode(String content, int width, int height) {
        try {
            Code128Writer writer = new Code128Writer();
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
