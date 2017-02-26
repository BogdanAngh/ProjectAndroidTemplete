package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.GamesMetadata.LoadGameInstancesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameSearchSuggestionsResult;
import com.google.android.gms.games.GamesMetadata.LoadGamesResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class GamesMetadataImpl implements GamesMetadata {

    private static abstract class LoadGameInstancesImpl extends BaseGamesApiMethodImpl<LoadGameInstancesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameInstancesImpl.1 */
        class C07661 implements LoadGameInstancesResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadGameInstancesImpl zzaqS;

            C07661(LoadGameInstancesImpl loadGameInstancesImpl, Status status) {
                this.zzaqS = loadGameInstancesImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzX(x0);
        }

        public LoadGameInstancesResult zzX(Status status) {
            return new C07661(this, status);
        }
    }

    private static abstract class LoadGameSearchSuggestionsImpl extends BaseGamesApiMethodImpl<LoadGameSearchSuggestionsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameSearchSuggestionsImpl.1 */
        class C07671 implements LoadGameSearchSuggestionsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadGameSearchSuggestionsImpl zzaqT;

            C07671(LoadGameSearchSuggestionsImpl loadGameSearchSuggestionsImpl, Status status) {
                this.zzaqT = loadGameSearchSuggestionsImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzY(x0);
        }

        public LoadGameSearchSuggestionsResult zzY(Status status) {
            return new C07671(this, status);
        }
    }

    private static abstract class LoadGamesImpl extends BaseGamesApiMethodImpl<LoadGamesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGamesImpl.1 */
        class C07681 implements LoadGamesResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadGamesImpl zzaqU;

            C07681(LoadGamesImpl loadGamesImpl, Status status) {
                this.zzaqU = loadGamesImpl;
                this.zzOl = status;
            }

            public GameBuffer getGames() {
                return new GameBuffer(DataHolder.zzbi(14));
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private LoadGamesImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzZ(x0);
        }

        public LoadGamesResult zzZ(Status status) {
            return new C07681(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.1 */
    class C09611 extends LoadGamesImpl {
        final /* synthetic */ GamesMetadataImpl zzaqQ;

        C09611(GamesMetadataImpl gamesMetadataImpl, GoogleApiClient x0) {
            this.zzaqQ = gamesMetadataImpl;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzf(this);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.2 */
    class C09622 extends LoadGameInstancesImpl {
        final /* synthetic */ String zzaqA;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzj(this, this.zzaqA);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl.3 */
    class C09633 extends LoadGameSearchSuggestionsImpl {
        final /* synthetic */ String zzaqR;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzk(this, this.zzaqR);
        }
    }

    public Game getCurrentGame(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsy();
    }

    public PendingResult<LoadGamesResult> loadGame(GoogleApiClient apiClient) {
        return apiClient.zza(new C09611(this, apiClient));
    }
}
