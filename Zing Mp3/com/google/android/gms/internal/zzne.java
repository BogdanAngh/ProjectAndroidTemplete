package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;
import java.util.Map;

public final class zzne extends zzg<zzne> {
    public long cA;
    public String ce;
    public String cz;
    public String mCategory;

    public String getCategory() {
        return this.mCategory;
    }

    public String getLabel() {
        return this.ce;
    }

    public long getTimeInMillis() {
        return this.cA;
    }

    public void setTimeInMillis(long j) {
        this.cA = j;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("variableName", this.cz);
        hashMap.put("timeInMillis", Long.valueOf(this.cA));
        hashMap.put("category", this.mCategory);
        hashMap.put("label", this.ce);
        return zzg.zzj(hashMap);
    }

    public void zza(zzne com_google_android_gms_internal_zzne) {
        if (!TextUtils.isEmpty(this.cz)) {
            com_google_android_gms_internal_zzne.zzeo(this.cz);
        }
        if (this.cA != 0) {
            com_google_android_gms_internal_zzne.setTimeInMillis(this.cA);
        }
        if (!TextUtils.isEmpty(this.mCategory)) {
            com_google_android_gms_internal_zzne.zzee(this.mCategory);
        }
        if (!TextUtils.isEmpty(this.ce)) {
            com_google_android_gms_internal_zzne.zzeg(this.ce);
        }
    }

    public String zzabm() {
        return this.cz;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzne) com_google_android_gms_analytics_zzg);
    }

    public void zzee(String str) {
        this.mCategory = str;
    }

    public void zzeg(String str) {
        this.ce = str;
    }

    public void zzeo(String str) {
        this.cz = str;
    }
}
