package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class Operator implements SafeParcelable {
    public static final Creator<Operator> CREATOR;
    public static final Operator zzaih;
    public static final Operator zzaii;
    public static final Operator zzaij;
    public static final Operator zzaik;
    public static final Operator zzail;
    public static final Operator zzaim;
    public static final Operator zzain;
    public static final Operator zzaio;
    public static final Operator zzaip;
    final String mTag;
    final int zzCY;

    static {
        CREATOR = new zzn();
        zzaih = new Operator("=");
        zzaii = new Operator("<");
        zzaij = new Operator("<=");
        zzaik = new Operator(">");
        zzail = new Operator(">=");
        zzaim = new Operator("and");
        zzain = new Operator("or");
        zzaio = new Operator("not");
        zzaip = new Operator("contains");
    }

    Operator(int versionCode, String tag) {
        this.zzCY = versionCode;
        this.mTag = tag;
    }

    private Operator(String tag) {
        this(1, tag);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Operator operator = (Operator) obj;
        return this.mTag == null ? operator.mTag == null : this.mTag.equals(operator.mTag);
    }

    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        return (this.mTag == null ? 0 : this.mTag.hashCode()) + 31;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzn.zza(this, out, flags);
    }
}
