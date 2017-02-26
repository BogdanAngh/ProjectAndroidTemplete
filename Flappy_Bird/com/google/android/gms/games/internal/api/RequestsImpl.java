package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.games.request.Requests.LoadRequestSummariesResult;
import com.google.android.gms.games.request.Requests.LoadRequestsResult;
import com.google.android.gms.games.request.Requests.SendRequestResult;
import com.google.android.gms.games.request.Requests.UpdateRequestsResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class RequestsImpl implements Requests {

    private static abstract class LoadRequestSummariesImpl extends BaseGamesApiMethodImpl<LoadRequestSummariesResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.LoadRequestSummariesImpl.1 */
        class C07861 implements LoadRequestSummariesResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadRequestSummariesImpl zzarW;

            C07861(LoadRequestSummariesImpl loadRequestSummariesImpl, Status status) {
                this.zzarW = loadRequestSummariesImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaq(x0);
        }

        public LoadRequestSummariesResult zzaq(Status status) {
            return new C07861(this, status);
        }
    }

    private static abstract class LoadRequestsImpl extends BaseGamesApiMethodImpl<LoadRequestsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.LoadRequestsImpl.1 */
        class C07871 implements LoadRequestsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ LoadRequestsImpl zzarX;

            C07871(LoadRequestsImpl loadRequestsImpl, Status status) {
                this.zzarX = loadRequestsImpl;
                this.zzOl = status;
            }

            public GameRequestBuffer getRequests(int type) {
                return new GameRequestBuffer(DataHolder.zzbi(this.zzOl.getStatusCode()));
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private LoadRequestsImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzar(x0);
        }

        public LoadRequestsResult zzar(Status status) {
            return new C07871(this, status);
        }
    }

    private static abstract class SendRequestImpl extends BaseGamesApiMethodImpl<SendRequestResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.SendRequestImpl.1 */
        class C07881 implements SendRequestResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ SendRequestImpl zzarY;

            C07881(SendRequestImpl sendRequestImpl, Status status) {
                this.zzarY = sendRequestImpl;
                this.zzOl = status;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzas(x0);
        }

        public SendRequestResult zzas(Status status) {
            return new C07881(this, status);
        }
    }

    private static abstract class UpdateRequestsImpl extends BaseGamesApiMethodImpl<UpdateRequestsResult> {

        /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.UpdateRequestsImpl.1 */
        class C07891 implements UpdateRequestsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ UpdateRequestsImpl zzarZ;

            C07891(UpdateRequestsImpl updateRequestsImpl, Status status) {
                this.zzarZ = updateRequestsImpl;
                this.zzOl = status;
            }

            public Set<String> getRequestIds() {
                return null;
            }

            public int getRequestOutcome(String requestId) {
                throw new IllegalArgumentException("Unknown request ID " + requestId);
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private UpdateRequestsImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzat(x0);
        }

        public UpdateRequestsResult zzat(Status status) {
            return new C07891(this, status);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.1 */
    class C09941 extends UpdateRequestsImpl {
        final /* synthetic */ String[] zzarO;
        final /* synthetic */ RequestsImpl zzarP;

        C09941(RequestsImpl requestsImpl, GoogleApiClient x0, String[] strArr) {
            this.zzarP = requestsImpl;
            this.zzarO = strArr;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzb((zzb) this, this.zzarO);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.2 */
    class C09952 extends UpdateRequestsImpl {
        final /* synthetic */ String[] zzarO;
        final /* synthetic */ RequestsImpl zzarP;

        C09952(RequestsImpl requestsImpl, GoogleApiClient x0, String[] strArr) {
            this.zzarP = requestsImpl;
            this.zzarO = strArr;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzc((zzb) this, this.zzarO);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.3 */
    class C09963 extends LoadRequestsImpl {
        final /* synthetic */ int zzaqV;
        final /* synthetic */ RequestsImpl zzarP;
        final /* synthetic */ int zzarQ;
        final /* synthetic */ int zzarR;

        C09963(RequestsImpl requestsImpl, GoogleApiClient x0, int i, int i2, int i3) {
            this.zzarP = requestsImpl;
            this.zzarQ = i;
            this.zzarR = i2;
            this.zzaqV = i3;
            super(null);
        }

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzarQ, this.zzarR, this.zzaqV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.4 */
    class C09974 extends SendRequestImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ String[] zzarS;
        final /* synthetic */ int zzarT;
        final /* synthetic */ byte[] zzarU;
        final /* synthetic */ int zzarV;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqA, this.zzarS, this.zzarT, this.zzarU, this.zzarV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.5 */
    class C09985 extends SendRequestImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ String[] zzarS;
        final /* synthetic */ int zzarT;
        final /* synthetic */ byte[] zzarU;
        final /* synthetic */ int zzarV;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqA, this.zzarS, this.zzarT, this.zzarU, this.zzarV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.6 */
    class C09996 extends UpdateRequestsImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ String zzarK;
        final /* synthetic */ String[] zzarO;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqA, this.zzarK, this.zzarO);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.7 */
    class C10007 extends LoadRequestsImpl {
        final /* synthetic */ String zzaqA;
        final /* synthetic */ int zzaqV;
        final /* synthetic */ String zzarK;
        final /* synthetic */ int zzarQ;
        final /* synthetic */ int zzarR;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza((zzb) this, this.zzaqA, this.zzarK, this.zzarQ, this.zzarR, this.zzaqV);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.RequestsImpl.8 */
    class C10018 extends LoadRequestSummariesImpl {
        final /* synthetic */ String zzarK;
        final /* synthetic */ int zzarR;

        protected void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzf(this, this.zzarK, this.zzarR);
        }
    }

    public PendingResult<UpdateRequestsResult> acceptRequest(GoogleApiClient apiClient, String requestId) {
        List arrayList = new ArrayList();
        arrayList.add(requestId);
        return acceptRequests(apiClient, arrayList);
    }

    public PendingResult<UpdateRequestsResult> acceptRequests(GoogleApiClient apiClient, List<String> requestIds) {
        return apiClient.zzb(new C09941(this, apiClient, requestIds == null ? null : (String[]) requestIds.toArray(new String[requestIds.size()])));
    }

    public PendingResult<UpdateRequestsResult> dismissRequest(GoogleApiClient apiClient, String requestId) {
        List arrayList = new ArrayList();
        arrayList.add(requestId);
        return dismissRequests(apiClient, arrayList);
    }

    public PendingResult<UpdateRequestsResult> dismissRequests(GoogleApiClient apiClient, List<String> requestIds) {
        return apiClient.zzb(new C09952(this, apiClient, requestIds == null ? null : (String[]) requestIds.toArray(new String[requestIds.size()])));
    }

    public ArrayList<GameRequest> getGameRequestsFromBundle(Bundle extras) {
        if (extras == null || !extras.containsKey(Requests.EXTRA_REQUESTS)) {
            return new ArrayList();
        }
        ArrayList arrayList = (ArrayList) extras.get(Requests.EXTRA_REQUESTS);
        ArrayList<GameRequest> arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList2.add((GameRequest) arrayList.get(i));
        }
        return arrayList2;
    }

    public ArrayList<GameRequest> getGameRequestsFromInboxResponse(Intent response) {
        return response == null ? new ArrayList() : getGameRequestsFromBundle(response.getExtras());
    }

    public Intent getInboxIntent(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsM();
    }

    public int getMaxLifetimeDays(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsO();
    }

    public int getMaxPayloadSize(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsN();
    }

    public Intent getSendIntent(GoogleApiClient apiClient, int type, byte[] payload, int requestLifetimeDays, Bitmap icon, String description) {
        return Games.zzd(apiClient).zza(type, payload, requestLifetimeDays, icon, description);
    }

    public PendingResult<LoadRequestsResult> loadRequests(GoogleApiClient apiClient, int requestDirection, int types, int sortOrder) {
        return apiClient.zza(new C09963(this, apiClient, requestDirection, types, sortOrder));
    }

    public void registerRequestListener(GoogleApiClient apiClient, OnRequestReceivedListener listener) {
        GamesClientImpl zzb = Games.zzb(apiClient, false);
        if (zzb != null) {
            zzb.zzd(apiClient.zzo(listener));
        }
    }

    public void unregisterRequestListener(GoogleApiClient apiClient) {
        GamesClientImpl zzb = Games.zzb(apiClient, false);
        if (zzb != null) {
            zzb.zzsG();
        }
    }
}
