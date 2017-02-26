package com.google.android.gms.appstate;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzjb;

@Deprecated
public final class AppStateManager {
    public static final Api<NoOptions> API;
    public static final Scope SCOPE_APP_STATE;
    static final ClientKey<zzjb> zzNX;
    private static final com.google.android.gms.common.api.Api.zza<zzjb, NoOptions> zzNY;

    /* renamed from: com.google.android.gms.appstate.AppStateManager.1 */
    static class C03961 implements com.google.android.gms.common.api.Api.zza<zzjb, NoOptions> {
        C03961() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, com.google.android.gms.common.internal.zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzc(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzjb zzc(Context context, Looper looper, com.google.android.gms.common.internal.zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzjb(context, looper, com_google_android_gms_common_internal_zze, connectionCallbacks, onConnectionFailedListener);
        }
    }

    public interface StateConflictResult extends Releasable, Result {
        byte[] getLocalData();

        String getResolvedVersion();

        byte[] getServerData();

        int getStateKey();
    }

    public interface StateDeletedResult extends Result {
        int getStateKey();
    }

    public interface StateListResult extends Result {
        AppStateBuffer getStateBuffer();
    }

    public interface StateLoadedResult extends Releasable, Result {
        byte[] getLocalData();

        int getStateKey();
    }

    public interface StateResult extends Releasable, Result {
        StateConflictResult getConflictResult();

        StateLoadedResult getLoadedResult();
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.2 */
    static class C06022 implements StateResult {
        final /* synthetic */ Status zzOl;

        C06022(Status status) {
            this.zzOl = status;
        }

        public StateConflictResult getConflictResult() {
            return null;
        }

        public StateLoadedResult getLoadedResult() {
            return null;
        }

        public Status getStatus() {
            return this.zzOl;
        }

        public void release() {
        }
    }

    public static abstract class zza<R extends Result> extends com.google.android.gms.common.api.zza.zza<R, zzjb> {
        public zza(GoogleApiClient googleApiClient) {
            super(AppStateManager.zzNX, googleApiClient);
        }
    }

    private static abstract class zzb extends zza<StateDeletedResult> {
        zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }
    }

    private static abstract class zzc extends zza<StateListResult> {

        /* renamed from: com.google.android.gms.appstate.AppStateManager.zzc.1 */
        class C06041 implements StateListResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ zzc zzOr;

            C06041(zzc com_google_android_gms_appstate_AppStateManager_zzc, Status status) {
                this.zzOr = com_google_android_gms_appstate_AppStateManager_zzc;
                this.zzOl = status;
            }

            public AppStateBuffer getStateBuffer() {
                return new AppStateBuffer(null);
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzg(x0);
        }

        public StateListResult zzg(Status status) {
            return new C06041(this, status);
        }
    }

    private static abstract class zzd extends zza<Status> {
        public zzd(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    private static abstract class zze extends zza<StateResult> {
        public zze(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzh(x0);
        }

        public StateResult zzh(Status status) {
            return AppStateManager.zzd(status);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.3 */
    static class C08973 extends zze {
        final /* synthetic */ int zzOm;
        final /* synthetic */ byte[] zzOn;

        C08973(GoogleApiClient googleApiClient, int i, byte[] bArr) {
            this.zzOm = i;
            this.zzOn = bArr;
            super(googleApiClient);
        }

        protected void zza(zzjb com_google_android_gms_internal_zzjb) throws RemoteException {
            com_google_android_gms_internal_zzjb.zza(null, this.zzOm, this.zzOn);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.4 */
    static class C08984 extends zze {
        final /* synthetic */ int zzOm;
        final /* synthetic */ byte[] zzOn;

        C08984(GoogleApiClient googleApiClient, int i, byte[] bArr) {
            this.zzOm = i;
            this.zzOn = bArr;
            super(googleApiClient);
        }

        protected void zza(zzjb com_google_android_gms_internal_zzjb) throws RemoteException {
            com_google_android_gms_internal_zzjb.zza(this, this.zzOm, this.zzOn);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.5 */
    static class C08995 extends zzb {
        final /* synthetic */ int zzOm;

        /* renamed from: com.google.android.gms.appstate.AppStateManager.5.1 */
        class C06031 implements StateDeletedResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ C08995 zzOo;

            C06031(C08995 c08995, Status status) {
                this.zzOo = c08995;
                this.zzOl = status;
            }

            public int getStateKey() {
                return this.zzOo.zzOm;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        C08995(GoogleApiClient googleApiClient, int i) {
            this.zzOm = i;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzf(x0);
        }

        protected void zza(zzjb com_google_android_gms_internal_zzjb) throws RemoteException {
            com_google_android_gms_internal_zzjb.zza(this, this.zzOm);
        }

        public StateDeletedResult zzf(Status status) {
            return new C06031(this, status);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.6 */
    static class C09006 extends zze {
        final /* synthetic */ int zzOm;

        C09006(GoogleApiClient googleApiClient, int i) {
            this.zzOm = i;
            super(googleApiClient);
        }

        protected void zza(zzjb com_google_android_gms_internal_zzjb) throws RemoteException {
            com_google_android_gms_internal_zzjb.zzb(this, this.zzOm);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.7 */
    static class C09017 extends zzc {
        C09017(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        protected void zza(zzjb com_google_android_gms_internal_zzjb) throws RemoteException {
            com_google_android_gms_internal_zzjb.zza((com.google.android.gms.common.api.zza.zzb) this);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.8 */
    static class C09028 extends zze {
        final /* synthetic */ int zzOm;
        final /* synthetic */ String zzOp;
        final /* synthetic */ byte[] zzOq;

        C09028(GoogleApiClient googleApiClient, int i, String str, byte[] bArr) {
            this.zzOm = i;
            this.zzOp = str;
            this.zzOq = bArr;
            super(googleApiClient);
        }

        protected void zza(zzjb com_google_android_gms_internal_zzjb) throws RemoteException {
            com_google_android_gms_internal_zzjb.zza(this, this.zzOm, this.zzOp, this.zzOq);
        }
    }

    /* renamed from: com.google.android.gms.appstate.AppStateManager.9 */
    static class C09039 extends zzd {
        C09039(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        protected void zza(zzjb com_google_android_gms_internal_zzjb) throws RemoteException {
            com_google_android_gms_internal_zzjb.zzb(this);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C03961();
        SCOPE_APP_STATE = new Scope(Scopes.APP_STATE);
        API = new Api("AppStateManager.API", zzNY, zzNX, SCOPE_APP_STATE);
    }

    private AppStateManager() {
    }

    public static PendingResult<StateDeletedResult> delete(GoogleApiClient googleApiClient, int stateKey) {
        return googleApiClient.zzb(new C08995(googleApiClient, stateKey));
    }

    public static int getMaxNumKeys(GoogleApiClient googleApiClient) {
        return zza(googleApiClient).zzkW();
    }

    public static int getMaxStateSize(GoogleApiClient googleApiClient) {
        return zza(googleApiClient).zzkV();
    }

    public static PendingResult<StateListResult> list(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new C09017(googleApiClient));
    }

    public static PendingResult<StateResult> load(GoogleApiClient googleApiClient, int stateKey) {
        return googleApiClient.zza(new C09006(googleApiClient, stateKey));
    }

    public static PendingResult<StateResult> resolve(GoogleApiClient googleApiClient, int stateKey, String resolvedVersion, byte[] resolvedData) {
        return googleApiClient.zzb(new C09028(googleApiClient, stateKey, resolvedVersion, resolvedData));
    }

    public static PendingResult<Status> signOut(GoogleApiClient googleApiClient) {
        return googleApiClient.zzb(new C09039(googleApiClient));
    }

    public static void update(GoogleApiClient googleApiClient, int stateKey, byte[] data) {
        googleApiClient.zzb(new C08973(googleApiClient, stateKey, data));
    }

    public static PendingResult<StateResult> updateImmediate(GoogleApiClient googleApiClient, int stateKey, byte[] data) {
        return googleApiClient.zzb(new C08984(googleApiClient, stateKey, data));
    }

    public static zzjb zza(GoogleApiClient googleApiClient) {
        zzu.zzb(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        zzu.zza(googleApiClient.isConnected(), (Object) "GoogleApiClient must be connected.");
        zzu.zza(googleApiClient.zza(API), (Object) "GoogleApiClient is not configured to use the AppState API. Pass AppStateManager.API into GoogleApiClient.Builder#addApi() to use this feature.");
        return (zzjb) googleApiClient.zza(zzNX);
    }

    private static StateResult zzd(Status status) {
        return new C06022(status);
    }
}
