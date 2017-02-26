package com.google.android.gms.nearby.messages.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.nearby.messages.zzc;
import com.google.android.gms.nearby.messages.zze;

public class zzg implements zzc {
    public static final ClientKey<zzf> zzNX;
    public static final zza<zzf, zze> zzNY;

    /* renamed from: com.google.android.gms.nearby.messages.internal.zzg.1 */
    static class C05111 implements zza<zzf, zze> {
        C05111() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public zzf zza(Context context, Looper looper, com.google.android.gms.common.internal.zze com_google_android_gms_common_internal_zze, zze com_google_android_gms_nearby_messages_zze, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzf(context, looper, connectionCallbacks, onConnectionFailedListener, com_google_android_gms_common_internal_zze, com_google_android_gms_nearby_messages_zze);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C05111();
    }
}
