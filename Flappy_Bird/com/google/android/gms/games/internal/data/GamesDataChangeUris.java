package com.google.android.gms.games.internal.data;

import android.net.Uri;
import com.google.android.gms.games.Games;

public final class GamesDataChangeUris {
    private static final Uri zzasD;
    public static final Uri zzasE;
    public static final Uri zzasF;

    static {
        zzasD = Uri.parse("content://com.google.android.gms.games/").buildUpon().appendPath("data_change").build();
        zzasE = zzasD.buildUpon().appendPath("invitations").build();
        zzasF = zzasD.buildUpon().appendEncodedPath(Games.EXTRA_PLAYER_IDS).build();
    }
}
