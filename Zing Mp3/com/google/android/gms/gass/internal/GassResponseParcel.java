package com.google.android.gms.gass.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzaf.zza;
import com.google.android.gms.internal.zzasa;

public final class GassResponseParcel extends AbstractSafeParcelable {
    public static final Creator<GassResponseParcel> CREATOR;
    private zza agI;
    private byte[] agJ;
    public final int versionCode;

    static {
        CREATOR = new zzd();
    }

    GassResponseParcel(int i, byte[] bArr) {
        this.versionCode = i;
        this.agI = null;
        this.agJ = bArr;
        zzazw();
    }

    private void zzazu() {
        if (!zzazv()) {
            try {
                this.agI = zza.zzd(this.agJ);
                this.agJ = null;
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
        zzazw();
    }

    private boolean zzazv() {
        return this.agI != null;
    }

    private void zzazw() {
        if (this.agI == null && this.agJ != null) {
            return;
        }
        if (this.agI != null && this.agJ == null) {
            return;
        }
        if (this.agI != null && this.agJ != null) {
            throw new IllegalStateException("Invalid internal representation - full");
        } else if (this.agI == null && this.agJ == null) {
            throw new IllegalStateException("Invalid internal representation - empty");
        } else {
            throw new IllegalStateException("Impossible");
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzd.zza(this, parcel, i);
    }

    public byte[] zzbnn() {
        return this.agJ != null ? this.agJ : zzasa.zzf(this.agI);
    }

    public zza zzbno() {
        zzazu();
        return this.agI;
    }
}
