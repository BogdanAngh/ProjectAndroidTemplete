package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

public final class zzci extends zzg<zzck> {
    private static final zzci zzakx;

    static {
        zzakx = new zzci();
    }

    private zzci() {
        super("com.google.android.gms.ads.adshield.AdShieldCreatorImpl");
    }

    public static zzcj zzb(String str, Context context, boolean z) {
        if (zzc.zzaql().isGooglePlayServicesAvailable(context) == 0) {
            zzcj zzc = zzakx.zzc(str, context, z);
            if (zzc != null) {
                return zzc;
            }
        }
        return new zzch(str, context, z);
    }

    private zzcj zzc(String str, Context context, boolean z) {
        IBinder zza;
        zzd zzac = zze.zzac(context);
        if (z) {
            try {
                zza = ((zzck) zzcr(context)).zza(str, zzac);
            } catch (RemoteException e) {
                return null;
            } catch (zza e2) {
                return null;
            }
        }
        zza = ((zzck) zzcr(context)).zzb(str, zzac);
        return zzcj.zza.zzd(zza);
    }

    protected zzck zzb(IBinder iBinder) {
        return zzck.zza.zze(iBinder);
    }

    protected /* synthetic */ Object zzc(IBinder iBinder) {
        return zzb(iBinder);
    }
}
