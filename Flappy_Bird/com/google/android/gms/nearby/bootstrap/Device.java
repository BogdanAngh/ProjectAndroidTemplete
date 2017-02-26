package com.google.android.gms.nearby.bootstrap;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;

public class Device implements SafeParcelable {
    public static final Creator<Device> CREATOR;
    private final String description;
    private final String name;
    final int versionCode;
    private final String zzaFf;
    private final byte zzaFg;

    static {
        CREATOR = new zzb();
    }

    Device(int versionCode, String name, String description, String deviceId, byte deviceType) {
        this.versionCode = versionCode;
        this.name = zzu.zzcj(name);
        this.description = (String) zzu.zzu(description);
        this.zzaFf = (String) zzu.zzu(deviceId);
        zzu.zzb(deviceType <= 3, (Object) "Unknown device type");
        this.zzaFg = deviceType;
    }

    public int describeContents() {
        return 0;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDeviceId() {
        return this.zzaFf;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name + ": " + this.description + "[" + this.zzaFg + "]";
    }

    public void writeToParcel(Parcel out, int flags) {
        zzb.zza(this, out, flags);
    }

    public byte zzwK() {
        return this.zzaFg;
    }
}
