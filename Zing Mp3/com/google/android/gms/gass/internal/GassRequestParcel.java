package com.google.android.gms.gass.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public final class GassRequestParcel extends AbstractSafeParcelable {
    public static final Creator<GassRequestParcel> CREATOR;
    public final String agH;
    public final String packageName;
    public final int versionCode;

    static {
        CREATOR = new zzc();
    }

    GassRequestParcel(int i, String str, String str2) {
        this.versionCode = i;
        this.packageName = str;
        this.agH = str2;
    }

    public GassRequestParcel(String str, String str2) {
        this(1, str, str2);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
