package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.List;

public class CheckServerAuthResult extends AbstractSafeParcelable {
    public static final Creator<CheckServerAuthResult> CREATOR;
    final boolean aDs;
    final List<Scope> aDt;
    final int mVersionCode;

    static {
        CREATOR = new zzc();
    }

    CheckServerAuthResult(int i, boolean z, List<Scope> list) {
        this.mVersionCode = i;
        this.aDs = z;
        this.aDt = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
