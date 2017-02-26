package com.google.android.gms.games.internal.constants;

import com.google.android.gms.drive.events.CompletionEvent;
import com.google.example.games.basegameutils.GameHelper;

public final class LeaderboardCollection {
    private LeaderboardCollection() {
    }

    public static String zzfG(int i) {
        switch (i) {
            case GameHelper.CLIENT_NONE /*0*/:
                return "PUBLIC";
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return "SOCIAL";
            default:
                throw new IllegalArgumentException("Unknown leaderboard collection: " + i);
        }
    }
}
