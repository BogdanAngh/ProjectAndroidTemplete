package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.ads.internal.client.zzs.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzji;

@zzji
public final class zzd extends zzg<zzt> {
    public zzd() {
        super("com.google.android.gms.ads.AdLoaderBuilderCreatorImpl");
    }

    public zzs zza(Context context, String str, zzgz com_google_android_gms_internal_zzgz) {
        try {
            return zza.zzo(((zzt) zzcr(context)).zza(zze.zzac(context), str, com_google_android_gms_internal_zzgz, 9877000));
        } catch (Throwable e) {
            zzb.zzc("Could not create remote builder for AdLoader.", e);
            return null;
        } catch (Throwable e2) {
            zzb.zzc("Could not create remote builder for AdLoader.", e2);
            return null;
        }
    }

    protected /* synthetic */ Object zzc(IBinder iBinder) {
        return zzj(iBinder);
    }

    protected zzt zzj(IBinder iBinder) {
        return zzt.zza.zzp(iBinder);
    }
}
