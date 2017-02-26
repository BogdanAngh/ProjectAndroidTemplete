package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.fitness.SessionsApi;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.request.SessionRegistrationRequest;
import com.google.android.gms.fitness.request.SessionStartRequest;
import com.google.android.gms.fitness.request.SessionStopRequest;
import com.google.android.gms.fitness.request.SessionUnregistrationRequest;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.fitness.result.SessionStopResult;
import java.util.concurrent.TimeUnit;

public class zznf implements SessionsApi {

    private static class zza extends com.google.android.gms.internal.zzms.zza {
        private final com.google.android.gms.common.api.zza.zzb<SessionReadResult> zzOs;

        private zza(com.google.android.gms.common.api.zza.zzb<SessionReadResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_SessionReadResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_SessionReadResult;
        }

        public void zza(SessionReadResult sessionReadResult) throws RemoteException {
            this.zzOs.zzm(sessionReadResult);
        }
    }

    private static class zzb extends com.google.android.gms.internal.zzmt.zza {
        private final com.google.android.gms.common.api.zza.zzb<SessionStopResult> zzOs;

        private zzb(com.google.android.gms.common.api.zza.zzb<SessionStopResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_SessionStopResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_SessionStopResult;
        }

        public void zza(SessionStopResult sessionStopResult) {
            this.zzOs.zzm(sessionStopResult);
        }
    }

    /* renamed from: com.google.android.gms.internal.zznf.2 */
    class C08702 extends zza<SessionStopResult> {
        final /* synthetic */ String val$name;
        final /* synthetic */ zznf zzalC;
        final /* synthetic */ String zzalD;

        C08702(zznf com_google_android_gms_internal_zznf, GoogleApiClient googleApiClient, String str, String str2) {
            this.zzalC = com_google_android_gms_internal_zznf;
            this.val$name = str;
            this.zzalD = str2;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzH(x0);
        }

        protected SessionStopResult zzH(Status status) {
            return SessionStopResult.zzP(status);
        }

        protected void zza(zzmd com_google_android_gms_internal_zzmd) throws RemoteException {
            ((zzmo) com_google_android_gms_internal_zzmd.zznM()).zza(new SessionStopRequest(this.val$name, this.zzalD, new zzb(null), com_google_android_gms_internal_zzmd.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznf.4 */
    class C08714 extends zza<SessionReadResult> {
        final /* synthetic */ zznf zzalC;
        final /* synthetic */ SessionReadRequest zzalF;

        C08714(zznf com_google_android_gms_internal_zznf, GoogleApiClient googleApiClient, SessionReadRequest sessionReadRequest) {
            this.zzalC = com_google_android_gms_internal_zznf;
            this.zzalF = sessionReadRequest;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzI(x0);
        }

        protected SessionReadResult zzI(Status status) {
            return SessionReadResult.zzO(status);
        }

        protected void zza(zzmd com_google_android_gms_internal_zzmd) throws RemoteException {
            ((zzmo) com_google_android_gms_internal_zzmd.zznM()).zza(new SessionReadRequest(this.zzalF, new zza(null), com_google_android_gms_internal_zzmd.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznf.1 */
    class C10321 extends zzc {
        final /* synthetic */ Session zzalB;
        final /* synthetic */ zznf zzalC;

        C10321(zznf com_google_android_gms_internal_zznf, GoogleApiClient googleApiClient, Session session) {
            this.zzalC = com_google_android_gms_internal_zznf;
            this.zzalB = session;
            super(googleApiClient);
        }

        protected void zza(zzmd com_google_android_gms_internal_zzmd) throws RemoteException {
            ((zzmo) com_google_android_gms_internal_zzmd.zznM()).zza(new SessionStartRequest(this.zzalB, new zzng(this), com_google_android_gms_internal_zzmd.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznf.3 */
    class C10333 extends zzc {
        final /* synthetic */ zznf zzalC;
        final /* synthetic */ SessionInsertRequest zzalE;

        C10333(zznf com_google_android_gms_internal_zznf, GoogleApiClient googleApiClient, SessionInsertRequest sessionInsertRequest) {
            this.zzalC = com_google_android_gms_internal_zznf;
            this.zzalE = sessionInsertRequest;
            super(googleApiClient);
        }

        protected void zza(zzmd com_google_android_gms_internal_zzmd) throws RemoteException {
            ((zzmo) com_google_android_gms_internal_zzmd.zznM()).zza(new SessionInsertRequest(this.zzalE, new zzng(this), com_google_android_gms_internal_zzmd.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznf.5 */
    class C10345 extends zzc {
        final /* synthetic */ zznf zzalC;
        final /* synthetic */ int zzalG;
        final /* synthetic */ PendingIntent zzalv;

        C10345(zznf com_google_android_gms_internal_zznf, GoogleApiClient googleApiClient, PendingIntent pendingIntent, int i) {
            this.zzalC = com_google_android_gms_internal_zznf;
            this.zzalv = pendingIntent;
            this.zzalG = i;
            super(googleApiClient);
        }

        protected void zza(zzmd com_google_android_gms_internal_zzmd) throws RemoteException {
            ((zzmo) com_google_android_gms_internal_zzmd.zznM()).zza(new SessionRegistrationRequest(this.zzalv, new zzng(this), com_google_android_gms_internal_zzmd.getContext().getPackageName(), this.zzalG));
        }
    }

    /* renamed from: com.google.android.gms.internal.zznf.6 */
    class C10356 extends zzc {
        final /* synthetic */ zznf zzalC;
        final /* synthetic */ PendingIntent zzalv;

        C10356(zznf com_google_android_gms_internal_zznf, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
            this.zzalC = com_google_android_gms_internal_zznf;
            this.zzalv = pendingIntent;
            super(googleApiClient);
        }

        protected void zza(zzmd com_google_android_gms_internal_zzmd) throws RemoteException {
            ((zzmo) com_google_android_gms_internal_zzmd.zznM()).zza(new SessionUnregistrationRequest(this.zzalv, new zzng(this), com_google_android_gms_internal_zzmd.getContext().getPackageName()));
        }
    }

    private PendingResult<SessionStopResult> zza(GoogleApiClient googleApiClient, String str, String str2) {
        return googleApiClient.zzb(new C08702(this, googleApiClient, str, str2));
    }

    public PendingResult<Status> insertSession(GoogleApiClient client, SessionInsertRequest request) {
        return client.zza(new C10333(this, client, request));
    }

    public PendingResult<SessionReadResult> readSession(GoogleApiClient client, SessionReadRequest request) {
        return client.zza(new C08714(this, client, request));
    }

    public PendingResult<Status> registerForSessions(GoogleApiClient client, PendingIntent intent) {
        return zza(client, intent, 0);
    }

    public PendingResult<Status> startSession(GoogleApiClient client, Session session) {
        zzu.zzb((Object) session, (Object) "Session cannot be null");
        zzu.zzb(session.getEndTime(TimeUnit.MILLISECONDS) == 0, (Object) "Cannot start a session which has already ended");
        return client.zzb(new C10321(this, client, session));
    }

    public PendingResult<SessionStopResult> stopSession(GoogleApiClient client, String identifier) {
        return zza(client, null, identifier);
    }

    public PendingResult<Status> unregisterForSessions(GoogleApiClient client, PendingIntent intent) {
        return client.zzb(new C10356(this, client, intent));
    }

    public PendingResult<Status> zza(GoogleApiClient googleApiClient, PendingIntent pendingIntent, int i) {
        return googleApiClient.zzb(new C10345(this, googleApiClient, pendingIntent, i));
    }
}
