package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;
import java.util.Map;

public final class zzms extends zzg<zzms> {
    private String bN;
    private String bO;
    private String bP;
    private String zzctj;

    public void setAppId(String str) {
        this.zzctj = str;
    }

    public void setAppInstallerId(String str) {
        this.bP = str;
    }

    public void setAppName(String str) {
        this.bN = str;
    }

    public void setAppVersion(String str) {
        this.bO = str;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("appName", this.bN);
        hashMap.put("appVersion", this.bO);
        hashMap.put("appId", this.zzctj);
        hashMap.put("appInstallerId", this.bP);
        return zzg.zzj(hashMap);
    }

    public void zza(zzms com_google_android_gms_internal_zzms) {
        if (!TextUtils.isEmpty(this.bN)) {
            com_google_android_gms_internal_zzms.setAppName(this.bN);
        }
        if (!TextUtils.isEmpty(this.bO)) {
            com_google_android_gms_internal_zzms.setAppVersion(this.bO);
        }
        if (!TextUtils.isEmpty(this.zzctj)) {
            com_google_android_gms_internal_zzms.setAppId(this.zzctj);
        }
        if (!TextUtils.isEmpty(this.bP)) {
            com_google_android_gms_internal_zzms.setAppInstallerId(this.bP);
        }
    }

    public String zzaae() {
        return this.bN;
    }

    public String zzaaf() {
        return this.bO;
    }

    public String zzaag() {
        return this.bP;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzms) com_google_android_gms_analytics_zzg);
    }

    public String zzup() {
        return this.zzctj;
    }
}
