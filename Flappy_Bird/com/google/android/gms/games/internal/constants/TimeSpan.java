package com.google.android.gms.games.internal.constants;

import com.google.android.gms.drive.events.CompletionEvent;
import com.google.example.games.basegameutils.GameHelper;

public final class TimeSpan {
    private TimeSpan() {
        throw new AssertionError("Uninstantiable");
    }

    public static String zzfG(int i) {
        switch (i) {
            case GameHelper.CLIENT_NONE /*0*/:
                return "DAILY";
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return "WEEKLY";
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return "ALL_TIME";
            default:
                throw new IllegalArgumentException("Unknown time span " + i);
        }
    }
}
