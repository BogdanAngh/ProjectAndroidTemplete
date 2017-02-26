package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzko.zza;

@zzji
public class zzis extends zziq {
    private zzir zzcgu;

    zzis(Context context, zza com_google_android_gms_internal_zzko_zza, zzmd com_google_android_gms_internal_zzmd, zziu.zza com_google_android_gms_internal_zziu_zza) {
        super(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zziu_zza);
    }

    protected void zzrx() {
        int i;
        int i2;
        AdSizeParcel zzeg = this.zzbnz.zzeg();
        if (zzeg.zzazr) {
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            i = displayMetrics.widthPixels;
            i2 = displayMetrics.heightPixels;
        } else {
            i = zzeg.widthPixels;
            i2 = zzeg.heightPixels;
        }
        this.zzcgu = new zzir(this, this.zzbnz, i, i2);
        this.zzbnz.zzxc().zza((zzme.zza) this);
        this.zzcgu.zza(this.zzcgg);
    }

    protected int zzry() {
        if (!this.zzcgu.zzsc()) {
            return !this.zzcgu.zzsd() ? 2 : -2;
        } else {
            zzb.zzdg("Ad-Network indicated no fill with passback URL.");
            return 3;
        }
    }
}
