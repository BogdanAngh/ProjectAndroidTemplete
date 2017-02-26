package com.google.android.gms.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DailyTotalRequest;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataInsertRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;

public class zznb implements HistoryApi {

    private static class zza extends com.google.android.gms.internal.zzmf.zza {
        private final zzb<DataReadResult> zzOs;
        private int zzalm;
        private DataReadResult zzaln;

        private zza(zzb<DataReadResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_DataReadResult) {
            this.zzalm = 0;
            this.zzaln = null;
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_DataReadResult;
        }

        public void zza(DataReadResult dataReadResult) {
            synchronized (this) {
                Log.v("Fitness", "Received batch result");
                if (this.zzaln == null) {
                    this.zzaln = dataReadResult;
                } else {
                    this.zzaln.zzb(dataReadResult);
                }
                this.zzalm++;
                if (this.zzalm == this.zzaln.zzrt()) {
                    this.zzOs.zzm(this.zzaln);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zznb.3 */
    class C08653 extends zza<DataReadResult> {
        final /* synthetic */ zznb zzalh;
        final /* synthetic */ DataReadRequest zzalj;

        C08653(zznb com_google_android_gms_internal_zznb, GoogleApiClient googleApiClient, DataReadRequest dataReadRequest) {
            this.zzalh = com_google_android_gms_internal_zznb;
            this.zzalj = dataReadRequest;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzD(x0);
        }

        protected DataReadResult zzD(Status status) {
            return DataReadResult.zza(status, this.zzalj);
        }

        protected void zza(zzlz com_google_android_gms_internal_zzlz) throws RemoteException {
            ((zzmk) com_google_android_gms_internal_zzlz.zznM()).zza(new DataReadRequest(this.zzalj, new zza(null), com_google_android_gms_internal_zzlz.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznb.4 */
    class C08664 extends zza<DailyTotalResult> {
        final /* synthetic */ zznb zzalh;
        final /* synthetic */ DataType zzalk;

        /* renamed from: com.google.android.gms.internal.zznb.4.1 */
        class C08131 extends com.google.android.gms.internal.zzme.zza {
            final /* synthetic */ C08664 zzall;

            C08131(C08664 c08664) {
                this.zzall = c08664;
            }

            public void zza(DailyTotalResult dailyTotalResult) throws RemoteException {
                this.zzall.setResult(dailyTotalResult);
            }
        }

        C08664(zznb com_google_android_gms_internal_zznb, GoogleApiClient googleApiClient, DataType dataType) {
            this.zzalh = com_google_android_gms_internal_zznb;
            this.zzalk = dataType;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzE(x0);
        }

        protected DailyTotalResult zzE(Status status) {
            return DailyTotalResult.zzK(status);
        }

        protected void zza(zzlz com_google_android_gms_internal_zzlz) throws RemoteException {
            ((zzmk) com_google_android_gms_internal_zzlz.zznM()).zza(new DailyTotalRequest(new C08131(this), this.zzalk, com_google_android_gms_internal_zzlz.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznb.1 */
    class C10251 extends zzc {
        final /* synthetic */ DataSet zzalf;
        final /* synthetic */ boolean zzalg;
        final /* synthetic */ zznb zzalh;

        C10251(zznb com_google_android_gms_internal_zznb, GoogleApiClient googleApiClient, DataSet dataSet, boolean z) {
            this.zzalh = com_google_android_gms_internal_zznb;
            this.zzalf = dataSet;
            this.zzalg = z;
            super(googleApiClient);
        }

        protected void zza(zzlz com_google_android_gms_internal_zzlz) throws RemoteException {
            ((zzmk) com_google_android_gms_internal_zzlz.zznM()).zza(new DataInsertRequest(this.zzalf, new zzng(this), com_google_android_gms_internal_zzlz.getContext().getPackageName(), this.zzalg));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznb.2 */
    class C10262 extends zzc {
        final /* synthetic */ zznb zzalh;
        final /* synthetic */ DataDeleteRequest zzali;

        C10262(zznb com_google_android_gms_internal_zznb, GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
            this.zzalh = com_google_android_gms_internal_zznb;
            this.zzali = dataDeleteRequest;
            super(googleApiClient);
        }

        protected void zza(zzlz com_google_android_gms_internal_zzlz) throws RemoteException {
            ((zzmk) com_google_android_gms_internal_zzlz.zznM()).zza(new DataDeleteRequest(this.zzali, new zzng(this), com_google_android_gms_internal_zzlz.getContext().getPackageName()));
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, DataSet dataSet, boolean z) {
        zzu.zzb((Object) dataSet, (Object) "Must set the data set");
        zzu.zza(!dataSet.getDataPoints().isEmpty(), (Object) "Cannot use an empty data set");
        zzu.zzb(dataSet.getDataSource().zzqB(), (Object) "Must set the app package name for the data source");
        return googleApiClient.zza(new C10251(this, googleApiClient, dataSet, z));
    }

    public PendingResult<Status> deleteData(GoogleApiClient client, DataDeleteRequest request) {
        return client.zza(new C10262(this, client, request));
    }

    public PendingResult<Status> insertData(GoogleApiClient client, DataSet dataSet) {
        return zza(client, dataSet, false);
    }

    public PendingResult<DailyTotalResult> readDailyTotal(GoogleApiClient client, DataType dataType) {
        return client.zza(new C08664(this, client, dataType));
    }

    public PendingResult<DataReadResult> readData(GoogleApiClient client, DataReadRequest request) {
        return client.zza(new C08653(this, client, request));
    }
}
