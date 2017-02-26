package com.google.android.gms.analytics;

import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zze;
import java.util.ArrayList;
import java.util.List;

public abstract class zzh<T extends zzh> {
    private final zzi bg;
    protected final zze bh;
    private final List<zzf> bi;

    protected zzh(zzi com_google_android_gms_analytics_zzi, zze com_google_android_gms_common_util_zze) {
        zzaa.zzy(com_google_android_gms_analytics_zzi);
        this.bg = com_google_android_gms_analytics_zzi;
        this.bi = new ArrayList();
        zze com_google_android_gms_analytics_zze = new zze(this, com_google_android_gms_common_util_zze);
        com_google_android_gms_analytics_zze.zzzs();
        this.bh = com_google_android_gms_analytics_zze;
    }

    protected void zza(zze com_google_android_gms_analytics_zze) {
    }

    protected void zzd(zze com_google_android_gms_analytics_zze) {
        for (zzf zza : this.bi) {
            zza.zza(this, com_google_android_gms_analytics_zze);
        }
    }

    public zze zzyu() {
        zze zzzi = this.bh.zzzi();
        zzd(zzzi);
        return zzzi;
    }

    protected zzi zzzq() {
        return this.bg;
    }

    public zze zzzt() {
        return this.bh;
    }

    public List<zzk> zzzu() {
        return this.bh.zzzk();
    }
}
