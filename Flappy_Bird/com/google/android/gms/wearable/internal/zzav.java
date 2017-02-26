package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

public final class zzav implements MessageApi {

    public static class zzb implements SendMessageResult {
        private final Status zzOt;
        private final int zzacR;

        public zzb(Status status, int i) {
            this.zzOt = status;
            this.zzacR = i;
        }

        public int getRequestId() {
            return this.zzacR;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzav.1 */
    class C08791 extends zzf<SendMessageResult> {
        final /* synthetic */ byte[] zzOn;
        final /* synthetic */ String zzaTI;
        final /* synthetic */ String zzaUC;
        final /* synthetic */ zzav zzaUD;

        C08791(zzav com_google_android_gms_wearable_internal_zzav, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr) {
            this.zzaUD = com_google_android_gms_wearable_internal_zzav;
            this.zzaTI = str;
            this.zzaUC = str2;
            this.zzOn = bArr;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbh(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaTI, this.zzaUC, this.zzOn);
        }

        protected SendMessageResult zzbh(Status status) {
            return new zzb(status, -1);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzav.2 */
    class C08802 extends zzf<Status> {
        final /* synthetic */ zzav zzaUD;
        final /* synthetic */ MessageListener zzaUE;

        C08802(zzav com_google_android_gms_wearable_internal_zzav, GoogleApiClient googleApiClient, MessageListener messageListener) {
            this.zzaUD = com_google_android_gms_wearable_internal_zzav;
            this.zzaUE = messageListener;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUE);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    private static final class zza extends zzf<Status> {
        private MessageListener zzaUF;
        private IntentFilter[] zzaUk;

        private zza(GoogleApiClient googleApiClient, MessageListener messageListener, IntentFilter[] intentFilterArr) {
            super(googleApiClient);
            this.zzaUF = messageListener;
            this.zzaUk = intentFilterArr;
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUF, this.zzaUk);
            this.zzaUF = null;
            this.zzaUk = null;
        }

        public Status zzb(Status status) {
            this.zzaUF = null;
            this.zzaUk = null;
            return status;
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, MessageListener messageListener, IntentFilter[] intentFilterArr) {
        return googleApiClient.zza(new zza(messageListener, intentFilterArr, null));
    }

    public PendingResult<Status> addListener(GoogleApiClient client, MessageListener listener) {
        return zza(client, listener, null);
    }

    public PendingResult<Status> removeListener(GoogleApiClient client, MessageListener listener) {
        return client.zza(new C08802(this, client, listener));
    }

    public PendingResult<SendMessageResult> sendMessage(GoogleApiClient client, String nodeId, String action, byte[] data) {
        return client.zza(new C08791(this, client, nodeId, action, data));
    }
}
