package com.google.android.gms.ads.internal.util.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;

@zzji
public final class VersionInfoParcel extends AbstractSafeParcelable {
    public static final Creator<VersionInfoParcel> CREATOR;
    public final int versionCode;
    public int zzcya;
    public int zzcyb;
    public boolean zzcyc;
    public String zzda;

    static {
        CREATOR = new zzd();
    }

    public VersionInfoParcel(int i, int i2, boolean z) {
        this(i, i2, z, false);
    }

    public VersionInfoParcel(int i, int i2, boolean z, boolean z2) {
        String valueOf = String.valueOf("afma-sdk-a-v");
        String str = z ? AppEventsConstants.EVENT_PARAM_VALUE_NO : z2 ? "2" : AppEventsConstants.EVENT_PARAM_VALUE_YES;
        this(1, new StringBuilder((String.valueOf(valueOf).length() + 24) + String.valueOf(str).length()).append(valueOf).append(i).append(".").append(i2).append(".").append(str).toString(), i, i2, z);
    }

    VersionInfoParcel(int i, String str, int i2, int i3, boolean z) {
        this.versionCode = i;
        this.zzda = str;
        this.zzcya = i2;
        this.zzcyb = i3;
        this.zzcyc = z;
    }

    public static VersionInfoParcel zzwr() {
        return new VersionInfoParcel(9877208, 9877208, true);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzd.zza(this, parcel, i);
    }
}
