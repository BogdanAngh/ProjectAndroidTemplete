package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult;
import com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class AchievementsImpl implements Achievements {

    private static abstract class LoadImpl extends BaseGamesApiMethodImpl<LoadAchievementsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.LoadImpl.1 */
        class C07611 implements LoadAchievementsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadImpl zzaqD;

            C07611(LoadImpl loadImpl, Status status) {
                this.zzaqD = loadImpl;
                this.zzOl = status;
            }

            public AchievementBuffer getAchievements() {
                return new AchievementBuffer(DataHolder.zzbi(14));
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private LoadImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzQ(x0);
        }

        public LoadAchievementsResult zzQ(Status status) {
            return new C07611(this, status);
        }
    }

    private static abstract class UpdateImpl extends BaseGamesApiMethodImpl<UpdateAchievementResult> {
        private final String zzKI;

        /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.UpdateImpl.1 */
        class C07621 implements UpdateAchievementResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ UpdateImpl zzaqE;

            C07621(UpdateImpl updateImpl, Status status) {
                this.zzaqE = updateImpl;
                this.zzOl = status;
            }

            public String getAchievementId() {
                return this.zzaqE.zzKI;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public UpdateImpl(String id, GoogleApiClient googleApiClient) {
            super(googleApiClient);
            this.zzKI = id;
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzR(x0);
        }

        public UpdateAchievementResult zzR(Status status) {
            return new C07621(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.10 */
    class AnonymousClass10 extends LoadImpl {
        final /* synthetic */ String zzTE;
        final /* synthetic */ String zzaqA;
        final /* synthetic */ boolean zzaqy;

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzTE, this.zzaqA, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.1 */
    class C09461 extends LoadImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09461(AchievementsImpl achievementsImpl, GoogleApiClient x0, boolean z) {
            this.zzaqz = achievementsImpl;
            this.zzaqy = z;
            super(null);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzc((zzb) this, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.2 */
    class C09472 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09472(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza(null, this.zzaqB);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.3 */
    class C09483 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09483(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqB);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.4 */
    class C09494 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09494(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb(null, this.zzaqB);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.5 */
    class C09505 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09505(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzaqB);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.6 */
    class C09516 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ int zzaqC;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09516(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str, int i) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            this.zzaqC = i;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza(null, this.zzaqB, this.zzaqC);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.7 */
    class C09527 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ int zzaqC;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09527(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str, int i) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            this.zzaqC = i;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqB, this.zzaqC);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.8 */
    class C09538 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ int zzaqC;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09538(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str, int i) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            this.zzaqC = i;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb(null, this.zzaqB, this.zzaqC);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AchievementsImpl.9 */
    class C09549 extends UpdateImpl {
        final /* synthetic */ String zzaqB;
        final /* synthetic */ int zzaqC;
        final /* synthetic */ AchievementsImpl zzaqz;

        C09549(AchievementsImpl achievementsImpl, String x0, GoogleApiClient x1, String str, int i) {
            this.zzaqz = achievementsImpl;
            this.zzaqB = str;
            this.zzaqC = i;
            super(x0, x1);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzaqB, this.zzaqC);
        }
    }

    public Intent getAchievementsIntent(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsA();
    }

    public void increment(GoogleApiClient apiClient, String id, int numSteps) {
        apiClient.zzb(new C09516(this, id, apiClient, id, numSteps));
    }

    public PendingResult<UpdateAchievementResult> incrementImmediate(GoogleApiClient apiClient, String id, int numSteps) {
        return apiClient.zzb(new C09527(this, id, apiClient, id, numSteps));
    }

    public PendingResult<LoadAchievementsResult> load(GoogleApiClient apiClient, boolean forceReload) {
        return apiClient.zza(new C09461(this, apiClient, forceReload));
    }

    public void reveal(GoogleApiClient apiClient, String id) {
        apiClient.zzb(new C09472(this, id, apiClient, id));
    }

    public PendingResult<UpdateAchievementResult> revealImmediate(GoogleApiClient apiClient, String id) {
        return apiClient.zzb(new C09483(this, id, apiClient, id));
    }

    public void setSteps(GoogleApiClient apiClient, String id, int numSteps) {
        apiClient.zzb(new C09538(this, id, apiClient, id, numSteps));
    }

    public PendingResult<UpdateAchievementResult> setStepsImmediate(GoogleApiClient apiClient, String id, int numSteps) {
        return apiClient.zzb(new C09549(this, id, apiClient, id, numSteps));
    }

    public void unlock(GoogleApiClient apiClient, String id) {
        apiClient.zzb(new C09494(this, id, apiClient, id));
    }

    public PendingResult<UpdateAchievementResult> unlockImmediate(GoogleApiClient apiClient, String id) {
        return apiClient.zzb(new C09505(this, id, apiClient, id));
    }
}
