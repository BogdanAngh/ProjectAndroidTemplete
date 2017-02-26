package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;

@zzji
public class StringParcel extends AbstractSafeParcelable {
    public static final Creator<StringParcel> CREATOR;
    final int mVersionCode;
    String zzbme;

    static {
        CREATOR = new zzo();
    }

    StringParcel(int i, String str) {
        this.mVersionCode = i;
        this.zzbme = str;
    }

    public StringParcel(String str) {
        this.mVersionCode = 1;
        this.zzbme = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }

    public String zzth() {
        return this.zzbme;
    }
}
