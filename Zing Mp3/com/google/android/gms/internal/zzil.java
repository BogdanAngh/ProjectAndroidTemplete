package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.zzig.zza;

@zzji
public final class zzil extends zza {
    private final InAppPurchaseListener zzbbg;

    public zzil(InAppPurchaseListener inAppPurchaseListener) {
        this.zzbbg = inAppPurchaseListener;
    }

    public void zza(zzif com_google_android_gms_internal_zzif) {
        this.zzbbg.onInAppPurchaseRequested(new zzio(com_google_android_gms_internal_zzif));
    }
}
