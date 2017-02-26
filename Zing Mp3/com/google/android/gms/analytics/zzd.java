package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.LogPrinter;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class zzd implements zzk {
    private static final Uri aT;
    private final LogPrinter aU;

    /* renamed from: com.google.android.gms.analytics.zzd.1 */
    class C11681 implements Comparator<zzg> {
        final /* synthetic */ zzd aV;

        C11681(zzd com_google_android_gms_analytics_zzd) {
            this.aV = com_google_android_gms_analytics_zzd;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return zza((zzg) obj, (zzg) obj2);
        }

        public int zza(zzg com_google_android_gms_analytics_zzg, zzg com_google_android_gms_analytics_zzg2) {
            return com_google_android_gms_analytics_zzg.getClass().getCanonicalName().compareTo(com_google_android_gms_analytics_zzg2.getClass().getCanonicalName());
        }
    }

    static {
        Builder builder = new Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("local");
        aT = builder.build();
    }

    public zzd() {
        this.aU = new LogPrinter(4, "GA/LogCatTransport");
    }

    public void zzb(zze com_google_android_gms_analytics_zze) {
        List<zzg> arrayList = new ArrayList(com_google_android_gms_analytics_zze.zzzj());
        Collections.sort(arrayList, new C11681(this));
        StringBuilder stringBuilder = new StringBuilder();
        for (zzg obj : arrayList) {
            Object obj2 = obj.toString();
            if (!TextUtils.isEmpty(obj2)) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(obj2);
            }
        }
        this.aU.println(stringBuilder.toString());
    }

    public Uri zzyx() {
        return aT;
    }
}
