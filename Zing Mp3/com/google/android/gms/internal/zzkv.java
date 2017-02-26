package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzu;
import java.math.BigInteger;
import java.util.Locale;

@zzji
public final class zzkv {
    private static final Object zzaox;
    private static String zzcuq;

    static {
        zzaox = new Object();
    }

    public static String zza(Context context, String str, String str2) {
        String str3;
        synchronized (zzaox) {
            if (zzcuq == null && !TextUtils.isEmpty(str)) {
                zzb(context, str, str2);
            }
            str3 = zzcuq;
        }
        return str3;
    }

    private static void zzb(Context context, String str, String str2) {
        try {
            ClassLoader classLoader = context.createPackageContext(str2, 3).getClassLoader();
            Class cls = Class.forName("com.google.ads.mediation.MediationAdapter", false, classLoader);
            BigInteger bigInteger = new BigInteger(new byte[1]);
            String[] split = str.split(",");
            BigInteger bigInteger2 = bigInteger;
            for (int i = 0; i < split.length; i++) {
                if (zzu.zzgm().zza(classLoader, cls, split[i])) {
                    bigInteger2 = bigInteger2.setBit(i);
                }
            }
            zzcuq = String.format(Locale.US, "%X", new Object[]{bigInteger2});
        } catch (Throwable th) {
            zzcuq = NotificationCompatApi24.CATEGORY_ERROR;
        }
    }

    public static String zzvl() {
        String str;
        synchronized (zzaox) {
            str = zzcuq;
        }
        return str;
    }
}
