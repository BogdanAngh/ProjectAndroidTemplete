package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public class zzg {
    public static boolean zza(int i, DriveId driveId) {
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return driveId != null;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return driveId == null;
            case Place.TYPE_ART_GALLERY /*5*/:
            case Place.TYPE_ATM /*6*/:
                return driveId != null;
            default:
                return false;
        }
    }
}
