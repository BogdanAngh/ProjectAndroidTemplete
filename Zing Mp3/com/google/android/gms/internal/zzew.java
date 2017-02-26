package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.internal.zzer.zza;

@zzji
public class zzew extends zza {
    private final OnContentAdLoadedListener zzbpf;

    public zzew(OnContentAdLoadedListener onContentAdLoadedListener) {
        this.zzbpf = onContentAdLoadedListener;
    }

    public void zza(zzem com_google_android_gms_internal_zzem) {
        this.zzbpf.onContentAdLoaded(zzb(com_google_android_gms_internal_zzem));
    }

    zzen zzb(zzem com_google_android_gms_internal_zzem) {
        return new zzen(com_google_android_gms_internal_zzem);
    }
}
