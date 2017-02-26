package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.regex.Pattern;

public final class zzkz {
    private static Pattern zzacJ;

    static {
        zzacJ = null;
    }

    public static boolean zzai(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
    }

    public static int zzbN(int i) {
        return i / GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE;
    }

    public static int zzbO(int i) {
        return (i % GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) / 100;
    }

    public static boolean zzbP(int i) {
        return zzbO(i) == 3;
    }
}
