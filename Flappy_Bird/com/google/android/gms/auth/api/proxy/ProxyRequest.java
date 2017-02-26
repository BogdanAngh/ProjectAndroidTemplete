package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ProxyRequest implements SafeParcelable {
    public static final Creator<ProxyRequest> CREATOR;
    public static final int zzPh;
    public static final int zzPi;
    public static final int zzPj;
    public static final int zzPk;
    public static final int zzPl;
    public static final int zzPm;
    public static final int zzPn;
    public static final int zzPo;
    public static final int zzPp;
    final int versionCode;
    public final int zzPq;
    public final long zzPr;
    public final byte[] zzPs;
    Bundle zzPt;
    public final String zzzf;

    static {
        CREATOR = new zzb();
        zzPh = 0;
        zzPi = 1;
        zzPj = 2;
        zzPk = 3;
        zzPl = 4;
        zzPm = 5;
        zzPn = 6;
        zzPo = 7;
        zzPp = 7;
    }

    ProxyRequest(int version, String googleUrl, int httpMethod, long timeoutMillis, byte[] body, Bundle headers) {
        this.versionCode = version;
        this.zzzf = googleUrl;
        this.zzPq = httpMethod;
        this.zzPr = timeoutMillis;
        this.zzPs = body;
        this.zzPt = headers;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ProxyRequest[ url: " + this.zzzf + ", method: " + this.zzPq + " ]";
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzb.zza(this, parcel, flags);
    }
}
