package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzej.zza;

@zzji
public class zzeu extends zzg<zzej> {
    public zzeu() {
        super("com.google.android.gms.ads.NativeAdViewDelegateCreatorImpl");
    }

    protected zzej zzal(IBinder iBinder) {
        return zza.zzad(iBinder);
    }

    public zzei zzb(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        Throwable e;
        try {
            return zzei.zza.zzac(((zzej) zzcr(context)).zza(zze.zzac(context), zze.zzac(frameLayout), zze.zzac(frameLayout2), 9877000));
        } catch (RemoteException e2) {
            e = e2;
            zzb.zzc("Could not create remote NativeAdViewDelegate.", e);
            return null;
        } catch (zzg.zza e3) {
            e = e3;
            zzb.zzc("Could not create remote NativeAdViewDelegate.", e);
            return null;
        }
    }

    protected /* synthetic */ Object zzc(IBinder iBinder) {
        return zzal(iBinder);
    }
}
