package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzi;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AppMetadata;
import com.google.android.gms.nearby.connection.Connections;
import com.google.android.gms.nearby.connection.Connections.ConnectionRequestListener;
import com.google.android.gms.nearby.connection.Connections.ConnectionResponseCallback;
import com.google.android.gms.nearby.connection.Connections.EndpointDiscoveryListener;
import com.google.android.gms.nearby.connection.Connections.MessageListener;
import com.google.android.gms.nearby.connection.Connections.StartAdvertisingResult;
import java.util.List;

public final class zzoq implements Connections {
    public static final ClientKey<zzop> zzNX;
    public static final com.google.android.gms.common.api.Api.zza<zzop, NoOptions> zzNY;

    /* renamed from: com.google.android.gms.internal.zzoq.1 */
    static class C04971 implements com.google.android.gms.common.api.Api.zza<zzop, NoOptions> {
        C04971() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzq(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzop zzq(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzop(context, looper, connectionCallbacks, onConnectionFailedListener);
        }
    }

    private static abstract class zza<R extends Result> extends com.google.android.gms.common.api.zza.zza<R, zzop> {
        public zza(GoogleApiClient googleApiClient) {
            super(zzoq.zzNX, googleApiClient);
        }
    }

    private static abstract class zzb extends zza<StartAdvertisingResult> {

        /* renamed from: com.google.android.gms.internal.zzoq.zzb.1 */
        class C08141 implements StartAdvertisingResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ zzb zzaFN;

            C08141(zzb com_google_android_gms_internal_zzoq_zzb, Status status) {
                this.zzaFN = com_google_android_gms_internal_zzoq_zzb;
                this.zzOl = status;
            }

            public String getLocalEndpointName() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        private zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaM(x0);
        }

        public StartAdvertisingResult zzaM(Status status) {
            return new C08141(this, status);
        }
    }

    private static abstract class zzc extends zza<Status> {
        private zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzoq.2 */
    class C10362 extends zzb {
        final /* synthetic */ String val$name;
        final /* synthetic */ AppMetadata zzaFG;
        final /* synthetic */ long zzaFH;
        final /* synthetic */ zzi zzaFI;
        final /* synthetic */ zzoq zzaFJ;

        C10362(zzoq com_google_android_gms_internal_zzoq, GoogleApiClient googleApiClient, String str, AppMetadata appMetadata, long j, zzi com_google_android_gms_common_api_zzi) {
            this.zzaFJ = com_google_android_gms_internal_zzoq;
            this.val$name = str;
            this.zzaFG = appMetadata;
            this.zzaFH = j;
            this.zzaFI = com_google_android_gms_common_api_zzi;
            super(null);
        }

        protected void zza(zzop com_google_android_gms_internal_zzop) throws RemoteException {
            com_google_android_gms_internal_zzop.zza(this, this.val$name, this.zzaFG, this.zzaFH, this.zzaFI);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzoq.3 */
    class C10373 extends zzc {
        final /* synthetic */ String zzaFE;
        final /* synthetic */ long zzaFH;
        final /* synthetic */ zzoq zzaFJ;
        final /* synthetic */ zzi zzaFK;

        C10373(zzoq com_google_android_gms_internal_zzoq, GoogleApiClient googleApiClient, String str, long j, zzi com_google_android_gms_common_api_zzi) {
            this.zzaFJ = com_google_android_gms_internal_zzoq;
            this.zzaFE = str;
            this.zzaFH = j;
            this.zzaFK = com_google_android_gms_common_api_zzi;
            super(null);
        }

        protected void zza(zzop com_google_android_gms_internal_zzop) throws RemoteException {
            com_google_android_gms_internal_zzop.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaFE, this.zzaFH, this.zzaFK);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzoq.4 */
    class C10384 extends zzc {
        final /* synthetic */ String val$name;
        final /* synthetic */ zzoq zzaFJ;
        final /* synthetic */ zzi zzaFL;
        final /* synthetic */ zzi zzaFM;
        final /* synthetic */ String zzaFs;
        final /* synthetic */ byte[] zzarU;

        C10384(zzoq com_google_android_gms_internal_zzoq, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr, zzi com_google_android_gms_common_api_zzi, zzi com_google_android_gms_common_api_zzi2) {
            this.zzaFJ = com_google_android_gms_internal_zzoq;
            this.val$name = str;
            this.zzaFs = str2;
            this.zzarU = bArr;
            this.zzaFL = com_google_android_gms_common_api_zzi;
            this.zzaFM = com_google_android_gms_common_api_zzi2;
            super(null);
        }

        protected void zza(zzop com_google_android_gms_internal_zzop) throws RemoteException {
            com_google_android_gms_internal_zzop.zza(this, this.val$name, this.zzaFs, this.zzarU, this.zzaFL, this.zzaFM);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzoq.5 */
    class C10395 extends zzc {
        final /* synthetic */ zzoq zzaFJ;
        final /* synthetic */ zzi zzaFM;
        final /* synthetic */ String zzaFs;
        final /* synthetic */ byte[] zzarU;

        C10395(zzoq com_google_android_gms_internal_zzoq, GoogleApiClient googleApiClient, String str, byte[] bArr, zzi com_google_android_gms_common_api_zzi) {
            this.zzaFJ = com_google_android_gms_internal_zzoq;
            this.zzaFs = str;
            this.zzarU = bArr;
            this.zzaFM = com_google_android_gms_common_api_zzi;
            super(null);
        }

        protected void zza(zzop com_google_android_gms_internal_zzop) throws RemoteException {
            com_google_android_gms_internal_zzop.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaFs, this.zzarU, this.zzaFM);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzoq.6 */
    class C10406 extends zzc {
        final /* synthetic */ zzoq zzaFJ;
        final /* synthetic */ String zzaFs;

        C10406(zzoq com_google_android_gms_internal_zzoq, GoogleApiClient googleApiClient, String str) {
            this.zzaFJ = com_google_android_gms_internal_zzoq;
            this.zzaFs = str;
            super(null);
        }

        protected void zza(zzop com_google_android_gms_internal_zzop) throws RemoteException {
            com_google_android_gms_internal_zzop.zzp(this, this.zzaFs);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C04971();
    }

    public static zzop zzd(GoogleApiClient googleApiClient, boolean z) {
        zzu.zzb(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        zzu.zza(googleApiClient.isConnected(), (Object) "GoogleApiClient must be connected.");
        return zze(googleApiClient, z);
    }

    public static zzop zze(GoogleApiClient googleApiClient, boolean z) {
        zzu.zza(googleApiClient.zza(Nearby.CONNECTIONS_API), (Object) "GoogleApiClient is not configured to use the Nearby Connections Api. Pass Nearby.CONNECTIONS_API into GoogleApiClient.Builder#addApi() to use this feature.");
        boolean hasConnectedApi = googleApiClient.hasConnectedApi(Nearby.CONNECTIONS_API);
        if (!z || hasConnectedApi) {
            return hasConnectedApi ? (zzop) googleApiClient.zza(zzNX) : null;
        } else {
            throw new IllegalStateException("GoogleApiClient has an optional Nearby.CONNECTIONS_API and is not connected to Nearby Connections. Use GoogleApiClient.hasConnectedApi(Nearby.CONNECTIONS_API) to guard this call.");
        }
    }

    public PendingResult<Status> acceptConnectionRequest(GoogleApiClient apiClient, String remoteEndpointId, byte[] payload, MessageListener messageListener) {
        return apiClient.zzb(new C10395(this, apiClient, remoteEndpointId, payload, apiClient.zzo(messageListener)));
    }

    public void disconnectFromEndpoint(GoogleApiClient apiClient, String remoteEndpointId) {
        zzd(apiClient, false).zzdR(remoteEndpointId);
    }

    public String getLocalDeviceId(GoogleApiClient apiClient) {
        return zzd(apiClient, true).zzwS();
    }

    public String getLocalEndpointId(GoogleApiClient apiClient) {
        return zzd(apiClient, true).zzwR();
    }

    public PendingResult<Status> rejectConnectionRequest(GoogleApiClient apiClient, String remoteEndpointId) {
        return apiClient.zzb(new C10406(this, apiClient, remoteEndpointId));
    }

    public PendingResult<Status> sendConnectionRequest(GoogleApiClient apiClient, String name, String remoteEndpointId, byte[] payload, ConnectionResponseCallback connectionResponseCallback, MessageListener messageListener) {
        return apiClient.zzb(new C10384(this, apiClient, name, remoteEndpointId, payload, apiClient.zzo(connectionResponseCallback), apiClient.zzo(messageListener)));
    }

    public void sendReliableMessage(GoogleApiClient apiClient, String remoteEndpointId, byte[] payload) {
        zzd(apiClient, false).zza(new String[]{remoteEndpointId}, payload);
    }

    public void sendReliableMessage(GoogleApiClient apiClient, List<String> remoteEndpointIds, byte[] payload) {
        zzd(apiClient, false).zza((String[]) remoteEndpointIds.toArray(new String[remoteEndpointIds.size()]), payload);
    }

    public void sendUnreliableMessage(GoogleApiClient apiClient, String remoteEndpointId, byte[] payload) {
        zzd(apiClient, false).zzb(new String[]{remoteEndpointId}, payload);
    }

    public void sendUnreliableMessage(GoogleApiClient apiClient, List<String> remoteEndpointIds, byte[] payload) {
        zzd(apiClient, false).zzb((String[]) remoteEndpointIds.toArray(new String[remoteEndpointIds.size()]), payload);
    }

    public PendingResult<StartAdvertisingResult> startAdvertising(GoogleApiClient apiClient, String name, AppMetadata appMetadata, long durationMillis, ConnectionRequestListener connectionRequestListener) {
        return apiClient.zzb(new C10362(this, apiClient, name, appMetadata, durationMillis, apiClient.zzo(connectionRequestListener)));
    }

    public PendingResult<Status> startDiscovery(GoogleApiClient apiClient, String serviceId, long durationMillis, EndpointDiscoveryListener listener) {
        return apiClient.zzb(new C10373(this, apiClient, serviceId, durationMillis, apiClient.zzo(listener)));
    }

    public void stopAdvertising(GoogleApiClient apiClient) {
        zzd(apiClient, false).zzwT();
    }

    public void stopAllEndpoints(GoogleApiClient apiClient) {
        zzd(apiClient, false).zzwU();
    }

    public void stopDiscovery(GoogleApiClient apiClient, String serviceId) {
        zzd(apiClient, false).zzdQ(serviceId);
    }
}
