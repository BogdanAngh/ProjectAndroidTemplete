package com.google.android.gms.ads.internal.cache;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.ads.internal.cache.zzf.zza;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zze.zzb;
import com.google.android.gms.internal.zzji;

@zzji
public class zzc extends zze<zzf> {
    zzc(Context context, Looper looper, zzb com_google_android_gms_common_internal_zze_zzb, com.google.android.gms.common.internal.zze.zzc com_google_android_gms_common_internal_zze_zzc) {
        super(context, looper, 123, com_google_android_gms_common_internal_zze_zzb, com_google_android_gms_common_internal_zze_zzc, null);
    }

    protected zzf zzg(IBinder iBinder) {
        return zza.zzi(iBinder);
    }

    protected /* synthetic */ IInterface zzh(IBinder iBinder) {
        return zzg(iBinder);
    }

    protected String zzjx() {
        return "com.google.android.gms.ads.service.CACHE";
    }

    protected String zzjy() {
        return "com.google.android.gms.ads.internal.cache.ICacheService";
    }

    public zzf zzjz() throws DeadObjectException {
        return (zzf) super.zzavg();
    }
}
