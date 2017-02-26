package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomClickListener;
import com.google.android.gms.internal.zzes.zza;

@zzji
public class zzex extends zza {
    private final OnCustomClickListener zzbpg;

    public zzex(OnCustomClickListener onCustomClickListener) {
        this.zzbpg = onCustomClickListener;
    }

    public void zza(zzeo com_google_android_gms_internal_zzeo, String str) {
        this.zzbpg.onCustomClick(new zzep(com_google_android_gms_internal_zzeo), str);
    }
}
