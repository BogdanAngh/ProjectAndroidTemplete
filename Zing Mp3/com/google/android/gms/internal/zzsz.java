package com.google.android.gms.internal;

import android.content.Context;

public class zzsz {
    private static zzsz GQ;
    private zzsy GP;

    static {
        GQ = new zzsz();
    }

    public zzsz() {
        this.GP = null;
    }

    public static zzsy zzco(Context context) {
        return GQ.zzcn(context);
    }

    public synchronized zzsy zzcn(Context context) {
        if (this.GP == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.GP = new zzsy(context);
        }
        return this.GP;
    }
}
