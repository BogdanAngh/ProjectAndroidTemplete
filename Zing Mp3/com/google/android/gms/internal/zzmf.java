package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zzs;
import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzmf {
    public zzmd zza(Context context, AdSizeParcel adSizeParcel, boolean z, boolean z2, @Nullable zzav com_google_android_gms_internal_zzav, VersionInfoParcel versionInfoParcel) {
        return zza(context, adSizeParcel, z, z2, com_google_android_gms_internal_zzav, versionInfoParcel, null, null, null);
    }

    public zzmd zza(Context context, AdSizeParcel adSizeParcel, boolean z, boolean z2, @Nullable zzav com_google_android_gms_internal_zzav, VersionInfoParcel versionInfoParcel, zzdz com_google_android_gms_internal_zzdz, zzs com_google_android_gms_ads_internal_zzs, zzd com_google_android_gms_ads_internal_zzd) {
        zzmd com_google_android_gms_internal_zzmg = new zzmg(zzmh.zzb(context, adSizeParcel, z, z2, com_google_android_gms_internal_zzav, versionInfoParcel, com_google_android_gms_internal_zzdz, com_google_android_gms_ads_internal_zzs, com_google_android_gms_ads_internal_zzd));
        com_google_android_gms_internal_zzmg.setWebViewClient(zzu.zzgo().zzb(com_google_android_gms_internal_zzmg, z2));
        com_google_android_gms_internal_zzmg.setWebChromeClient(zzu.zzgo().zzn(com_google_android_gms_internal_zzmg));
        return com_google_android_gms_internal_zzmg;
    }
}
