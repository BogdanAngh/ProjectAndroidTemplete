package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;

@zzji
public abstract class zzjt {
    public abstract void zza(Context context, zzjn com_google_android_gms_internal_zzjn, VersionInfoParcel versionInfoParcel);

    protected void zze(zzjn com_google_android_gms_internal_zzjn) {
        com_google_android_gms_internal_zzjn.zztl();
        if (com_google_android_gms_internal_zzjn.zztj() != null) {
            com_google_android_gms_internal_zzjn.zztj().release();
        }
    }
}
