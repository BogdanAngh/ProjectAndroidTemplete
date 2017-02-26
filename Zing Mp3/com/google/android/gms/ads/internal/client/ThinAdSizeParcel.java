package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.internal.zzji;

@zzji
public class ThinAdSizeParcel extends AdSizeParcel {
    public ThinAdSizeParcel(AdSizeParcel adSizeParcel) {
        super(adSizeParcel.versionCode, adSizeParcel.zzazq, adSizeParcel.height, adSizeParcel.heightPixels, adSizeParcel.zzazr, adSizeParcel.width, adSizeParcel.widthPixels, adSizeParcel.zzazs, adSizeParcel.zzazt, adSizeParcel.zzazu, adSizeParcel.zzazv);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, this.versionCode);
        zzb.zza(parcel, 2, this.zzazq, false);
        zzb.zzc(parcel, 3, this.height);
        zzb.zzc(parcel, 6, this.width);
        zzb.zzaj(parcel, zzcs);
    }
}
