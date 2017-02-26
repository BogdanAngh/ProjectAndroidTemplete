package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener;
import com.google.android.gms.internal.zzet.zza;

@zzji
public class zzey extends zza {
    private final OnCustomTemplateAdLoadedListener zzbph;

    public zzey(OnCustomTemplateAdLoadedListener onCustomTemplateAdLoadedListener) {
        this.zzbph = onCustomTemplateAdLoadedListener;
    }

    public void zza(zzeo com_google_android_gms_internal_zzeo) {
        this.zzbph.onCustomTemplateAdLoaded(new zzep(com_google_android_gms_internal_zzeo));
    }
}
