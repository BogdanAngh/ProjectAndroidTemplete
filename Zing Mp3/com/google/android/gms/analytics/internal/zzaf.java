package com.google.android.gms.analytics.internal;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import com.google.android.gms.common.internal.zzaa;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Map;
import java.util.Map.Entry;

public class zzaf extends zzd {
    private static String fu;
    private static String fv;
    private static zzaf fw;

    static {
        fu = "3";
        fv = "01VDIWEA?";
    }

    public zzaf(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
    }

    public static zzaf zzagg() {
        return fw;
    }

    public void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        String str2 = (String) zzy.en.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc.zzc(str, obj, obj2, obj3));
        }
        if (i >= 5) {
            zzb(i, str, obj, obj2, obj3);
        }
    }

    public void zza(zzab com_google_android_gms_analytics_internal_zzab, String str) {
        Object obj;
        if (str == null) {
            obj = "no reason provided";
        }
        Object com_google_android_gms_analytics_internal_zzab2 = com_google_android_gms_analytics_internal_zzab != null ? com_google_android_gms_analytics_internal_zzab.toString() : "no hit data";
        String str2 = "Discarding hit. ";
        String valueOf = String.valueOf(obj);
        zzd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), com_google_android_gms_analytics_internal_zzab2);
    }

    public synchronized void zzb(int i, String str, Object obj, Object obj2, Object obj3) {
        int i2 = 0;
        synchronized (this) {
            char c;
            zzaa.zzy(str);
            if (i >= 0) {
                i2 = i;
            }
            int length = i2 >= fv.length() ? fv.length() - 1 : i2;
            if (zzacb().zzaef()) {
                zzacb();
                c = 'C';
            } else {
                zzacb();
                c = 'c';
            }
            String str2 = fu;
            char charAt = fv.charAt(length);
            String str3 = zze.VERSION;
            String valueOf = String.valueOf(zzc.zzc(str, zzm(obj), zzm(obj2), zzm(obj3)));
            String stringBuilder = new StringBuilder(((String.valueOf(str2).length() + 3) + String.valueOf(str3).length()) + String.valueOf(valueOf).length()).append(str2).append(charAt).append(c).append(str3).append(":").append(valueOf).toString();
            if (stringBuilder.length() > AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT) {
                stringBuilder = stringBuilder.substring(0, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
            }
            zzai zzaco = zzabx().zzaco();
            if (zzaco != null) {
                zzaco.zzagt().zzfg(stringBuilder);
            }
        }
    }

    public void zzh(Map<String, String> map, String str) {
        Object obj;
        Object stringBuilder;
        if (str == null) {
            obj = "no reason provided";
        }
        if (map != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                if (stringBuilder2.length() > 0) {
                    stringBuilder2.append(',');
                }
                stringBuilder2.append((String) entry.getKey());
                stringBuilder2.append('=');
                stringBuilder2.append((String) entry.getValue());
            }
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = "no hit data";
        }
        String str2 = "Discarding hit. ";
        String valueOf = String.valueOf(obj);
        zzd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), stringBuilder);
    }

    protected String zzm(Object obj) {
        if (obj == null) {
            return null;
        }
        Object l = obj instanceof Integer ? new Long((long) ((Integer) obj).intValue()) : obj;
        if (!(l instanceof Long)) {
            return l instanceof Boolean ? String.valueOf(l) : l instanceof Throwable ? l.getClass().getCanonicalName() : "-";
        } else {
            if (Math.abs(((Long) l).longValue()) < 100) {
                return String.valueOf(l);
            }
            String str = String.valueOf(l).charAt(0) == '-' ? "-" : BuildConfig.FLAVOR;
            String valueOf = String.valueOf(Math.abs(((Long) l).longValue()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(Math.round(Math.pow(10.0d, (double) (valueOf.length() - 1))));
            stringBuilder.append("...");
            stringBuilder.append(str);
            stringBuilder.append(Math.round(Math.pow(10.0d, (double) valueOf.length()) - 1.0d));
            return stringBuilder.toString();
        }
    }

    protected void zzzy() {
        synchronized (zzaf.class) {
            fw = this;
        }
    }
}
