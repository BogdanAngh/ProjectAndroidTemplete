package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OnLoadRealtimeResponse implements SafeParcelable {
    public static final Creator<OnLoadRealtimeResponse> CREATOR;
    final int zzCY;
    final boolean zzpb;

    static {
        CREATOR = new zzbe();
    }

    OnLoadRealtimeResponse(int versionCode, boolean isInitialized) {
        this.zzCY = versionCode;
        this.zzpb = isInitialized;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzbe.zza(this, dest, flags);
    }
}
