package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class UserDataType implements SafeParcelable {
    public static final zzn CREATOR;
    public static final UserDataType zzaAa;
    public static final UserDataType zzaAb;
    public static final Set<UserDataType> zzaAc;
    public static final UserDataType zzazZ;
    final int zzCY;
    final String zzEl;
    final int zzaAd;

    static {
        zzazZ = zzy("test_type", 1);
        zzaAa = zzy("labeled_place", 6);
        zzaAb = zzy("here_content", 7);
        zzaAc = Collections.unmodifiableSet(new HashSet(Arrays.asList(new UserDataType[]{zzazZ, zzaAa, zzaAb})));
        CREATOR = new zzn();
    }

    UserDataType(int versionCode, String type, int enumValue) {
        zzu.zzcj(type);
        this.zzCY = versionCode;
        this.zzEl = type;
        this.zzaAd = enumValue;
    }

    private static UserDataType zzy(String str, int i) {
        return new UserDataType(0, str, i);
    }

    public int describeContents() {
        zzn com_google_android_gms_location_places_zzn = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UserDataType)) {
            return false;
        }
        UserDataType userDataType = (UserDataType) object;
        return this.zzEl.equals(userDataType.zzEl) && this.zzaAd == userDataType.zzaAd;
    }

    public int hashCode() {
        return this.zzEl.hashCode();
    }

    public String toString() {
        return this.zzEl;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzn com_google_android_gms_location_places_zzn = CREATOR;
        zzn.zza(this, parcel, flags);
    }
}
