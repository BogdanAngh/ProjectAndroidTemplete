package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@zzji
public class zzdy {
    @Nullable
    private final zzdz zzalt;
    private final Map<String, zzdx> zzblw;

    public zzdy(@Nullable zzdz com_google_android_gms_internal_zzdz) {
        this.zzalt = com_google_android_gms_internal_zzdz;
        this.zzblw = new HashMap();
    }

    public void zza(String str, zzdx com_google_android_gms_internal_zzdx) {
        this.zzblw.put(str, com_google_android_gms_internal_zzdx);
    }

    public void zza(String str, String str2, long j) {
        zzdv.zza(this.zzalt, (zzdx) this.zzblw.get(str2), j, str);
        this.zzblw.put(str, zzdv.zza(this.zzalt, j));
    }

    @Nullable
    public zzdz zzly() {
        return this.zzalt;
    }
}
