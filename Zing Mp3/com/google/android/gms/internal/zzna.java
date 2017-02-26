package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;
import java.util.Map;

public final class zzna extends zzg<zzna> {
    public String cg;
    public boolean ch;

    public String getDescription() {
        return this.cg;
    }

    public void setDescription(String str) {
        this.cg = str;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DESCRIPTION, this.cg);
        hashMap.put("fatal", Boolean.valueOf(this.ch));
        return zzg.zzj(hashMap);
    }

    public void zza(zzna com_google_android_gms_internal_zzna) {
        if (!TextUtils.isEmpty(this.cg)) {
            com_google_android_gms_internal_zzna.setDescription(this.cg);
        }
        if (this.ch) {
            com_google_android_gms_internal_zzna.zzar(this.ch);
        }
    }

    public boolean zzaaz() {
        return this.ch;
    }

    public void zzar(boolean z) {
        this.ch = z;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzna) com_google_android_gms_analytics_zzg);
    }
}
