package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzmd;

@zzji
public class zzn extends zzj {
    @Nullable
    public zzi zza(Context context, zzmd com_google_android_gms_internal_zzmd, int i, boolean z, zzdz com_google_android_gms_internal_zzdz) {
        if (!zzp(context)) {
            return null;
        }
        return new zzc(context, z, zzh(com_google_android_gms_internal_zzmd), new zzy(context, com_google_android_gms_internal_zzmd.zzxf(), com_google_android_gms_internal_zzmd.getRequestId(), com_google_android_gms_internal_zzdz, com_google_android_gms_internal_zzmd.zzxl()));
    }
}
