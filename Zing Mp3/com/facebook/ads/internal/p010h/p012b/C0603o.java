package com.facebook.ads.internal.p010h.p012b;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.facebook.share.internal.ShareConstants;
import com.mp3download.zingmp3.BuildConfig;
import java.io.File;

/* renamed from: com.facebook.ads.internal.h.b.o */
final class C0603o {
    public static File m1117a(Context context) {
        return new File(C0603o.m1118a(context, true), "video-cache");
    }

    private static File m1118a(Context context, boolean z) {
        Object externalStorageState;
        File file = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = BuildConfig.FLAVOR;
        }
        if (z && "mounted".equals(r1)) {
            file = C0603o.m1119b(context);
        }
        if (file == null) {
            file = context.getCacheDir();
        }
        if (file != null) {
            return file;
        }
        String str = "/data/data/" + context.getPackageName() + "/cache/";
        Log.w("ProxyCache", "Can't define system cache directory! '" + str + "%s' will be used.");
        return new File(str);
    }

    private static File m1119b(Context context) {
        File file = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), ShareConstants.WEB_DIALOG_PARAM_DATA), context.getPackageName()), "cache");
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        Log.w("ProxyCache", "Unable to create external cache directory");
        return null;
    }
}
