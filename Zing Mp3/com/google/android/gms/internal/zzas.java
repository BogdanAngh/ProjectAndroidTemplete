package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.internal.zzaf.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class zzas extends zzat {
    private static final String TAG;
    private Info zzagv;

    static {
        TAG = zzas.class.getSimpleName();
    }

    protected zzas(Context context) {
        super(context, BuildConfig.FLAVOR);
    }

    public static zzas zzc(Context context) {
        zzat.zza(context, true);
        return new zzas(context);
    }

    protected zza zza(Context context, View view) {
        return null;
    }

    public String zza(String str, String str2) {
        return zzan.zza(str, str2, true);
    }

    public void zza(Info info) {
        this.zzagv = info;
    }

    protected void zza(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza, zzad.zza com_google_android_gms_internal_zzad_zza) {
        if (!com_google_android_gms_internal_zzbc.zzcr()) {
            zza(zzb(com_google_android_gms_internal_zzbc, com_google_android_gms_internal_zzaf_zza, com_google_android_gms_internal_zzad_zza));
        } else if (this.zzagv != null) {
            Object id = this.zzagv.getId();
            if (!TextUtils.isEmpty(id)) {
                com_google_android_gms_internal_zzaf_zza.zzew = zzbe.zzr(id);
                com_google_android_gms_internal_zzaf_zza.zzex = Integer.valueOf(5);
                com_google_android_gms_internal_zzaf_zza.zzey = Boolean.valueOf(this.zzagv.isLimitAdTrackingEnabled());
            }
            this.zzagv = null;
        }
    }

    protected List<Callable<Void>> zzb(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza, zzad.zza com_google_android_gms_internal_zzad_zza) {
        List<Callable<Void>> arrayList = new ArrayList();
        if (com_google_android_gms_internal_zzbc.zzcm() == null) {
            return arrayList;
        }
        zzbc com_google_android_gms_internal_zzbc2 = com_google_android_gms_internal_zzbc;
        arrayList.add(new zzbm(com_google_android_gms_internal_zzbc2, zzay.zzbm(), zzay.zzbn(), com_google_android_gms_internal_zzaf_zza, com_google_android_gms_internal_zzbc.zzaw(), 24));
        return arrayList;
    }
}
