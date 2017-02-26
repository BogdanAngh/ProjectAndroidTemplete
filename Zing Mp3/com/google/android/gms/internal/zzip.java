package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzik.zza;

@zzji
public final class zzip extends zza {
    private final PlayStorePurchaseListener zzbbi;

    public zzip(PlayStorePurchaseListener playStorePurchaseListener) {
        this.zzbbi = playStorePurchaseListener;
    }

    public boolean isValidPurchase(String str) {
        return this.zzbbi.isValidPurchase(str);
    }

    public void zza(zzij com_google_android_gms_internal_zzij) {
        this.zzbbi.onInAppPurchaseFinished(new zzin(com_google_android_gms_internal_zzij));
    }
}
