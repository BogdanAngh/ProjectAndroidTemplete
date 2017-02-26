package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.badlogic.gdx.graphics.GL20;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.drive.events.CompletionEvent;

public final class FileUploadPreferencesImpl implements SafeParcelable, FileUploadPreferences {
    public static final Creator<FileUploadPreferencesImpl> CREATOR;
    final int zzCY;
    int zzafG;
    int zzafH;
    boolean zzafI;

    static {
        CREATOR = new zzae();
    }

    FileUploadPreferencesImpl(int versionCode, int networkTypePreference, int batteryUsagePreference, boolean allowRoaming) {
        this.zzCY = versionCode;
        this.zzafG = networkTypePreference;
        this.zzafH = batteryUsagePreference;
        this.zzafI = allowRoaming;
    }

    public static boolean zzcD(int i) {
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return true;
            default:
                return false;
        }
    }

    public static boolean zzcE(int i) {
        switch (i) {
            case GL20.GL_DEPTH_BUFFER_BIT /*256*/:
            case FileUploadPreferences.BATTERY_USAGE_CHARGING_ONLY /*257*/:
                return true;
            default:
                return false;
        }
    }

    public int describeContents() {
        return 0;
    }

    public int getBatteryUsagePreference() {
        return !zzcE(this.zzafH) ? 0 : this.zzafH;
    }

    public int getNetworkTypePreference() {
        return !zzcD(this.zzafG) ? 0 : this.zzafG;
    }

    public boolean isRoamingAllowed() {
        return this.zzafI;
    }

    public void setBatteryUsagePreference(int batteryUsagePreference) {
        if (zzcE(batteryUsagePreference)) {
            this.zzafH = batteryUsagePreference;
            return;
        }
        throw new IllegalArgumentException("Invalid battery usage preference value.");
    }

    public void setNetworkTypePreference(int networkTypePreference) {
        if (zzcD(networkTypePreference)) {
            this.zzafG = networkTypePreference;
            return;
        }
        throw new IllegalArgumentException("Invalid data connection preference value.");
    }

    public void setRoamingAllowed(boolean allowRoaming) {
        this.zzafI = allowRoaming;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzae.zza(this, parcel, flags);
    }
}
