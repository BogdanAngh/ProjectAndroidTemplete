package com.google.android.gms.appinvite;

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
import com.google.android.gms.internal.zziw;
import com.google.android.gms.internal.zzix;

public final class AppInvite {
    public static final Api<NoOptions> API;
    public static final AppInviteApi AppInviteApi;
    public static final ClientKey<zzix> zzNX;
    private static final zza<zzix, NoOptions> zzNY;

    /* renamed from: com.google.android.gms.appinvite.AppInvite.1 */
    static class C03951 implements zza<zzix, NoOptions> {
        C03951() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzb(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzix zzb(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzix(context, looper, connectionCallbacks, onConnectionFailedListener, com_google_android_gms_common_internal_zze);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C03951();
        API = new Api("AppInvite.API", zzNY, zzNX, new Scope[0]);
        AppInviteApi = new zziw();
    }

    private AppInvite() {
    }
}
