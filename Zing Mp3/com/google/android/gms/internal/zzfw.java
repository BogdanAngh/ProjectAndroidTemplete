package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zzl;

@zzji
public class zzfw {
    private final Context mContext;
    private final zzd zzamb;
    private final zzgz zzamf;
    private final VersionInfoParcel zzanu;

    zzfw(Context context, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        this.mContext = context;
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.zzanu = versionInfoParcel;
        this.zzamb = com_google_android_gms_ads_internal_zzd;
    }

    public Context getApplicationContext() {
        return this.mContext.getApplicationContext();
    }

    public zzl zzbj(String str) {
        return new zzl(this.mContext, new AdSizeParcel(), str, this.zzamf, this.zzanu, this.zzamb);
    }

    public zzl zzbk(String str) {
        return new zzl(this.mContext.getApplicationContext(), new AdSizeParcel(), str, this.zzamf, this.zzanu, this.zzamb);
    }

    public zzfw zznl() {
        return new zzfw(getApplicationContext(), this.zzamf, this.zzanu, this.zzamb);
    }
}
