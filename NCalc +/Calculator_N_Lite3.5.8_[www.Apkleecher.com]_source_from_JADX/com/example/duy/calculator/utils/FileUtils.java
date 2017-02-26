package com.example.duy.calculator.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.FileOutputStream;

public class FileUtils {
    private Context context;

    public FileUtils(Context context) {
        this.context = context;
    }

    public boolean save(String fileName, String data) {
        try {
            FileOutputStream outputStream = this.context.openFileOutput(fileName, 0);
            outputStream.write(data.getBytes());
            outputStream.close();
            Toast.makeText(this.context, "Save OK, " + fileName, 0).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isExternalStorageWritable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state) || "mounted_ro".equals(state)) {
            return true;
        }
        return false;
    }
}
