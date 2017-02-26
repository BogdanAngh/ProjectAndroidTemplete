package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.purchase.InAppPurchase;

@zzji
public class zzio implements InAppPurchase {
    private final zzif zzcfp;

    public zzio(zzif com_google_android_gms_internal_zzif) {
        this.zzcfp = com_google_android_gms_internal_zzif;
    }

    public String getProductId() {
        try {
            return this.zzcfp.getProductId();
        } catch (Throwable e) {
            zzb.zzc("Could not forward getProductId to InAppPurchase", e);
            return null;
        }
    }

    public void recordPlayBillingResolution(int i) {
        try {
            this.zzcfp.recordPlayBillingResolution(i);
        } catch (Throwable e) {
            zzb.zzc("Could not forward recordPlayBillingResolution to InAppPurchase", e);
        }
    }

    public void recordResolution(int i) {
        try {
            this.zzcfp.recordResolution(i);
        } catch (Throwable e) {
            zzb.zzc("Could not forward recordResolution to InAppPurchase", e);
        }
    }
}
