package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

public abstract class zzk {
    private static final Object zzaaJ;
    private static zzk zzaaK;

    static {
        zzaaJ = new Object();
    }

    public static zzk zzah(Context context) {
        synchronized (zzaaJ) {
            if (zzaaK == null) {
                zzaaK = new zzl(context.getApplicationContext());
            }
        }
        return zzaaK;
    }

    public abstract boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str);

    public abstract boolean zza(String str, ServiceConnection serviceConnection, String str2);

    public abstract void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str);

    public abstract void zzb(String str, ServiceConnection serviceConnection, String str2);
}
