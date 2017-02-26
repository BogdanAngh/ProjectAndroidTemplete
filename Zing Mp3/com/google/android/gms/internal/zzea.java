package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.ads.internal.zzh;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzec.zza;

@zzji
public final class zzea extends zza {
    private final zzh zzbmc;
    @Nullable
    private final String zzbmd;
    private final String zzbme;

    public zzea(zzh com_google_android_gms_ads_internal_zzh, @Nullable String str, String str2) {
        this.zzbmc = com_google_android_gms_ads_internal_zzh;
        this.zzbmd = str;
        this.zzbme = str2;
    }

    public String getContent() {
        return this.zzbme;
    }

    public void recordClick() {
        this.zzbmc.zzfa();
    }

    public void recordImpression() {
        this.zzbmc.zzfb();
    }

    public void zzi(@Nullable zzd com_google_android_gms_dynamic_zzd) {
        if (com_google_android_gms_dynamic_zzd != null) {
            this.zzbmc.zzc((View) zze.zzae(com_google_android_gms_dynamic_zzd));
        }
    }

    public String zzme() {
        return this.zzbmd;
    }
}
