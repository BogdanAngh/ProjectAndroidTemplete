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

public final class zzkl {
    public static final Api<NoOptions> API;
    public static final ClientKey<zzkp> zzNX;
    private static final zza<zzkp, NoOptions> zzNY;
    public static final zzkm zzabj;

    /* renamed from: com.google.android.gms.internal.zzkl.1 */
    static class C04871 implements zza<zzkp, NoOptions> {
        C04871() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzg(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzkp zzg(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzkp(context, looper, connectionCallbacks, onConnectionFailedListener);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C04871();
        API = new Api("Common.API", zzNY, zzNX, new Scope[0]);
        zzabj = new zzkn();
    }
}
