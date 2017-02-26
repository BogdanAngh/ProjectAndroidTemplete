package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;

@zzji
public class VideoOptionsParcel extends AbstractSafeParcelable {
    public static final Creator<VideoOptionsParcel> CREATOR;
    public final int versionCode;
    public final boolean zzbck;

    static {
        CREATOR = new zzaq();
    }

    public VideoOptionsParcel(int i, boolean z) {
        this.versionCode = i;
        this.zzbck = z;
    }

    public VideoOptionsParcel(VideoOptions videoOptions) {
        this(1, videoOptions.getStartMuted());
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzaq.zza(this, parcel, i);
    }
}
