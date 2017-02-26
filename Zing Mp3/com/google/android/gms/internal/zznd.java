package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;
import java.util.Map;

public final class zznd extends zzg<zznd> {
    public String cd;
    public String cx;
    public String cy;

    public String getAction() {
        return this.cd;
    }

    public String getTarget() {
        return this.cy;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("network", this.cx);
        hashMap.put(NativeProtocol.WEB_DIALOG_ACTION, this.cd);
        hashMap.put("target", this.cy);
        return zzg.zzj(hashMap);
    }

    public void zza(zznd com_google_android_gms_internal_zznd) {
        if (!TextUtils.isEmpty(this.cx)) {
            com_google_android_gms_internal_zznd.zzem(this.cx);
        }
        if (!TextUtils.isEmpty(this.cd)) {
            com_google_android_gms_internal_zznd.zzef(this.cd);
        }
        if (!TextUtils.isEmpty(this.cy)) {
            com_google_android_gms_internal_zznd.zzen(this.cy);
        }
    }

    public String zzabl() {
        return this.cx;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zznd) com_google_android_gms_analytics_zzg);
    }

    public void zzef(String str) {
        this.cd = str;
    }

    public void zzem(String str) {
        this.cx = str;
    }

    public void zzen(String str) {
        this.cy = str;
    }
}
