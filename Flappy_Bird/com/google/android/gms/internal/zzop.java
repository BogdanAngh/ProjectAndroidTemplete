package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.nearby.connection.AppMetadata;
import com.google.android.gms.nearby.connection.Connections.ConnectionRequestListener;
import com.google.android.gms.nearby.connection.Connections.ConnectionResponseCallback;
import com.google.android.gms.nearby.connection.Connections.EndpointDiscoveryListener;
import com.google.android.gms.nearby.connection.Connections.MessageListener;
import com.google.android.gms.nearby.connection.Connections.StartAdvertisingResult;

public final class zzop extends zzi<zzos> {
    private final long zzaoX;

    private static final class zze implements StartAdvertisingResult {
        private final Status zzOt;
        private final String zzaFB;

        zze(Status status, String str) {
            this.zzOt = status;
            this.zzaFB = str;
        }

        public String getLocalEndpointName() {
            return this.zzaFB;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzop.1 */
    class C08491 extends zzoo {
        final /* synthetic */ com.google.android.gms.common.api.zza.zzb zzaFp;
        final /* synthetic */ zzop zzaFq;

        C08491(zzop com_google_android_gms_internal_zzop, com.google.android.gms.common.api.zza.zzb com_google_android_gms_common_api_zza_zzb) {
            this.zzaFq = com_google_android_gms_internal_zzop;
            this.zzaFp = com_google_android_gms_common_api_zza_zzb;
        }

        public void zzib(int i) throws RemoteException {
            this.zzaFp.zzm(new Status(i));
        }
    }

    private static class zzb extends zzoo {
        private final com.google.android.gms.common.api.zzi<MessageListener> zzaFr;

        /* renamed from: com.google.android.gms.internal.zzop.zzb.1 */
        class C04911 implements com.google.android.gms.common.api.zzi.zzb<MessageListener> {
            final /* synthetic */ String zzaFs;
            final /* synthetic */ boolean zzaFt;
            final /* synthetic */ zzb zzaFu;
            final /* synthetic */ byte[] zzarU;

            C04911(zzb com_google_android_gms_internal_zzop_zzb, String str, byte[] bArr, boolean z) {
                this.zzaFu = com_google_android_gms_internal_zzop_zzb;
                this.zzaFs = str;
                this.zzarU = bArr;
                this.zzaFt = z;
            }

            public void zza(MessageListener messageListener) {
                messageListener.onMessageReceived(this.zzaFs, this.zzarU, this.zzaFt);
            }

            public void zzmw() {
            }

            public /* synthetic */ void zzn(Object obj) {
                zza((MessageListener) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzop.zzb.2 */
        class C04922 implements com.google.android.gms.common.api.zzi.zzb<MessageListener> {
            final /* synthetic */ String zzaFs;
            final /* synthetic */ zzb zzaFu;

            C04922(zzb com_google_android_gms_internal_zzop_zzb, String str) {
                this.zzaFu = com_google_android_gms_internal_zzop_zzb;
                this.zzaFs = str;
            }

            public void zza(MessageListener messageListener) {
                messageListener.onDisconnected(this.zzaFs);
            }

            public void zzmw() {
            }

            public /* synthetic */ void zzn(Object obj) {
                zza((MessageListener) obj);
            }
        }

        zzb(com.google.android.gms.common.api.zzi<MessageListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener) {
            this.zzaFr = com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener;
        }

        public void onDisconnected(String remoteEndpointId) throws RemoteException {
            this.zzaFr.zza(new C04922(this, remoteEndpointId));
        }

        public void onMessageReceived(String remoteEndpointId, byte[] payload, boolean isReliable) throws RemoteException {
            this.zzaFr.zza(new C04911(this, remoteEndpointId, payload, isReliable));
        }
    }

    private static final class zzd extends zzoo {
        private final com.google.android.gms.common.api.zza.zzb<StartAdvertisingResult> zzOs;
        private final com.google.android.gms.common.api.zzi<ConnectionRequestListener> zzaFx;

        /* renamed from: com.google.android.gms.internal.zzop.zzd.1 */
        class C04941 implements com.google.android.gms.common.api.zzi.zzb<ConnectionRequestListener> {
            final /* synthetic */ zzd zzaFA;
            final /* synthetic */ String zzaFs;
            final /* synthetic */ String zzaFy;
            final /* synthetic */ String zzaFz;
            final /* synthetic */ byte[] zzarU;

            C04941(zzd com_google_android_gms_internal_zzop_zzd, String str, String str2, String str3, byte[] bArr) {
                this.zzaFA = com_google_android_gms_internal_zzop_zzd;
                this.zzaFs = str;
                this.zzaFy = str2;
                this.zzaFz = str3;
                this.zzarU = bArr;
            }

            public void zza(ConnectionRequestListener connectionRequestListener) {
                connectionRequestListener.onConnectionRequest(this.zzaFs, this.zzaFy, this.zzaFz, this.zzarU);
            }

            public void zzmw() {
            }

            public /* synthetic */ void zzn(Object obj) {
                zza((ConnectionRequestListener) obj);
            }
        }

        zzd(com.google.android.gms.common.api.zza.zzb<StartAdvertisingResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_nearby_connection_Connections_StartAdvertisingResult, com.google.android.gms.common.api.zzi<ConnectionRequestListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionRequestListener) {
            this.zzOs = (com.google.android.gms.common.api.zza.zzb) zzu.zzu(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_nearby_connection_Connections_StartAdvertisingResult);
            this.zzaFx = (com.google.android.gms.common.api.zzi) zzu.zzu(com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionRequestListener);
        }

        public void onConnectionRequest(String remoteEndpointId, String remoteDeviceId, String remoteEndpointName, byte[] payload) throws RemoteException {
            this.zzaFx.zza(new C04941(this, remoteEndpointId, remoteDeviceId, remoteEndpointName, payload));
        }

        public void zzj(int i, String str) throws RemoteException {
            this.zzOs.zzm(new zze(new Status(i), str));
        }
    }

    private static final class zzf extends zzoo {
        private final com.google.android.gms.common.api.zza.zzb<Status> zzOs;
        private final com.google.android.gms.common.api.zzi<EndpointDiscoveryListener> zzaFx;

        /* renamed from: com.google.android.gms.internal.zzop.zzf.1 */
        class C04951 implements com.google.android.gms.common.api.zzi.zzb<EndpointDiscoveryListener> {
            final /* synthetic */ String val$name;
            final /* synthetic */ String zzaFC;
            final /* synthetic */ String zzaFD;
            final /* synthetic */ String zzaFE;
            final /* synthetic */ zzf zzaFF;

            C04951(zzf com_google_android_gms_internal_zzop_zzf, String str, String str2, String str3, String str4) {
                this.zzaFF = com_google_android_gms_internal_zzop_zzf;
                this.zzaFC = str;
                this.zzaFD = str2;
                this.zzaFE = str3;
                this.val$name = str4;
            }

            public void zza(EndpointDiscoveryListener endpointDiscoveryListener) {
                endpointDiscoveryListener.onEndpointFound(this.zzaFC, this.zzaFD, this.zzaFE, this.val$name);
            }

            public void zzmw() {
            }

            public /* synthetic */ void zzn(Object obj) {
                zza((EndpointDiscoveryListener) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzop.zzf.2 */
        class C04962 implements com.google.android.gms.common.api.zzi.zzb<EndpointDiscoveryListener> {
            final /* synthetic */ String zzaFC;
            final /* synthetic */ zzf zzaFF;

            C04962(zzf com_google_android_gms_internal_zzop_zzf, String str) {
                this.zzaFF = com_google_android_gms_internal_zzop_zzf;
                this.zzaFC = str;
            }

            public void zza(EndpointDiscoveryListener endpointDiscoveryListener) {
                endpointDiscoveryListener.onEndpointLost(this.zzaFC);
            }

            public void zzmw() {
            }

            public /* synthetic */ void zzn(Object obj) {
                zza((EndpointDiscoveryListener) obj);
            }
        }

        zzf(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, com.google.android.gms.common.api.zzi<EndpointDiscoveryListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_EndpointDiscoveryListener) {
            this.zzOs = (com.google.android.gms.common.api.zza.zzb) zzu.zzu(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status);
            this.zzaFx = (com.google.android.gms.common.api.zzi) zzu.zzu(com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_EndpointDiscoveryListener);
        }

        public void onEndpointFound(String endpointId, String deviceId, String serviceId, String name) throws RemoteException {
            this.zzaFx.zza(new C04951(this, endpointId, deviceId, serviceId, name));
        }

        public void onEndpointLost(String endpointId) throws RemoteException {
            this.zzaFx.zza(new C04962(this, endpointId));
        }

        public void zzhX(int i) throws RemoteException {
            this.zzOs.zzm(new Status(i));
        }
    }

    private static final class zza extends zzb {
        private final com.google.android.gms.common.api.zza.zzb<Status> zzOs;

        public zza(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, com.google.android.gms.common.api.zzi<MessageListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener) {
            super(com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener);
            this.zzOs = (com.google.android.gms.common.api.zza.zzb) zzu.zzu(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status);
        }

        public void zzia(int i) throws RemoteException {
            this.zzOs.zzm(new Status(i));
        }
    }

    private static final class zzc extends zzb {
        private final com.google.android.gms.common.api.zza.zzb<Status> zzOs;
        private final com.google.android.gms.common.api.zzi<ConnectionResponseCallback> zzaFv;

        /* renamed from: com.google.android.gms.internal.zzop.zzc.1 */
        class C04931 implements com.google.android.gms.common.api.zzi.zzb<ConnectionResponseCallback> {
            final /* synthetic */ int zzUK;
            final /* synthetic */ String zzaFs;
            final /* synthetic */ zzc zzaFw;
            final /* synthetic */ byte[] zzarU;

            C04931(zzc com_google_android_gms_internal_zzop_zzc, String str, int i, byte[] bArr) {
                this.zzaFw = com_google_android_gms_internal_zzop_zzc;
                this.zzaFs = str;
                this.zzUK = i;
                this.zzarU = bArr;
            }

            public void zza(ConnectionResponseCallback connectionResponseCallback) {
                connectionResponseCallback.onConnectionResponse(this.zzaFs, new Status(this.zzUK), this.zzarU);
            }

            public void zzmw() {
            }

            public /* synthetic */ void zzn(Object obj) {
                zza((ConnectionResponseCallback) obj);
            }
        }

        public zzc(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, com.google.android.gms.common.api.zzi<ConnectionResponseCallback> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionResponseCallback, com.google.android.gms.common.api.zzi<MessageListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener) {
            super(com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener);
            this.zzOs = (com.google.android.gms.common.api.zza.zzb) zzu.zzu(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status);
            this.zzaFv = (com.google.android.gms.common.api.zzi) zzu.zzu(com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionResponseCallback);
        }

        public void zza(String str, int i, byte[] bArr) throws RemoteException {
            this.zzaFv.zza(new C04931(this, str, i, bArr));
        }

        public void zzhZ(int i) throws RemoteException {
            this.zzOs.zzm(new Status(i));
        }
    }

    public zzop(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 54, connectionCallbacks, onConnectionFailedListener);
        this.zzaoX = (long) hashCode();
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                ((zzos) zznM()).zzE(this.zzaoX);
            } catch (Throwable e) {
                Log.w("NearbyConnectionsClient", "Failed to notify client disconnect.", e);
            }
        }
        super.disconnect();
    }

    protected String getServiceDescriptor() {
        return "com.google.android.gms.nearby.internal.connection.INearbyConnectionService";
    }

    protected String getStartServiceAction() {
        return "com.google.android.gms.nearby.connection.service.START";
    }

    protected /* synthetic */ IInterface zzT(IBinder iBinder) {
        return zzda(iBinder);
    }

    public void zza(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, String str, long j, com.google.android.gms.common.api.zzi<EndpointDiscoveryListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_EndpointDiscoveryListener) throws RemoteException {
        ((zzos) zznM()).zza(new zzf(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_EndpointDiscoveryListener), str, j, this.zzaoX);
    }

    public void zza(com.google.android.gms.common.api.zza.zzb<StartAdvertisingResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_nearby_connection_Connections_StartAdvertisingResult, String str, AppMetadata appMetadata, long j, com.google.android.gms.common.api.zzi<ConnectionRequestListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionRequestListener) throws RemoteException {
        ((zzos) zznM()).zza(new zzd(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_nearby_connection_Connections_StartAdvertisingResult, com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionRequestListener), str, appMetadata, j, this.zzaoX);
    }

    public void zza(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, String str, String str2, byte[] bArr, com.google.android.gms.common.api.zzi<ConnectionResponseCallback> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionResponseCallback, com.google.android.gms.common.api.zzi<MessageListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener) throws RemoteException {
        ((zzos) zznM()).zza(new zzc(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_ConnectionResponseCallback, com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener), str, str2, bArr, this.zzaoX);
    }

    public void zza(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, String str, byte[] bArr, com.google.android.gms.common.api.zzi<MessageListener> com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener) throws RemoteException {
        ((zzos) zznM()).zza(new zza(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, com_google_android_gms_common_api_zzi_com_google_android_gms_nearby_connection_Connections_MessageListener), str, bArr, this.zzaoX);
    }

    public void zza(String[] strArr, byte[] bArr) {
        try {
            ((zzos) zznM()).zza(strArr, bArr, this.zzaoX);
        } catch (Throwable e) {
            Log.w("NearbyConnectionsClient", "Couldn't send reliable message", e);
        }
    }

    public void zzb(String[] strArr, byte[] bArr) {
        try {
            ((zzos) zznM()).zzb(strArr, bArr, this.zzaoX);
        } catch (Throwable e) {
            Log.w("NearbyConnectionsClient", "Couldn't send unreliable message", e);
        }
    }

    public void zzdQ(String str) {
        try {
            ((zzos) zznM()).zzf(str, this.zzaoX);
        } catch (Throwable e) {
            Log.w("NearbyConnectionsClient", "Couldn't stop discovery", e);
        }
    }

    public void zzdR(String str) {
        try {
            ((zzos) zznM()).zzg(str, this.zzaoX);
        } catch (Throwable e) {
            Log.w("NearbyConnectionsClient", "Couldn't disconnect from endpoint", e);
        }
    }

    protected zzos zzda(IBinder iBinder) {
        return com.google.android.gms.internal.zzos.zza.zzdc(iBinder);
    }

    public void zzp(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, String str) throws RemoteException {
        ((zzos) zznM()).zza(new C08491(this, com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status), str, this.zzaoX);
    }

    public String zzwR() {
        try {
            return ((zzos) zznM()).zzQ(this.zzaoX);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public String zzwS() {
        try {
            return ((zzos) zznM()).zzwS();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void zzwT() {
        try {
            ((zzos) zznM()).zzN(this.zzaoX);
        } catch (Throwable e) {
            Log.w("NearbyConnectionsClient", "Couldn't stop advertising", e);
        }
    }

    public void zzwU() {
        try {
            ((zzos) zznM()).zzP(this.zzaoX);
        } catch (Throwable e) {
            Log.w("NearbyConnectionsClient", "Couldn't stop all endpoints", e);
        }
    }
}
