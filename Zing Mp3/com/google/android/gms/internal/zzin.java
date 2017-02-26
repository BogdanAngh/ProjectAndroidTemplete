package com.google.android.gms.internal;

import android.content.Intent;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.purchase.InAppPurchaseResult;

@zzji
public class zzin implements InAppPurchaseResult {
    private final zzij zzcgd;

    public zzin(zzij com_google_android_gms_internal_zzij) {
        this.zzcgd = com_google_android_gms_internal_zzij;
    }

    public void finishPurchase() {
        try {
            this.zzcgd.finishPurchase();
        } catch (Throwable e) {
            zzb.zzc("Could not forward finishPurchase to InAppPurchaseResult", e);
        }
    }

    public String getProductId() {
        try {
            return this.zzcgd.getProductId();
        } catch (Throwable e) {
            zzb.zzc("Could not forward getProductId to InAppPurchaseResult", e);
            return null;
        }
    }

    public Intent getPurchaseData() {
        try {
            return this.zzcgd.getPurchaseData();
        } catch (Throwable e) {
            zzb.zzc("Could not forward getPurchaseData to InAppPurchaseResult", e);
            return null;
        }
    }

    public int getResultCode() {
        try {
            return this.zzcgd.getResultCode();
        } catch (Throwable e) {
            zzb.zzc("Could not forward getPurchaseData to InAppPurchaseResult", e);
            return 0;
        }
    }

    public boolean isVerified() {
        try {
            return this.zzcgd.isVerified();
        } catch (Throwable e) {
            zzb.zzc("Could not forward isVerified to InAppPurchaseResult", e);
            return false;
        }
    }
}
