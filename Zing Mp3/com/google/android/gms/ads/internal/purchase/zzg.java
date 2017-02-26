package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzij.zza;
import com.google.android.gms.internal.zzji;

@zzji
public final class zzg extends zza implements ServiceConnection {
    private Context mContext;
    private int mResultCode;
    zzb zzcfg;
    private String zzcfm;
    private zzf zzcfq;
    private boolean zzcfw;
    private Intent zzcfx;

    public zzg(Context context, String str, boolean z, int i, Intent intent, zzf com_google_android_gms_ads_internal_purchase_zzf) {
        this.zzcfw = false;
        this.zzcfm = str;
        this.mResultCode = i;
        this.zzcfx = intent;
        this.zzcfw = z;
        this.mContext = context;
        this.zzcfq = com_google_android_gms_ads_internal_purchase_zzf;
    }

    public void finishPurchase() {
        int zzd = zzu.zzha().zzd(this.zzcfx);
        if (this.mResultCode == -1 && zzd == 0) {
            this.zzcfg = new zzb(this.mContext);
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
            com.google.android.gms.common.stats.zza.zzaxr().zza(this.mContext, intent, (ServiceConnection) this, 1);
        }
    }

    public String getProductId() {
        return this.zzcfm;
    }

    public Intent getPurchaseData() {
        return this.zzcfx;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public boolean isVerified() {
        return this.zzcfw;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzb.zzdh("In-app billing service connected.");
        this.zzcfg.zzav(iBinder);
        String zzch = zzu.zzha().zzch(zzu.zzha().zze(this.zzcfx));
        if (zzch != null) {
            if (this.zzcfg.zzl(this.mContext.getPackageName(), zzch) == 0) {
                zzh.zzq(this.mContext).zza(this.zzcfq);
            }
            com.google.android.gms.common.stats.zza.zzaxr().zza(this.mContext, this);
            this.zzcfg.destroy();
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        zzb.zzdh("In-app billing service disconnected.");
        this.zzcfg.destroy();
    }
}
