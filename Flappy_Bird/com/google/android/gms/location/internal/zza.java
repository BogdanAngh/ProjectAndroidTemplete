package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionApi;

public class zza implements ActivityRecognitionApi {

    private static abstract class zza extends com.google.android.gms.location.ActivityRecognition.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zza.1 */
    class C10601 extends zza {
        final /* synthetic */ long zzayt;
        final /* synthetic */ PendingIntent zzayu;
        final /* synthetic */ zza zzayv;

        C10601(zza com_google_android_gms_location_internal_zza, GoogleApiClient googleApiClient, long j, PendingIntent pendingIntent) {
            this.zzayv = com_google_android_gms_location_internal_zza;
            this.zzayt = j;
            this.zzayu = pendingIntent;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayt, this.zzayu);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zza.2 */
    class C10612 extends zza {
        final /* synthetic */ PendingIntent zzayu;
        final /* synthetic */ zza zzayv;

        C10612(zza com_google_android_gms_location_internal_zza, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
            this.zzayv = com_google_android_gms_location_internal_zza;
            this.zzayu = pendingIntent;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayu);
            setResult(Status.zzXP);
        }
    }

    public PendingResult<Status> removeActivityUpdates(GoogleApiClient client, PendingIntent callbackIntent) {
        return client.zzb(new C10612(this, client, callbackIntent));
    }

    public PendingResult<Status> requestActivityUpdates(GoogleApiClient client, long detectionIntervalMillis, PendingIntent callbackIntent) {
        return client.zzb(new C10601(this, client, detectionIntervalMillis, callbackIntent));
    }
}
