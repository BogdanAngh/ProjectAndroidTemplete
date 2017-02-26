package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.zzu.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzji;

@zzji
public class zze extends zzg<zzv> {
    public zze() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    public zzu zza(Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, int i) {
        Throwable e;
        try {
            return zza.zzq(((zzv) zzcr(context)).zza(com.google.android.gms.dynamic.zze.zzac(context), adSizeParcel, str, com_google_android_gms_internal_zzgz, 9877000, i));
        } catch (RemoteException e2) {
            e = e2;
            zzb.zza("Could not create remote AdManager.", e);
            return null;
        } catch (zzg.zza e3) {
            e = e3;
            zzb.zza("Could not create remote AdManager.", e);
            return null;
        }
    }

    protected /* synthetic */ Object zzc(IBinder iBinder) {
        return zzk(iBinder);
    }

    protected zzv zzk(IBinder iBinder) {
        return zzv.zza.zzr(iBinder);
    }
}
