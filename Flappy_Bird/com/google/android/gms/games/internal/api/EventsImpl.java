package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.games.event.Events;
import com.google.android.gms.games.event.Events.LoadEventsResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class EventsImpl implements Events {

    private static abstract class LoadImpl extends BaseGamesApiMethodImpl<LoadEventsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.EventsImpl.LoadImpl.1 */
        class C07651 implements LoadEventsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadImpl zzaqO;

            C07651(LoadImpl loadImpl, Status status) {
                this.zzaqO = loadImpl;
                this.zzOl = status;
            }

            public EventBuffer getEvents() {
                return new EventBuffer(DataHolder.zzbi(14));
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
            return zzW(x0);
        }

        public LoadEventsResult zzW(Status status) {
            return new C07651(this, status);
        }
    }

    private static abstract class UpdateImpl extends BaseGamesApiMethodImpl<Result> {

        /* renamed from: com.google.android.gms.games.internal.api.EventsImpl.UpdateImpl.1 */
        class C04321 implements Result {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ UpdateImpl zzaqP;

            C04321(UpdateImpl updateImpl, Status status) {
                this.zzaqP = updateImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        private UpdateImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public Result createFailedResult(Status status) {
            return new C04321(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.EventsImpl.1 */
    class C09581 extends LoadImpl {
        final /* synthetic */ String[] zzaqK;
        final /* synthetic */ EventsImpl zzaqL;
        final /* synthetic */ boolean zzaqy;

        C09581(EventsImpl eventsImpl, GoogleApiClient x0, boolean z, String[] strArr) {
            this.zzaqL = eventsImpl;
            this.zzaqy = z;
            this.zzaqK = strArr;
            super(null);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqy, this.zzaqK);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.EventsImpl.2 */
    class C09592 extends LoadImpl {
        final /* synthetic */ EventsImpl zzaqL;
        final /* synthetic */ boolean zzaqy;

        C09592(EventsImpl eventsImpl, GoogleApiClient x0, boolean z) {
            this.zzaqL = eventsImpl;
            this.zzaqy = z;
            super(null);
        }

        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd((zzb) this, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.EventsImpl.3 */
    class C09603 extends UpdateImpl {
        final /* synthetic */ EventsImpl zzaqL;
        final /* synthetic */ String zzaqM;
        final /* synthetic */ int zzaqN;

        C09603(EventsImpl eventsImpl, GoogleApiClient x0, String str, int i) {
            this.zzaqL = eventsImpl;
            this.zzaqM = str;
            this.zzaqN = i;
            super(null);
        }

        public void zza(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.zzp(this.zzaqM, this.zzaqN);
        }
    }

    public void increment(GoogleApiClient apiClient, String eventId, int incrementAmount) {
        GamesClientImpl zzc = Games.zzc(apiClient, false);
        if (zzc != null) {
            if (zzc.isConnected()) {
                zzc.zzp(eventId, incrementAmount);
            } else {
                apiClient.zzb(new C09603(this, apiClient, eventId, incrementAmount));
            }
        }
    }

    public PendingResult<LoadEventsResult> load(GoogleApiClient apiClient, boolean forceReload) {
        return apiClient.zza(new C09592(this, apiClient, forceReload));
    }

    public PendingResult<LoadEventsResult> loadByIds(GoogleApiClient apiClient, boolean forceReload, String... eventIds) {
        return apiClient.zza(new C09581(this, apiClient, forceReload, eventIds));
    }
}
