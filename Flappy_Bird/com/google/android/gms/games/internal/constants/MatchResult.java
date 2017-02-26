package com.google.android.gms.games.internal.constants;

import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public final class MatchResult {
    public static boolean isValid(int result) {
        switch (result) {
            case GameHelper.CLIENT_NONE /*0*/:
            case CompletionEvent.STATUS_FAILURE /*1*/:
            case CompletionEvent.STATUS_CONFLICT /*2*/:
            case CompletionEvent.STATUS_CANCELED /*3*/:
            case GameHelper.CLIENT_APPSTATE /*4*/:
            case Place.TYPE_ART_GALLERY /*5*/:
                return true;
            default:
                return false;
        }
    }
}
