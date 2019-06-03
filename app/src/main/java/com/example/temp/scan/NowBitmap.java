package com.example.temp.scan;

import android.graphics.Bitmap;

public class NowBitmap {
    private static Bitmap bitmap;

    public static void setBitmap(Bitmap bitmap1) {
        bitmap = bitmap1;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }
}
