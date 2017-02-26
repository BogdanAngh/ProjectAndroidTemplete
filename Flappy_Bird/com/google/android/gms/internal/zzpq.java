package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.signin.internal.zzg;
import com.google.android.gms.signin.internal.zzh;
import java.util.concurrent.Executors;

public final class zzpq {
    public static final Api<zzpt> API;
    public static final ClientKey<zzh> zzNX;
    public static final zza<zzh, zzpt> zzNY;
    static final zza<zzh, NoOptions> zzaJO;
    public static final zzpr zzaJP;
    public static final Api<NoOptions> zzada;
    public static final ClientKey<zzh> zzajz;

    /* renamed from: com.google.android.gms.internal.zzpq.1 */
    static class C04981 implements zza<zzh, zzpt> {
        C04981() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public zzh zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, zzpt com_google_android_gms_internal_zzpt, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzh(context, looper, true, com_google_android_gms_common_internal_zze, com_google_android_gms_internal_zzpt == null ? zzpt.zzaJQ : com_google_android_gms_internal_zzpt, connectionCallbacks, onConnectionFailedListener, Executors.newSingleThreadExecutor());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpq.2 */
    static class C04992 implements zza<zzh, NoOptions> {
        C04992() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzv(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzh zzv(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzh(context, looper, false, com_google_android_gms_common_internal_zze, zzpt.zzaJQ, connectionCallbacks, onConnectionFailedListener, Executors.newSingleThreadExecutor());
        }
    }

    static {
        zzNX = new ClientKey();
        zzajz = new ClientKey();
        zzNY = new C04981();
        zzaJO = new C04992();
        API = new Api("SignIn.API", zzNY, zzNX, new Scope[0]);
        zzada = new Api("SignIn.INTERNAL_API", zzaJO, zzajz, new Scope[0]);
        zzaJP = new zzg();
    }
}
