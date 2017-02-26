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
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.Players.LoadPlayersResult;
import com.google.android.gms.games.Players.LoadProfileSettingsResult;
import com.google.android.gms.games.Players.LoadXpForGameCategoriesResult;
import com.google.android.gms.games.Players.LoadXpStreamResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class PlayersImpl implements Players {

    private static abstract class LoadPlayersImpl extends BaseGamesApiMethodImpl<LoadPlayersResult> {

        /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.LoadPlayersImpl.1 */
        class C07791 implements LoadPlayersResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadPlayersImpl zzarB;

            C07791(LoadPlayersImpl loadPlayersImpl, Status status) {
                this.zzarB = loadPlayersImpl;
                this.zzOl = status;
            }

            public PlayerBuffer getPlayers() {
                return new PlayerBuffer(DataHolder.zzbi(14));
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private LoadPlayersImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaj(x0);
        }

        public LoadPlayersResult zzaj(Status status) {
            return new C07791(this, status);
        }
    }

    private static abstract class LoadProfileSettingsResultImpl extends BaseGamesApiMethodImpl<LoadProfileSettingsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.LoadProfileSettingsResultImpl.1 */
        class C07801 implements LoadProfileSettingsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadProfileSettingsResultImpl zzarC;

            C07801(LoadProfileSettingsResultImpl loadProfileSettingsResultImpl, Status status) {
                this.zzarC = loadProfileSettingsResultImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public boolean isProfileVisible() {
                return true;
            }

            public boolean isVisibilityExplicitlySet() {
                return false;
            }
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzak(x0);
        }

        protected LoadProfileSettingsResult zzak(Status status) {
            return new C07801(this, status);
        }
    }

    private static abstract class LoadXpForGameCategoriesResultImpl extends BaseGamesApiMethodImpl<LoadXpForGameCategoriesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.LoadXpForGameCategoriesResultImpl.1 */
        class C07811 implements LoadXpForGameCategoriesResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadXpForGameCategoriesResultImpl zzarD;

            C07811(LoadXpForGameCategoriesResultImpl loadXpForGameCategoriesResultImpl, Status status) {
                this.zzarD = loadXpForGameCategoriesResultImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzal(x0);
        }

        public LoadXpForGameCategoriesResult zzal(Status status) {
            return new C07811(this, status);
        }
    }

    private static abstract class LoadXpStreamResultImpl extends BaseGamesApiMethodImpl<LoadXpStreamResult> {

        /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.LoadXpStreamResultImpl.1 */
        class C07821 implements LoadXpStreamResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadXpStreamResultImpl zzarE;

            C07821(LoadXpStreamResultImpl loadXpStreamResultImpl, Status status) {
                this.zzarE = loadXpStreamResultImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzam(x0);
        }

        public LoadXpStreamResult zzam(Status status) {
            return new C07821(this, status);
        }
    }

    private static abstract class UpdateProfileSettingsResultImpl extends BaseGamesApiMethodImpl<Status> {
        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.10 */
    class AnonymousClass10 extends LoadPlayersImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, "nearby", this.zzaqA, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.11 */
    class AnonymousClass11 extends LoadPlayersImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, "played_with", this.zzaqA, this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.12 */
    class AnonymousClass12 extends LoadPlayersImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, "played_with", this.zzaqA, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.13 */
    class AnonymousClass13 extends LoadPlayersImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.14 */
    class AnonymousClass14 extends LoadPlayersImpl {
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.15 */
    class AnonymousClass15 extends LoadPlayersImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzc((zzb) this, this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.16 */
    class AnonymousClass16 extends LoadPlayersImpl {
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzc((zzb) this, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.17 */
    class AnonymousClass17 extends LoadPlayersImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd(this, this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.18 */
    class AnonymousClass18 extends LoadPlayersImpl {
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd(this, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.19 */
    class AnonymousClass19 extends LoadPlayersImpl {
        final /* synthetic */ String zzaqR;
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb(this, this.zzaqR, this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.1 */
    class C09791 extends LoadPlayersImpl {
        final /* synthetic */ String zzTE;
        final /* synthetic */ PlayersImpl zzaru;

        C09791(PlayersImpl playersImpl, GoogleApiClient x0, String str) {
            this.zzaru = playersImpl;
            this.zzTE = str;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzTE, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.20 */
    class AnonymousClass20 extends LoadPlayersImpl {
        final /* synthetic */ String zzaqR;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb(this, this.zzaqR, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.21 */
    class AnonymousClass21 extends LoadPlayersImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ String zzarm;
        final /* synthetic */ int zzarv;
        final /* synthetic */ String zzarw;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzarw, this.zzarm, this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.22 */
    class AnonymousClass22 extends LoadPlayersImpl {
        final /* synthetic */ String zzarm;
        final /* synthetic */ int zzarv;
        final /* synthetic */ String zzarw;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzarw, this.zzarm, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.23 */
    class AnonymousClass23 extends LoadXpForGameCategoriesResultImpl {
        final /* synthetic */ String zzarx;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzl(this, this.zzarx);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.24 */
    class AnonymousClass24 extends LoadXpStreamResultImpl {
        final /* synthetic */ String zzarx;
        final /* synthetic */ int zzary;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzc((zzb) this, this.zzarx, this.zzary);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.25 */
    class AnonymousClass25 extends LoadXpStreamResultImpl {
        final /* synthetic */ String zzarx;
        final /* synthetic */ int zzary;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd((zzb) this, this.zzarx, this.zzary);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.26 */
    class AnonymousClass26 extends LoadProfileSettingsResultImpl {
        final /* synthetic */ boolean zzaqy;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzf((zzb) this, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.27 */
    class AnonymousClass27 extends UpdateProfileSettingsResultImpl {
        final /* synthetic */ boolean zzarz;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzg((zzb) this, this.zzarz);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.2 */
    class C09802 extends LoadPlayersImpl {
        final /* synthetic */ String zzTE;
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ PlayersImpl zzaru;

        C09802(PlayersImpl playersImpl, GoogleApiClient x0, String str, boolean z) {
            this.zzaru = playersImpl;
            this.zzTE = str;
            this.zzaqy = z;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzTE, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.3 */
    class C09813 extends LoadPlayersImpl {
        final /* synthetic */ String[] zzarA;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzarA);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.4 */
    class C09824 extends LoadPlayersImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ PlayersImpl zzaru;
        final /* synthetic */ int zzarv;

        C09824(PlayersImpl playersImpl, GoogleApiClient x0, int i, boolean z) {
            this.zzaru = playersImpl;
            this.zzarv = i;
            this.zzaqy = z;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.5 */
    class C09835 extends LoadPlayersImpl {
        final /* synthetic */ PlayersImpl zzaru;
        final /* synthetic */ int zzarv;

        C09835(PlayersImpl playersImpl, GoogleApiClient x0, int i) {
            this.zzaru = playersImpl;
            this.zzarv = i;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.6 */
    class C09846 extends LoadPlayersImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ PlayersImpl zzaru;
        final /* synthetic */ int zzarv;

        C09846(PlayersImpl playersImpl, GoogleApiClient x0, int i, boolean z) {
            this.zzaru = playersImpl;
            this.zzarv = i;
            this.zzaqy = z;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, "played_with", this.zzarv, false, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.7 */
    class C09857 extends LoadPlayersImpl {
        final /* synthetic */ PlayersImpl zzaru;
        final /* synthetic */ int zzarv;

        C09857(PlayersImpl playersImpl, GoogleApiClient x0, int i) {
            this.zzaru = playersImpl;
            this.zzarv = i;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, "played_with", this.zzarv, true, false);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.8 */
    class C09868 extends LoadPlayersImpl {
        final /* synthetic */ boolean zzaqy;
        final /* synthetic */ PlayersImpl zzaru;

        C09868(PlayersImpl playersImpl, GoogleApiClient x0, boolean z) {
            this.zzaru = playersImpl;
            this.zzaqy = z;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.PlayersImpl.9 */
    class C09879 extends LoadPlayersImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ int zzarv;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, "nearby", this.zzaqA, this.zzarv, false, false);
        }
    }

    public Intent getCompareProfileIntent(GoogleApiClient apiClient, Player player) {
        return Games.zzd(apiClient).zza(new PlayerEntity(player));
    }

    public Player getCurrentPlayer(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsx();
    }

    public String getCurrentPlayerId(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsw();
    }

    public Intent getPlayerSearchIntent(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsH();
    }

    public PendingResult<LoadPlayersResult> loadConnectedPlayers(GoogleApiClient apiClient, boolean forceReload) {
        return apiClient.zza(new C09868(this, apiClient, forceReload));
    }

    public PendingResult<LoadPlayersResult> loadInvitablePlayers(GoogleApiClient apiClient, int pageSize, boolean forceReload) {
        return apiClient.zza(new C09824(this, apiClient, pageSize, forceReload));
    }

    public PendingResult<LoadPlayersResult> loadMoreInvitablePlayers(GoogleApiClient apiClient, int pageSize) {
        return apiClient.zza(new C09835(this, apiClient, pageSize));
    }

    public PendingResult<LoadPlayersResult> loadMoreRecentlyPlayedWithPlayers(GoogleApiClient apiClient, int pageSize) {
        return apiClient.zza(new C09857(this, apiClient, pageSize));
    }

    public PendingResult<LoadPlayersResult> loadPlayer(GoogleApiClient apiClient, String playerId) {
        return apiClient.zza(new C09791(this, apiClient, playerId));
    }

    public PendingResult<LoadPlayersResult> loadPlayer(GoogleApiClient apiClient, String playerId, boolean forceReload) {
        return apiClient.zza(new C09802(this, apiClient, playerId, forceReload));
    }

    public PendingResult<LoadPlayersResult> loadRecentlyPlayedWithPlayers(GoogleApiClient apiClient, int pageSize, boolean forceReload) {
        return apiClient.zza(new C09846(this, apiClient, pageSize, forceReload));
    }
}
