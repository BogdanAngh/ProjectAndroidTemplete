package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.SensorsApi;
import com.google.android.gms.fitness.data.zzj;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRegistrationRequest;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.SensorUnregistrationRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

public class zzne implements SensorsApi {

    private interface zza {
        void zzqR();
    }

    /* renamed from: com.google.android.gms.internal.zzne.3 */
    class C04893 implements zza {
        final /* synthetic */ zzne zzals;
        final /* synthetic */ OnDataPointListener zzalw;

        C04893(zzne com_google_android_gms_internal_zzne, OnDataPointListener onDataPointListener) {
            this.zzals = com_google_android_gms_internal_zzne;
            this.zzalw = onDataPointListener;
        }

        public void zzqR() {
            com.google.android.gms.fitness.data.zzk.zza.zzqH().zzc(this.zzalw);
        }
    }

    private static class zzb extends com.google.android.gms.internal.zzmg.zza {
        private final com.google.android.gms.common.api.zza.zzb<DataSourcesResult> zzOs;

        private zzb(com.google.android.gms.common.api.zza.zzb<DataSourcesResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_DataSourcesResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_DataSourcesResult;
        }

        public void zza(DataSourcesResult dataSourcesResult) {
            this.zzOs.zzm(dataSourcesResult);
        }
    }

    private static class zzc extends com.google.android.gms.internal.zzmu.zza {
        private final com.google.android.gms.common.api.zza.zzb<Status> zzOs;
        private final zza zzalA;

        private zzc(com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, zza com_google_android_gms_internal_zzne_zza) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status;
            this.zzalA = com_google_android_gms_internal_zzne_zza;
        }

        public void zzm(Status status) {
            if (this.zzalA != null && status.isSuccess()) {
                this.zzalA.zzqR();
            }
            this.zzOs.zzm(status);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzne.1 */
    class C08691 extends zza<DataSourcesResult> {
        final /* synthetic */ DataSourcesRequest zzalr;
        final /* synthetic */ zzne zzals;

        C08691(zzne com_google_android_gms_internal_zzne, GoogleApiClient googleApiClient, DataSourcesRequest dataSourcesRequest) {
            this.zzals = com_google_android_gms_internal_zzne;
            this.zzalr = dataSourcesRequest;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzG(x0);
        }

        protected DataSourcesResult zzG(Status status) {
            return DataSourcesResult.zzL(status);
        }

        protected void zza(zzmc com_google_android_gms_internal_zzmc) throws RemoteException {
            ((zzmn) com_google_android_gms_internal_zzmc.zznM()).zza(new DataSourcesRequest(this.zzalr, new zzb(null), com_google_android_gms_internal_zzmc.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzne.2 */
    class C10302 extends zzc {
        final /* synthetic */ zzne zzals;
        final /* synthetic */ SensorRequest zzalt;
        final /* synthetic */ zzj zzalu;
        final /* synthetic */ PendingIntent zzalv;

        C10302(zzne com_google_android_gms_internal_zzne, GoogleApiClient googleApiClient, SensorRequest sensorRequest, zzj com_google_android_gms_fitness_data_zzj, PendingIntent pendingIntent) {
            this.zzals = com_google_android_gms_internal_zzne;
            this.zzalt = sensorRequest;
            this.zzalu = com_google_android_gms_fitness_data_zzj;
            this.zzalv = pendingIntent;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzmc com_google_android_gms_internal_zzmc) throws RemoteException {
            zzmu com_google_android_gms_internal_zzng = new zzng(this);
            String packageName = com_google_android_gms_internal_zzmc.getContext().getPackageName();
            ((zzmn) com_google_android_gms_internal_zzmc.zznM()).zza(new SensorRegistrationRequest(this.zzalt, this.zzalu, this.zzalv, com_google_android_gms_internal_zzng, packageName));
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzne.4 */
    class C10314 extends zzc {
        final /* synthetic */ zzne zzals;
        final /* synthetic */ zza zzalx;
        final /* synthetic */ zzj zzaly;
        final /* synthetic */ PendingIntent zzalz;

        C10314(zzne com_google_android_gms_internal_zzne, GoogleApiClient googleApiClient, zza com_google_android_gms_internal_zzne_zza, zzj com_google_android_gms_fitness_data_zzj, PendingIntent pendingIntent) {
            this.zzals = com_google_android_gms_internal_zzne;
            this.zzalx = com_google_android_gms_internal_zzne_zza;
            this.zzaly = com_google_android_gms_fitness_data_zzj;
            this.zzalz = pendingIntent;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzmc com_google_android_gms_internal_zzmc) throws RemoteException {
            ((zzmn) com_google_android_gms_internal_zzmc.zznM()).zza(new SensorUnregistrationRequest(this.zzaly, this.zzalz, new zzc(this.zzalx, null), com_google_android_gms_internal_zzmc.getContext().getPackageName()));
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, zzj com_google_android_gms_fitness_data_zzj, PendingIntent pendingIntent, zza com_google_android_gms_internal_zzne_zza) {
        return googleApiClient.zzb(new C10314(this, googleApiClient, com_google_android_gms_internal_zzne_zza, com_google_android_gms_fitness_data_zzj, pendingIntent));
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, SensorRequest sensorRequest, zzj com_google_android_gms_fitness_data_zzj, PendingIntent pendingIntent) {
        return googleApiClient.zza(new C10302(this, googleApiClient, sensorRequest, com_google_android_gms_fitness_data_zzj, pendingIntent));
    }

    public PendingResult<Status> add(GoogleApiClient client, SensorRequest request, PendingIntent intent) {
        return zza(client, request, null, intent);
    }

    public PendingResult<Status> add(GoogleApiClient client, SensorRequest request, OnDataPointListener listener) {
        return zza(client, request, com.google.android.gms.fitness.data.zzk.zza.zzqH().zza(listener), null);
    }

    public PendingResult<DataSourcesResult> findDataSources(GoogleApiClient client, DataSourcesRequest request) {
        return client.zza(new C08691(this, client, request));
    }

    public PendingResult<Status> remove(GoogleApiClient client, PendingIntent pendingIntent) {
        return zza(client, null, pendingIntent, null);
    }

    public PendingResult<Status> remove(GoogleApiClient client, OnDataPointListener listener) {
        zzj zzb = com.google.android.gms.fitness.data.zzk.zza.zzqH().zzb(listener);
        return zzb == null ? new zzmw(Status.zzXP) : zza(client, zzb, null, new C04893(this, listener));
    }
}
