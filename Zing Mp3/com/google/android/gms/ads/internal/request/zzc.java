package com.google.android.gms.ads.internal.request;

import android.content.Context;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.internal.zzlw;

@zzji
public final class zzc {

    public interface zza {
        void zzb(AdResponseParcel adResponseParcel);
    }

    interface zzb {
        boolean zza(VersionInfoParcel versionInfoParcel);
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzc.1 */
    class C10991 implements zzb {
        final /* synthetic */ Context zzang;

        C10991(Context context) {
            this.zzang = context;
        }

        public boolean zza(VersionInfoParcel versionInfoParcel) {
            return versionInfoParcel.zzcyc || (zzi.zzcj(this.zzang) && !((Boolean) zzdr.zzbel.get()).booleanValue());
        }
    }

    public static zzld zza(Context context, VersionInfoParcel versionInfoParcel, zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza) {
        return zza(context, versionInfoParcel, com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza, new C10991(context));
    }

    static zzld zza(Context context, VersionInfoParcel versionInfoParcel, zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza, zzb com_google_android_gms_ads_internal_request_zzc_zzb) {
        return com_google_android_gms_ads_internal_request_zzc_zzb.zza(versionInfoParcel) ? zza(context, com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza) : zzb(context, versionInfoParcel, com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
    }

    private static zzld zza(Context context, zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza) {
        com.google.android.gms.ads.internal.util.client.zzb.zzdg("Fetching ad response from local ad request service.");
        zzld com_google_android_gms_ads_internal_request_zzd_zza = new com.google.android.gms.ads.internal.request.zzd.zza(context, com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
        Void voidR = (Void) com_google_android_gms_ads_internal_request_zzd_zza.zzrz();
        return com_google_android_gms_ads_internal_request_zzd_zza;
    }

    private static zzld zzb(Context context, VersionInfoParcel versionInfoParcel, zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, zza com_google_android_gms_ads_internal_request_zzc_zza) {
        com.google.android.gms.ads.internal.util.client.zzb.zzdg("Fetching ad response from remote ad request service.");
        if (zzm.zzkr().zzap(context)) {
            return new com.google.android.gms.ads.internal.request.zzd.zzb(context, versionInfoParcel, com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzdi("Failed to connect to remote ad request service.");
        return null;
    }
}
