package com.google.android.gms.ads.internal.reward.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;

@zzji
public final class RewardedVideoAdRequestParcel extends AbstractSafeParcelable {
    public static final Creator<RewardedVideoAdRequestParcel> CREATOR;
    public final int versionCode;
    public final String zzarg;
    public final AdRequestParcel zzcju;

    static {
        CREATOR = new zzh();
    }

    public RewardedVideoAdRequestParcel(int i, AdRequestParcel adRequestParcel, String str) {
        this.versionCode = i;
        this.zzcju = adRequestParcel;
        this.zzarg = str;
    }

    public RewardedVideoAdRequestParcel(AdRequestParcel adRequestParcel, String str) {
        this(1, adRequestParcel, str);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.zza(this, parcel, i);
    }
}
