package com.badlogic.gdx.utils;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
    public static final byte[] EMPTY_BYTES;

    static {
        EMPTY_BYTES = new byte[0];
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        copyStream(input, output, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
    }

    public static void copyStream(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        while (true) {
            int bytesRead = input.read(buffer);
            if (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
            } else {
                return;
            }
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
