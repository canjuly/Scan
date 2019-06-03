package com.example.temp.scan;

import android.content.Context;

import java.io.File;

/**
 * Created by temp on 2019/5/16.
 */

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
