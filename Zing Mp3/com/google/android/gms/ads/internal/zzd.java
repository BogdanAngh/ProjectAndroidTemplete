package com.google.android.gms.ads.internal;

import com.google.android.gms.ads.internal.overlay.zzj;
import com.google.android.gms.ads.internal.overlay.zzm;
import com.google.android.gms.ads.internal.overlay.zzn;
import com.google.android.gms.ads.internal.overlay.zzt;
import com.google.android.gms.ads.internal.safebrowsing.zza;
import com.google.android.gms.internal.zzfb;
import com.google.android.gms.internal.zzfu;
import com.google.android.gms.internal.zzji;

@zzji
public class zzd {
    public final zzfu zzamp;
    public final zzj zzamq;
    public final zzm zzamr;
    public final com.google.android.gms.ads.internal.safebrowsing.zzd zzams;

    public zzd(zzfu com_google_android_gms_internal_zzfu, zzj com_google_android_gms_ads_internal_overlay_zzj, zzm com_google_android_gms_ads_internal_overlay_zzm, com.google.android.gms.ads.internal.safebrowsing.zzd com_google_android_gms_ads_internal_safebrowsing_zzd) {
        this.zzamp = com_google_android_gms_internal_zzfu;
        this.zzamq = com_google_android_gms_ads_internal_overlay_zzj;
        this.zzamr = com_google_android_gms_ads_internal_overlay_zzm;
        this.zzams = com_google_android_gms_ads_internal_safebrowsing_zzd;
    }

    public static zzd zzfd() {
        return new zzd(new zzfb(), new zzn(), new zzt(), new zza());
    }
}
