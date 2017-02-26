package com.google.android.gms.ads.internal.formats;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzeg.zza;
import com.google.android.gms.internal.zzji;

@zzji
public class zzc extends zza {
    private final Uri mUri;
    private final Drawable zzbmw;
    private final double zzbmx;

    public zzc(Drawable drawable, Uri uri, double d) {
        this.zzbmw = drawable;
        this.mUri = uri;
        this.zzbmx = d;
    }

    public double getScale() {
        return this.zzbmx;
    }

    public Uri getUri() throws RemoteException {
        return this.mUri;
    }

    public zzd zzmn() throws RemoteException {
        return zze.zzac(this.zzbmw);
    }
}
