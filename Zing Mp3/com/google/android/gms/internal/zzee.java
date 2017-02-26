package com.google.android.gms.internal;

import com.google.android.gms.ads.doubleclick.OnCustomRenderedAdLoadedListener;
import com.google.android.gms.internal.zzed.zza;

@zzji
public final class zzee extends zza {
    private final OnCustomRenderedAdLoadedListener zzbbh;

    public zzee(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.zzbbh = onCustomRenderedAdLoadedListener;
    }

    public void zza(zzec com_google_android_gms_internal_zzec) {
        this.zzbbh.onCustomRenderedAdLoaded(new zzeb(com_google_android_gms_internal_zzec));
    }
}
