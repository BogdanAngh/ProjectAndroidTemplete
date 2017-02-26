package com.google.android.gms.ads.internal.request;

import com.google.android.gms.ads.internal.request.zzl.zza;
import com.google.android.gms.internal.zzji;
import java.lang.ref.WeakReference;

@zzji
public final class zzg extends zza {
    private final WeakReference<zzc.zza> zzckz;

    public zzg(zzc.zza com_google_android_gms_ads_internal_request_zzc_zza) {
        this.zzckz = new WeakReference(com_google_android_gms_ads_internal_request_zzc_zza);
    }

    public void zzb(AdResponseParcel adResponseParcel) {
        zzc.zza com_google_android_gms_ads_internal_request_zzc_zza = (zzc.zza) this.zzckz.get();
        if (com_google_android_gms_ads_internal_request_zzc_zza != null) {
            com_google_android_gms_ads_internal_request_zzc_zza.zzb(adResponseParcel);
        }
    }
}
