package com.google.android.gms.ads.internal.request;

import android.content.Context;
import com.google.android.gms.internal.zzav;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzkw;
import java.util.concurrent.Future;

@zzji
public class zza {

    public interface zza {
        void zza(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza);
    }

    public zzkw zza(Context context, com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza, zzav com_google_android_gms_internal_zzav, zza com_google_android_gms_ads_internal_request_zza_zza) {
        zzkw com_google_android_gms_ads_internal_request_zzn = com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza.zzcju.extras.getBundle("sdk_less_server_data") != null ? new zzn(context, com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza, com_google_android_gms_ads_internal_request_zza_zza) : new zzb(context, com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza, com_google_android_gms_internal_zzav, com_google_android_gms_ads_internal_request_zza_zza);
        Future future = (Future) com_google_android_gms_ads_internal_request_zzn.zzrz();
        return com_google_android_gms_ads_internal_request_zzn;
    }
}
