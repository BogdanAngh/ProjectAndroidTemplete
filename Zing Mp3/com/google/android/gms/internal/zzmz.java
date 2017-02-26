package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;
import java.util.Map;

public final class zzmz extends zzg<zzmz> {
    private String cd;
    private String ce;
    private long cf;
    private String mCategory;

    public String getAction() {
        return this.cd;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public String getLabel() {
        return this.ce;
    }

    public long getValue() {
        return this.cf;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("category", this.mCategory);
        hashMap.put(NativeProtocol.WEB_DIALOG_ACTION, this.cd);
        hashMap.put("label", this.ce);
        hashMap.put("value", Long.valueOf(this.cf));
        return zzg.zzj(hashMap);
    }

    public void zza(zzmz com_google_android_gms_internal_zzmz) {
        if (!TextUtils.isEmpty(this.mCategory)) {
            com_google_android_gms_internal_zzmz.zzee(this.mCategory);
        }
        if (!TextUtils.isEmpty(this.cd)) {
            com_google_android_gms_internal_zzmz.zzef(this.cd);
        }
        if (!TextUtils.isEmpty(this.ce)) {
            com_google_android_gms_internal_zzmz.zzeg(this.ce);
        }
        if (this.cf != 0) {
            com_google_android_gms_internal_zzmz.zzq(this.cf);
        }
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzmz) com_google_android_gms_analytics_zzg);
    }

    public void zzee(String str) {
        this.mCategory = str;
    }

    public void zzef(String str) {
        this.cd = str;
    }

    public void zzeg(String str) {
        this.ce = str;
    }

    public void zzq(long j) {
        this.cf = j;
    }
}
