package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import com.google.android.gms.internal.zzsz;

public class zzy {
    private static String EE;
    private static int EF;
    private static Object zzaox;
    private static boolean zzchk;

    static {
        zzaox = new Object();
    }

    public static String zzcd(Context context) {
        zzcf(context);
        return EE;
    }

    public static int zzce(Context context) {
        zzcf(context);
        return EF;
    }

    private static void zzcf(Context context) {
        synchronized (zzaox) {
            if (zzchk) {
                return;
            }
            zzchk = true;
            try {
                Bundle bundle = zzsz.zzco(context).getApplicationInfo(context.getPackageName(), AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS).metaData;
                if (bundle == null) {
                    return;
                }
                EE = bundle.getString("com.google.app.id");
                EF = bundle.getInt("com.google.android.gms.version");
            } catch (Throwable e) {
                Log.wtf("MetadataValueReader", "This should never happen.", e);
            }
        }
    }
}
