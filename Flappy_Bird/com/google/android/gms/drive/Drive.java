package com.google.android.gms.drive;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.drive.internal.zzaa;
import com.google.android.gms.drive.internal.zzq;
import com.google.android.gms.drive.internal.zzs;
import com.google.android.gms.drive.internal.zzv;
import com.google.android.gms.drive.internal.zzy;

public final class Drive {
    public static final Api<NoOptions> API;
    public static final DriveApi DriveApi;
    public static final DrivePreferencesApi DrivePreferencesApi;
    public static final Scope SCOPE_APPFOLDER;
    public static final Scope SCOPE_FILE;
    public static final ClientKey<zzs> zzNX;
    public static final Scope zzacY;
    public static final Scope zzacZ;
    public static final Api<zzb> zzada;
    public static final zzc zzadb;
    public static final zzf zzadc;

    public static abstract class zza<O extends ApiOptions> implements com.google.android.gms.common.api.Api.zza<zzs, O> {
        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        protected abstract Bundle zza(O o);

        public zzs zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, O o, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzs(context, looper, com_google_android_gms_common_internal_zze, connectionCallbacks, onConnectionFailedListener, zza(o));
        }
    }

    /* renamed from: com.google.android.gms.drive.Drive.1 */
    static class C07591 extends zza<NoOptions> {
        C07591() {
        }

        protected Bundle zza(NoOptions noOptions) {
            return new Bundle();
        }
    }

    /* renamed from: com.google.android.gms.drive.Drive.2 */
    static class C07602 extends zza<zzb> {
        C07602() {
        }

        protected Bundle zza(zzb com_google_android_gms_drive_Drive_zzb) {
            return com_google_android_gms_drive_Drive_zzb == null ? new Bundle() : com_google_android_gms_drive_Drive_zzb.zzpd();
        }
    }

    public static class zzb implements Optional {
        private final Bundle zzNW;

        private zzb() {
            this(new Bundle());
        }

        private zzb(Bundle bundle) {
            this.zzNW = bundle;
        }

        public Bundle zzpd() {
            return this.zzNW;
        }
    }

    static {
        zzNX = new ClientKey();
        SCOPE_FILE = new Scope(Scopes.DRIVE_FILE);
        SCOPE_APPFOLDER = new Scope(Scopes.DRIVE_APPFOLDER);
        zzacY = new Scope("https://www.googleapis.com/auth/drive");
        zzacZ = new Scope("https://www.googleapis.com/auth/drive.apps");
        API = new Api("Drive.API", new C07591(), zzNX, new Scope[0]);
        zzada = new Api("Drive.INTERNAL_API", new C07602(), zzNX, new Scope[0]);
        DriveApi = new zzq();
        zzadb = new zzv();
        zzadc = new zzaa();
        DrivePreferencesApi = new zzy();
    }

    private Drive() {
    }
}
