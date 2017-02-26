package com.google.android.gms.ads.internal.cache;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;
import java.io.InputStream;

@zzji
public class CacheEntryParcel extends AbstractSafeParcelable {
    public static final Creator<CacheEntryParcel> CREATOR;
    public final int version;
    @Nullable
    private ParcelFileDescriptor zzayc;

    static {
        CREATOR = new zzb();
    }

    public CacheEntryParcel() {
        this(1, null);
    }

    CacheEntryParcel(int i, @Nullable ParcelFileDescriptor parcelFileDescriptor) {
        this.version = i;
        this.zzayc = parcelFileDescriptor;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }

    public synchronized boolean zzju() {
        return this.zzayc != null;
    }

    @Nullable
    public synchronized InputStream zzjv() {
        InputStream inputStream = null;
        synchronized (this) {
            if (this.zzayc != null) {
                inputStream = new AutoCloseInputStream(this.zzayc);
                this.zzayc = null;
            }
        }
        return inputStream;
    }

    synchronized ParcelFileDescriptor zzjw() {
        return this.zzayc;
    }
}
