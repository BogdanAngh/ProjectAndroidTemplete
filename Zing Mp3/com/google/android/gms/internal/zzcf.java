package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import android.view.MotionEvent;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

@zzji
public final class zzcf {
    private final zzcj zzaks;

    public zzcf(String str, Context context, boolean z) {
        this.zzaks = zzci.zzb(str, context, z);
    }

    public void zza(MotionEvent motionEvent) throws RemoteException {
        this.zzaks.zzd(zze.zzac(motionEvent));
    }

    public Uri zzc(Uri uri, Context context) throws zzcg, RemoteException {
        zzd zza = this.zzaks.zza(zze.zzac(uri), zze.zzac(context));
        if (zza != null) {
            return (Uri) zze.zzae(zza);
        }
        throw new zzcg();
    }

    public Uri zzd(Uri uri, Context context) throws zzcg, RemoteException {
        zzd zzb = this.zzaks.zzb(zze.zzac(uri), zze.zzac(context));
        if (zzb != null) {
            return (Uri) zze.zzae(zzb);
        }
        throw new zzcg();
    }
}
