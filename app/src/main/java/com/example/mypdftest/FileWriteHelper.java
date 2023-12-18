package com.example.mypdftest;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

public class FileWriteHelper {

    public static void writeToExternalStorage(Context context, String fileName, String content) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Downloads.IS_PENDING, 1);
        }

        // 获取ContentResolver
        context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

        // 写入文件内容
        // 此处可以使用流写入文件内容，具体根据需求实现

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Downloads.IS_PENDING, 0);
            context.getContentResolver().update(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values, null, null);
        }
    }
}

