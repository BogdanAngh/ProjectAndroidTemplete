package com.google.android.gms.games.internal.constants;

import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.games.internal.GamesLog;
import com.google.example.games.basegameutils.GameHelper;

public final class TurnBasedMatchTurnStatus {
    public static String zzfG(int i) {
        switch (i) {
            case GameHelper.CLIENT_NONE /*0*/:
                return "TURN_STATUS_INVITED";
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return "TURN_STATUS_MY_TURN";
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return "TURN_STATUS_THEIR_TURN";
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return "TURN_STATUS_COMPLETE";
            default:
                GamesLog.zzv("MatchTurnStatus", "Unknown match turn status: " + i);
                return "TURN_STATUS_UNKNOWN";
        }
    }
}
