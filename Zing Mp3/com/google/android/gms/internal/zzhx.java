package com.google.android.gms.internal;

import android.app.Activity;
import android.os.IBinder;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzhz.zza;

@zzji
public final class zzhx extends zzg<zzhz> {
    public zzhx() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    protected zzhz zzas(IBinder iBinder) {
        return zza.zzau(iBinder);
    }

    protected /* synthetic */ Object zzc(IBinder iBinder) {
        return zzas(iBinder);
    }

    public zzhy zzf(Activity activity) {
        try {
            return zzhy.zza.zzat(((zzhz) zzcr(activity)).zzo(zze.zzac(activity)));
        } catch (Throwable e) {
            zzb.zzc("Could not create remote AdOverlay.", e);
            return null;
        } catch (Throwable e2) {
            zzb.zzc("Could not create remote AdOverlay.", e2);
            return null;
        }
    }
}
