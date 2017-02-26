package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;
import com.google.android.gms.wearable.NodeApi.NodeListener;
import java.util.ArrayList;
import java.util.List;

public final class zzax implements NodeApi {

    public static class zzb implements GetConnectedNodesResult {
        private final Status zzOt;
        private final List<Node> zzaUJ;

        public zzb(Status status, List<Node> list) {
            this.zzOt = status;
            this.zzaUJ = list;
        }

        public List<Node> getNodes() {
            return this.zzaUJ;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    public static class zzc implements GetLocalNodeResult {
        private final Status zzOt;
        private final Node zzaUK;

        public zzc(Status status, Node node) {
            this.zzOt = status;
            this.zzaUK = node;
        }

        public Node getNode() {
            return this.zzaUK;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzax.1 */
    class C08811 extends zzf<GetLocalNodeResult> {
        final /* synthetic */ zzax zzaUG;

        C08811(zzax com_google_android_gms_wearable_internal_zzax, GoogleApiClient googleApiClient) {
            this.zzaUG = com_google_android_gms_wearable_internal_zzax;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbi(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzm(this);
        }

        protected GetLocalNodeResult zzbi(Status status) {
            return new zzc(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzax.2 */
    class C08822 extends zzf<GetConnectedNodesResult> {
        final /* synthetic */ zzax zzaUG;

        C08822(zzax com_google_android_gms_wearable_internal_zzax, GoogleApiClient googleApiClient) {
            this.zzaUG = com_google_android_gms_wearable_internal_zzax;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbj(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzn(this);
        }

        protected GetConnectedNodesResult zzbj(Status status) {
            return new zzb(status, new ArrayList());
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzax.3 */
    class C08833 extends zzf<Status> {
        final /* synthetic */ zzax zzaUG;
        final /* synthetic */ NodeListener zzaUH;

        C08833(zzax com_google_android_gms_wearable_internal_zzax, GoogleApiClient googleApiClient, NodeListener nodeListener) {
            this.zzaUG = com_google_android_gms_wearable_internal_zzax;
            this.zzaUH = nodeListener;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzb(this, this.zzaUH);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    private static final class zza extends zzf<Status> {
        private NodeListener zzaUI;

        private zza(GoogleApiClient googleApiClient, NodeListener nodeListener) {
            super(googleApiClient);
            this.zzaUI = nodeListener;
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUI);
            this.zzaUI = null;
        }

        public Status zzb(Status status) {
            this.zzaUI = null;
            return status;
        }
    }

    public PendingResult<Status> addListener(GoogleApiClient client, NodeListener listener) {
        return client.zza(new zza(listener, null));
    }

    public PendingResult<GetConnectedNodesResult> getConnectedNodes(GoogleApiClient client) {
        return client.zza(new C08822(this, client));
    }

    public PendingResult<GetLocalNodeResult> getLocalNode(GoogleApiClient client) {
        return client.zza(new C08811(this, client));
    }

    public PendingResult<Status> removeListener(GoogleApiClient client, NodeListener listener) {
        return client.zza(new C08833(this, client, listener));
    }
}
