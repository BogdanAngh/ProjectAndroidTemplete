package com.google.android.gms.internal;

import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

final class zzjv {
    protected static final int zzaJ(int i) {
        switch (i) {
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return 5;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return 1;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return 2;
            case Place.TYPE_ART_GALLERY /*5*/:
                return 3;
            case Place.TYPE_ATM /*6*/:
                return 4;
            default:
                return 0;
        }
    }
}
