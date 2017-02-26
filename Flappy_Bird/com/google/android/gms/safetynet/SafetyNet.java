package com.google.android.gms.safetynet;

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
import com.google.android.gms.internal.zzpj;
import com.google.android.gms.internal.zzpk;
import com.google.android.gms.internal.zzpl;

public final class SafetyNet {
    public static final Api<NoOptions> API;
    public static final SafetyNetApi SafetyNetApi;
    public static final ClientKey<zzpk> zzNX;
    public static final zza<zzpk, NoOptions> zzNY;
    public static final zzb zzaJy;

    /* renamed from: com.google.android.gms.safetynet.SafetyNet.1 */
    static class C05151 implements zza<zzpk, NoOptions> {
        C05151() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzt(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzpk zzt(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzpk(context, looper, connectionCallbacks, onConnectionFailedListener);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C05151();
        API = new Api("SafetyNet.API", zzNY, zzNX, new Scope[0]);
        SafetyNetApi = new zzpj();
        zzaJy = new zzpl();
    }

    private SafetyNet() {
    }
}
