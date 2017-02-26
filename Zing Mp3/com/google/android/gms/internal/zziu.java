package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzq;
import com.google.android.gms.common.util.zzs;

@zzji
public class zziu {

    public interface zza {
        void zzb(zzko com_google_android_gms_internal_zzko);
    }

    public zzld zza(Context context, com.google.android.gms.ads.internal.zza com_google_android_gms_ads_internal_zza, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzav com_google_android_gms_internal_zzav, @Nullable zzmd com_google_android_gms_internal_zzmd, zzgz com_google_android_gms_internal_zzgz, zza com_google_android_gms_internal_zziu_zza, zzdz com_google_android_gms_internal_zzdz) {
        AdResponseParcel adResponseParcel = com_google_android_gms_internal_zzko_zza.zzcsu;
        zzld com_google_android_gms_internal_zziy = adResponseParcel.zzclb ? new zziy(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzgz, com_google_android_gms_internal_zziu_zza, com_google_android_gms_internal_zzdz, com_google_android_gms_internal_zzmd) : (adResponseParcel.zzazt || (com_google_android_gms_ads_internal_zza instanceof zzq)) ? (adResponseParcel.zzazt && (com_google_android_gms_ads_internal_zza instanceof zzq)) ? new zziz(context, (zzq) com_google_android_gms_ads_internal_zza, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzav, com_google_android_gms_internal_zziu_zza, com_google_android_gms_internal_zzdz) : new zziw(com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zziu_zza) : (((Boolean) zzdr.zzbet.get()).booleanValue() && adResponseParcel.zzclh) ? new zzis(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zziu_zza) : (((Boolean) zzdr.zzbfm.get()).booleanValue() && zzs.zzayu() && !zzs.zzayw() && com_google_android_gms_internal_zzmd != null && com_google_android_gms_internal_zzmd.zzeg().zzazr) ? new zzix(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zziu_zza) : new zziv(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zziu_zza);
        String str = "AdRenderer: ";
        String valueOf = String.valueOf(com_google_android_gms_internal_zziy.getClass().getName());
        zzb.zzdg(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        com_google_android_gms_internal_zziy.zzrz();
        return com_google_android_gms_internal_zziy;
    }

    public zzld zza(Context context, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzkb com_google_android_gms_internal_zzkb) {
        zzld com_google_android_gms_internal_zzki = new zzki(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzkb);
        String str = "AdRenderer: ";
        String valueOf = String.valueOf(com_google_android_gms_internal_zzki.getClass().getName());
        zzb.zzdg(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        com_google_android_gms_internal_zzki.zzrz();
        return com_google_android_gms_internal_zzki;
    }
}
