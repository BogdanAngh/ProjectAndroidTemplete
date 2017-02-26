package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzmv;
import com.google.android.gms.internal.zzmv.zza;

public class GetSyncInfoRequest implements SafeParcelable {
    public static final Creator<GetSyncInfoRequest> CREATOR;
    private final int zzCY;
    private final String zzMZ;
    private final zzmv zzaml;

    static {
        CREATOR = new zzm();
    }

    GetSyncInfoRequest(int versionCode, IBinder callback, String packageName) {
        this.zzCY = versionCode;
        this.zzaml = zza.zzbG(callback);
        this.zzMZ = packageName;
    }

    public int describeContents() {
        return 0;
    }

    public String getPackageName() {
        return this.zzMZ;
    }

    int getVersionCode() {
        return this.zzCY;
    }

    public String toString() {
        return String.format("GetSyncInfoRequest {%d, %s, %s}", new Object[]{Integer.valueOf(this.zzCY), this.zzaml, this.zzMZ});
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzm.zza(this, parcel, flags);
    }

    public IBinder zzqU() {
        return this.zzaml.asBinder();
    }
}
