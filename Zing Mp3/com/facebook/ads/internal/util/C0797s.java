package com.facebook.ads.internal.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.facebook.ads.AudienceNetworkActivity;
import com.google.android.exoplayer.util.NalUnitUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/* renamed from: com.facebook.ads.internal.util.s */
public class C0797s {
    @Nullable
    public static final String m1666a(Context context, String str) {
        try {
            return C0797s.m1670b(context.getPackageManager().getApplicationInfo(str, 0).sourceDir);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static final String m1667a(File file) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
            InputStream fileInputStream = new FileInputStream(file);
            int read;
            do {
                read = fileInputStream.read(bArr);
                if (read > 0) {
                    instance.update(bArr, 0, read);
                }
            } while (read != -1);
            fileInputStream.close();
            return C0797s.m1669a(instance.digest());
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static String m1668a(String str) {
        try {
            return C0797s.m1669a(MessageDigest.getInstance("MD5").digest(str.getBytes(AudienceNetworkActivity.WEBVIEW_ENCODING)));
        } catch (Exception e) {
            return null;
        }
    }

    private static final String m1669a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bArr) {
            stringBuilder.append(Integer.toString((b & NalUnitUtil.EXTENDED_SAR) + AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, 16).substring(1));
        }
        return stringBuilder.toString();
    }

    @Nullable
    public static final String m1670b(String str) {
        return C0797s.m1667a(new File(str));
    }
}
