package com.google.android.gms.internal;

import android.app.Activity;
import android.os.IBinder;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzii.zza;

@zzji
public final class zzim extends zzg<zzii> {
    public zzim() {
        super("com.google.android.gms.ads.InAppPurchaseManagerCreatorImpl");
    }

    protected zzii zzbc(IBinder iBinder) {
        return zza.zzaz(iBinder);
    }

    protected /* synthetic */ Object zzc(IBinder iBinder) {
        return zzbc(iBinder);
    }

    public zzih zzg(Activity activity) {
        try {
            return zzih.zza.zzay(((zzii) zzcr(activity)).zzp(zze.zzac(activity)));
        } catch (Throwable e) {
            zzb.zzc("Could not create remote InAppPurchaseManager.", e);
            return null;
        } catch (Throwable e2) {
            zzb.zzc("Could not create remote InAppPurchaseManager.", e2);
            return null;
        }
    }
}
