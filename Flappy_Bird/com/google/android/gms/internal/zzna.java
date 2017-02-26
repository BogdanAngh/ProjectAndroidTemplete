package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.fitness.ConfigApi;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.request.DataTypeReadRequest;
import com.google.android.gms.fitness.request.DisableFitRequest;
import com.google.android.gms.fitness.result.DataTypeResult;

public class zzna implements ConfigApi {

    private static class zza extends com.google.android.gms.internal.zzmh.zza {
        private final zzb<DataTypeResult> zzOs;

        private zza(zzb<DataTypeResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_DataTypeResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_DataTypeResult;
        }

        public void zza(DataTypeResult dataTypeResult) {
            this.zzOs.zzm(dataTypeResult);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzna.1 */
    class C08631 extends zza<DataTypeResult> {
        final /* synthetic */ DataTypeCreateRequest zzalc;
        final /* synthetic */ zzna zzald;

        C08631(zzna com_google_android_gms_internal_zzna, GoogleApiClient googleApiClient, DataTypeCreateRequest dataTypeCreateRequest) {
            this.zzald = com_google_android_gms_internal_zzna;
            this.zzalc = dataTypeCreateRequest;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzC(x0);
        }

        protected DataTypeResult zzC(Status status) {
            return DataTypeResult.zzM(status);
        }

        protected void zza(zzly com_google_android_gms_internal_zzly) throws RemoteException {
            ((zzmj) com_google_android_gms_internal_zzly.zznM()).zza(new DataTypeCreateRequest(this.zzalc, new zza(null), com_google_android_gms_internal_zzly.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzna.2 */
    class C08642 extends zza<DataTypeResult> {
        final /* synthetic */ zzna zzald;
        final /* synthetic */ String zzale;

        C08642(zzna com_google_android_gms_internal_zzna, GoogleApiClient googleApiClient, String str) {
            this.zzald = com_google_android_gms_internal_zzna;
            this.zzale = str;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzC(x0);
        }

        protected DataTypeResult zzC(Status status) {
            return DataTypeResult.zzM(status);
        }

        protected void zza(zzly com_google_android_gms_internal_zzly) throws RemoteException {
            ((zzmj) com_google_android_gms_internal_zzly.zznM()).zza(new DataTypeReadRequest(this.zzale, new zza(null), com_google_android_gms_internal_zzly.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzna.3 */
    class C10243 extends zzc {
        final /* synthetic */ zzna zzald;

        C10243(zzna com_google_android_gms_internal_zzna, GoogleApiClient googleApiClient) {
            this.zzald = com_google_android_gms_internal_zzna;
            super(googleApiClient);
        }

        protected void zza(zzly com_google_android_gms_internal_zzly) throws RemoteException {
            ((zzmj) com_google_android_gms_internal_zzly.zznM()).zza(new DisableFitRequest(new zzng(this), com_google_android_gms_internal_zzly.getContext().getPackageName()));
        }
    }

    public PendingResult<DataTypeResult> createCustomDataType(GoogleApiClient client, DataTypeCreateRequest request) {
        return client.zzb(new C08631(this, client, request));
    }

    public PendingResult<Status> disableFit(GoogleApiClient client) {
        return client.zzb(new C10243(this, client));
    }

    public PendingResult<DataTypeResult> readDataType(GoogleApiClient client, String dataTypeName) {
        return client.zza(new C08642(this, client, dataTypeName));
    }
}
