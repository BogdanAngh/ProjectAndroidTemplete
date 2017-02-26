package com.google.android.gms.analytics.internal;

import com.google.android.gms.internal.zzms;

public class zzk extends zzd {
    private final zzms bn;

    zzk(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.bn = new zzms();
    }

    public zzms zzadg() {
        zzacj();
        return this.bn;
    }

    public void zzzc() {
        zzap zzzh = zzzh();
        String zzaae = zzzh.zzaae();
        if (zzaae != null) {
            this.bn.setAppName(zzaae);
        }
        String zzaaf = zzzh.zzaaf();
        if (zzaaf != null) {
            this.bn.setAppVersion(zzaaf);
        }
    }

    protected void zzzy() {
        zzacc().zzzv().zza(this.bn);
        zzzc();
    }
}
