package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzaa;
import java.util.HashMap;
import java.util.Map;

public final class zznb extends zzg<zznb> {
    private String ci;
    private String cj;
    private String ck;
    private String cl;
    private boolean cm;
    private String cn;
    private boolean co;
    private double cp;

    public String getUserId() {
        return this.ck;
    }

    public void setClientId(String str) {
        this.cj = str;
    }

    public void setSampleRate(double d) {
        boolean z = d >= 0.0d && d <= 100.0d;
        zzaa.zzb(z, (Object) "Sample rate must be between 0% and 100%");
        this.cp = d;
    }

    public void setUserId(String str) {
        this.ck = str;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("hitType", this.ci);
        hashMap.put("clientId", this.cj);
        hashMap.put("userId", this.ck);
        hashMap.put("androidAdId", this.cl);
        hashMap.put("AdTargetingEnabled", Boolean.valueOf(this.cm));
        hashMap.put("sessionControl", this.cn);
        hashMap.put("nonInteraction", Boolean.valueOf(this.co));
        hashMap.put("sampleRate", Double.valueOf(this.cp));
        return zzg.zzj(hashMap);
    }

    public void zza(zznb com_google_android_gms_internal_zznb) {
        if (!TextUtils.isEmpty(this.ci)) {
            com_google_android_gms_internal_zznb.zzeh(this.ci);
        }
        if (!TextUtils.isEmpty(this.cj)) {
            com_google_android_gms_internal_zznb.setClientId(this.cj);
        }
        if (!TextUtils.isEmpty(this.ck)) {
            com_google_android_gms_internal_zznb.setUserId(this.ck);
        }
        if (!TextUtils.isEmpty(this.cl)) {
            com_google_android_gms_internal_zznb.zzei(this.cl);
        }
        if (this.cm) {
            com_google_android_gms_internal_zznb.zzas(true);
        }
        if (!TextUtils.isEmpty(this.cn)) {
            com_google_android_gms_internal_zznb.zzej(this.cn);
        }
        if (this.co) {
            com_google_android_gms_internal_zznb.zzat(this.co);
        }
        if (this.cp != 0.0d) {
            com_google_android_gms_internal_zznb.setSampleRate(this.cp);
        }
    }

    public String zzaba() {
        return this.ci;
    }

    public String zzabb() {
        return this.cl;
    }

    public boolean zzabc() {
        return this.cm;
    }

    public String zzabd() {
        return this.cn;
    }

    public boolean zzabe() {
        return this.co;
    }

    public double zzabf() {
        return this.cp;
    }

    public void zzas(boolean z) {
        this.cm = z;
    }

    public void zzat(boolean z) {
        this.co = z;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zznb) com_google_android_gms_analytics_zzg);
    }

    public void zzeh(String str) {
        this.ci = str;
    }

    public void zzei(String str) {
        this.cl = str;
    }

    public void zzej(String str) {
        this.cn = str;
    }

    public String zzze() {
        return this.cj;
    }
}
