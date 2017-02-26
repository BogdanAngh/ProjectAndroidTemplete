package com.google.android.gms.appdatasearch;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.internal.zzit;
import com.google.android.gms.internal.zziv;

public final class zza {
    public static final ClientKey<zzit> zzMO;
    private static final com.google.android.gms.common.api.Api.zza<zzit, NoOptions> zzMP;
    public static final Api<NoOptions> zzMQ;
    public static final zzk zzMR;

    /* renamed from: com.google.android.gms.appdatasearch.zza.1 */
    static class C03941 implements com.google.android.gms.common.api.Api.zza<zzit, NoOptions> {
        C03941() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public zzit zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzit(context, looper, connectionCallbacks, onConnectionFailedListener);
        }
    }

    static {
        zzMO = new ClientKey();
        zzMP = new C03941();
        zzMQ = new Api("AppDataSearch.LIGHTWEIGHT_API", zzMP, zzMO, new Scope[0]);
        zzMR = new zziv();
    }
}
