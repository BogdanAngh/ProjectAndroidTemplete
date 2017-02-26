package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class GroundOverlayOptionsParcelable implements SafeParcelable {
    public static final zzc CREATOR;
    private final int zzCY;
    private BitmapDescriptorParcelable zzaDP;

    static {
        CREATOR = new zzc();
    }

    public GroundOverlayOptionsParcelable() {
        this.zzCY = 1;
    }

    GroundOverlayOptionsParcelable(int versionCode, BitmapDescriptorParcelable parcelableImage) {
        this.zzCY = versionCode;
        this.zzaDP = parcelableImage;
    }

    public int describeContents() {
        return 0;
    }

    int getVersionCode() {
        return this.zzCY;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzc.zza(this, out, flags);
    }

    public BitmapDescriptorParcelable zzvM() {
        return this.zzaDP;
    }
}
