package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

@Deprecated
public class zzae {
    private static volatile Logger ft;

    static {
        setLogger(new zzs());
    }

    public static Logger getLogger() {
        return ft;
    }

    public static void setLogger(Logger logger) {
        ft = logger;
    }

    public static void m1696v(String str) {
        zzaf zzagg = zzaf.zzagg();
        if (zzagg != null) {
            zzagg.zzes(str);
        } else if (zzbi(0)) {
            Log.v((String) zzy.en.get(), str);
        }
        Logger logger = ft;
        if (logger != null) {
            logger.verbose(str);
        }
    }

    public static boolean zzbi(int i) {
        return getLogger() != null && getLogger().getLogLevel() <= i;
    }

    public static void zzdh(String str) {
        zzaf zzagg = zzaf.zzagg();
        if (zzagg != null) {
            zzagg.zzeu(str);
        } else if (zzbi(1)) {
            Log.i((String) zzy.en.get(), str);
        }
        Logger logger = ft;
        if (logger != null) {
            logger.info(str);
        }
    }

    public static void zzdi(String str) {
        zzaf zzagg = zzaf.zzagg();
        if (zzagg != null) {
            zzagg.zzev(str);
        } else if (zzbi(2)) {
            Log.w((String) zzy.en.get(), str);
        }
        Logger logger = ft;
        if (logger != null) {
            logger.warn(str);
        }
    }

    public static void zzf(String str, Object obj) {
        zzaf zzagg = zzaf.zzagg();
        if (zzagg != null) {
            zzagg.zze(str, obj);
        } else if (zzbi(3)) {
            String stringBuilder;
            if (obj != null) {
                String valueOf = String.valueOf(obj);
                stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(valueOf).length()).append(str).append(":").append(valueOf).toString();
            } else {
                stringBuilder = str;
            }
            Log.e((String) zzy.en.get(), stringBuilder);
        }
        Logger logger = ft;
        if (logger != null) {
            logger.error(str);
        }
    }
}
