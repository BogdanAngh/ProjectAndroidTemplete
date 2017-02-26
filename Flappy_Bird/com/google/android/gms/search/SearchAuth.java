package com.google.android.gms.search;

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
import com.google.android.gms.internal.zzpo;
import com.google.android.gms.internal.zzpp;

public class SearchAuth {
    public static final Api<NoOptions> API;
    public static final SearchAuthApi SearchAuthApi;
    public static final ClientKey<zzpo> zzNX;
    private static final zza<zzpo, NoOptions> zzaJH;

    public static class StatusCodes {
        public static final int AUTH_DISABLED = 10000;
        public static final int AUTH_THROTTLED = 10001;
        public static final int DEVELOPER_ERROR = 10;
        public static final int INTERNAL_ERROR = 8;
        public static final int SUCCESS = 0;
    }

    /* renamed from: com.google.android.gms.search.SearchAuth.1 */
    static class C05161 implements zza<zzpo, NoOptions> {
        C05161() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzu(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzpo zzu(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzpo(context, connectionCallbacks, onConnectionFailedListener, com_google_android_gms_common_internal_zze);
        }
    }

    static {
        zzaJH = new C05161();
        zzNX = new ClientKey();
        API = new Api("SearchAuth.API", zzaJH, zzNX, new Scope[0]);
        SearchAuthApi = new zzpp();
    }

    private SearchAuth() {
    }
}
