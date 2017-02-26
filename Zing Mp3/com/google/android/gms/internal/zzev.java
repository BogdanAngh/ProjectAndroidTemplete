package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;
import com.google.android.gms.internal.zzeq.zza;

@zzji
public class zzev extends zza {
    private final OnAppInstallAdLoadedListener zzbpe;

    public zzev(OnAppInstallAdLoadedListener onAppInstallAdLoadedListener) {
        this.zzbpe = onAppInstallAdLoadedListener;
    }

    public void zza(zzek com_google_android_gms_internal_zzek) {
        this.zzbpe.onAppInstallAdLoaded(zzb(com_google_android_gms_internal_zzek));
    }

    zzel zzb(zzek com_google_android_gms_internal_zzek) {
        return new zzel(com_google_android_gms_internal_zzek);
    }
}
