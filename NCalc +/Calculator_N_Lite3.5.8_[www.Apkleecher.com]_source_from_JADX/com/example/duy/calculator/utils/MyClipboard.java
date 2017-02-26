package com.example.duy.calculator.utils;

import android.content.ClipData;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.ClipboardManager;
import android.widget.Toast;
import com.example.duy.calculator.R;
import io.github.kexanie.library.BuildConfig;

public class MyClipboard {
    public static void setClipboard(Context context, String text) {
        if (VERSION.SDK_INT < 11) {
            ((ClipboardManager) context.getSystemService("clipboard")).setText(text);
            Toast.makeText(context, R.string.copied, 0).show();
            return;
        }
        ((android.content.ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Copied Text", text));
        Toast.makeText(context, R.string.copied, 0).show();
    }

    public static String getClipboard(Context context) {
        String res = BuildConfig.FLAVOR;
        if (VERSION.SDK_INT < 11) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService("clipboard");
            if (clipboard.getText() != null) {
                res = clipboard.getText().toString();
            } else {
                res = BuildConfig.FLAVOR;
            }
        } else {
            android.content.ClipboardManager clipboard2 = (android.content.ClipboardManager) context.getSystemService("clipboard");
            if (clipboard2.getText() != null) {
                res = clipboard2.getText().toString();
            } else {
                res = BuildConfig.FLAVOR;
            }
        }
        Toast.makeText(context, res, 0).show();
        return res;
    }
}
