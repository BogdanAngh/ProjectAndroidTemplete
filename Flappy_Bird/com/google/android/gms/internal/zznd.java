package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.fitness.RecordingApi;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.ListSubscriptionsRequest;
import com.google.android.gms.fitness.request.SubscribeRequest;
import com.google.android.gms.fitness.request.UnsubscribeRequest;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

public class zznd implements RecordingApi {

    private static class zza extends com.google.android.gms.internal.zzmp.zza {
        private final zzb<ListSubscriptionsResult> zzOs;

        private zza(zzb<ListSubscriptionsResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_ListSubscriptionsResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_ListSubscriptionsResult;
        }

        public void zza(ListSubscriptionsResult listSubscriptionsResult) {
            this.zzOs.zzm(listSubscriptionsResult);
        }
    }

    /* renamed from: com.google.android.gms.internal.zznd.1 */
    class C08671 extends zza<ListSubscriptionsResult> {
        final /* synthetic */ zznd zzalo;

        C08671(zznd com_google_android_gms_internal_zznd, GoogleApiClient googleApiClient) {
            this.zzalo = com_google_android_gms_internal_zznd;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzF(x0);
        }

        protected ListSubscriptionsResult zzF(Status status) {
            return ListSubscriptionsResult.zzN(status);
        }

        protected void zza(zzmb com_google_android_gms_internal_zzmb) throws RemoteException {
            ((zzmm) com_google_android_gms_internal_zzmb.zznM()).zza(new ListSubscriptionsRequest(null, new zza(null), com_google_android_gms_internal_zzmb.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznd.2 */
    class C08682 extends zza<ListSubscriptionsResult> {
        final /* synthetic */ DataType zzalk;
        final /* synthetic */ zznd zzalo;

        C08682(zznd com_google_android_gms_internal_zznd, GoogleApiClient googleApiClient, DataType dataType) {
            this.zzalo = com_google_android_gms_internal_zznd;
            this.zzalk = dataType;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzF(x0);
        }

        protected ListSubscriptionsResult zzF(Status status) {
            return ListSubscriptionsResult.zzN(status);
        }

        protected void zza(zzmb com_google_android_gms_internal_zzmb) throws RemoteException {
            ((zzmm) com_google_android_gms_internal_zzmb.zznM()).zza(new ListSubscriptionsRequest(this.zzalk, new zza(null), com_google_android_gms_internal_zzmb.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznd.3 */
    class C10273 extends zzc {
        final /* synthetic */ zznd zzalo;
        final /* synthetic */ Subscription zzalp;

        C10273(zznd com_google_android_gms_internal_zznd, GoogleApiClient googleApiClient, Subscription subscription) {
            this.zzalo = com_google_android_gms_internal_zznd;
            this.zzalp = subscription;
            super(googleApiClient);
        }

        protected void zza(zzmb com_google_android_gms_internal_zzmb) throws RemoteException {
            ((zzmm) com_google_android_gms_internal_zzmb.zznM()).zza(new SubscribeRequest(this.zzalp, false, new zzng(this), com_google_android_gms_internal_zzmb.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznd.4 */
    class C10284 extends zzc {
        final /* synthetic */ DataType zzalk;
        final /* synthetic */ zznd zzalo;

        C10284(zznd com_google_android_gms_internal_zznd, GoogleApiClient googleApiClient, DataType dataType) {
            this.zzalo = com_google_android_gms_internal_zznd;
            this.zzalk = dataType;
            super(googleApiClient);
        }

        protected void zza(zzmb com_google_android_gms_internal_zzmb) throws RemoteException {
            ((zzmm) com_google_android_gms_internal_zzmb.zznM()).zza(new UnsubscribeRequest(this.zzalk, null, new zzng(this), com_google_android_gms_internal_zzmb.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznd.5 */
    class C10295 extends zzc {
        final /* synthetic */ zznd zzalo;
        final /* synthetic */ DataSource zzalq;

        C10295(zznd com_google_android_gms_internal_zznd, GoogleApiClient googleApiClient, DataSource dataSource) {
            this.zzalo = com_google_android_gms_internal_zznd;
            this.zzalq = dataSource;
            super(googleApiClient);
        }

        protected void zza(zzmb com_google_android_gms_internal_zzmb) throws RemoteException {
            ((zzmm) com_google_android_gms_internal_zzmb.zznM()).zza(new UnsubscribeRequest(null, this.zzalq, new zzng(this), com_google_android_gms_internal_zzmb.getContext().getPackageName()));
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, Subscription subscription) {
        return googleApiClient.zza(new C10273(this, googleApiClient, subscription));
    }

    public PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient client) {
        return client.zza(new C08671(this, client));
    }

    public PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient client, DataType dataType) {
        return client.zza(new C08682(this, client, dataType));
    }

    public PendingResult<Status> subscribe(GoogleApiClient client, DataSource dataSource) {
        return zza(client, new com.google.android.gms.fitness.data.Subscription.zza().zzb(dataSource).zzqN());
    }

    public PendingResult<Status> subscribe(GoogleApiClient client, DataType dataType) {
        return zza(client, new com.google.android.gms.fitness.data.Subscription.zza().zzb(dataType).zzqN());
    }

    public PendingResult<Status> unsubscribe(GoogleApiClient client, DataSource dataSource) {
        return client.zzb(new C10295(this, client, dataSource));
    }

    public PendingResult<Status> unsubscribe(GoogleApiClient client, DataType dataType) {
        return client.zzb(new C10284(this, client, dataType));
    }

    public PendingResult<Status> unsubscribe(GoogleApiClient client, Subscription subscription) {
        return subscription.getDataType() == null ? unsubscribe(client, subscription.getDataSource()) : unsubscribe(client, subscription.getDataType());
    }
}
