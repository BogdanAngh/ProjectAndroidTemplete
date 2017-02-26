package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;
import java.util.Map;

public final class zzmx extends zzg<zzmx> {
    private String bZ;
    public int ca;
    public int cb;
    public int cc;
    public int zzbzd;
    public int zzbze;

    public String getLanguage() {
        return this.bZ;
    }

    public void setLanguage(String str) {
        this.bZ = str;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("language", this.bZ);
        hashMap.put("screenColors", Integer.valueOf(this.ca));
        hashMap.put("screenWidth", Integer.valueOf(this.zzbzd));
        hashMap.put("screenHeight", Integer.valueOf(this.zzbze));
        hashMap.put("viewportWidth", Integer.valueOf(this.cb));
        hashMap.put("viewportHeight", Integer.valueOf(this.cc));
        return zzg.zzj(hashMap);
    }

    public void zza(zzmx com_google_android_gms_internal_zzmx) {
        if (this.ca != 0) {
            com_google_android_gms_internal_zzmx.zzby(this.ca);
        }
        if (this.zzbzd != 0) {
            com_google_android_gms_internal_zzmx.zzbz(this.zzbzd);
        }
        if (this.zzbze != 0) {
            com_google_android_gms_internal_zzmx.zzca(this.zzbze);
        }
        if (this.cb != 0) {
            com_google_android_gms_internal_zzmx.zzcb(this.cb);
        }
        if (this.cc != 0) {
            com_google_android_gms_internal_zzmx.zzcc(this.cc);
        }
        if (!TextUtils.isEmpty(this.bZ)) {
            com_google_android_gms_internal_zzmx.setLanguage(this.bZ);
        }
    }

    public int zzaaq() {
        return this.ca;
    }

    public int zzaar() {
        return this.zzbzd;
    }

    public int zzaas() {
        return this.zzbze;
    }

    public int zzaat() {
        return this.cb;
    }

    public int zzaau() {
        return this.cc;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzmx) com_google_android_gms_analytics_zzg);
    }

    public void zzby(int i) {
        this.ca = i;
    }

    public void zzbz(int i) {
        this.zzbzd = i;
    }

    public void zzca(int i) {
        this.zzbze = i;
    }

    public void zzcb(int i) {
        this.cb = i;
    }

    public void zzcc(int i) {
        this.cc = i;
    }
}
