package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.example.games.basegameutils.GameHelper;

public class zzlv {
    private static int zzakU;

    static {
        zzakU = -1;
    }

    public static boolean zzam(Context context) {
        return zzap(context) == 3;
    }

    private static int zzan(Context context) {
        return ((zzao(context) % GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) / 100) + 5;
    }

    private static int zzao(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.w("Fitness", "Could not find package info for Google Play Services");
            return -1;
        }
    }

    public static int zzap(Context context) {
        if (zzakU == -1) {
            switch (zzan(context)) {
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                case ConnectionsStatusCodes.STATUS_ERROR /*13*/:
                    zzakU = 0;
                    break;
                case Place.TYPE_BEAUTY_SALON /*10*/:
                    zzakU = 3;
                    break;
                default:
                    zzakU = zzaq(context) ? 1 : 2;
                    break;
            }
        }
        return zzakU;
    }

    private static boolean zzaq(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getPhoneType() != 0;
    }
}
