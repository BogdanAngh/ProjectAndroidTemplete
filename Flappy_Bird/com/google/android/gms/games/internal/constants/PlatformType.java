package com.google.android.gms.games.internal.constants;

import com.google.android.gms.drive.events.CompletionEvent;
import com.google.example.games.basegameutils.GameHelper;

public final class PlatformType {
    private PlatformType() {
    }

    public static String zzfG(int i) {
        switch (i) {
            case GameHelper.CLIENT_NONE /*0*/:
                return "ANDROID";
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return "IOS";
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return "WEB_APP";
            default:
                throw new IllegalArgumentException("Unknown platform type: " + i);
        }
    }
}
