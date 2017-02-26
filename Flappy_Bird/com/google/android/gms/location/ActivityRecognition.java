package com.google.android.gms.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.location.internal.zzj;

public class ActivityRecognition {
    public static final Api<NoOptions> API;
    public static final ActivityRecognitionApi ActivityRecognitionApi;
    public static final String CLIENT_NAME = "activity_recognition";
    private static final ClientKey<zzj> zzNX;
    private static final com.google.android.gms.common.api.Api.zza<zzj, NoOptions> zzNY;

    /* renamed from: com.google.android.gms.location.ActivityRecognition.1 */
    static class C05031 implements com.google.android.gms.common.api.Api.zza<zzj, NoOptions> {
        C05031() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzo(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzj zzo(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzj(context, looper, connectionCallbacks, onConnectionFailedListener, ActivityRecognition.CLIENT_NAME);
        }
    }

    public static abstract class zza<R extends Result> extends com.google.android.gms.common.api.zza.zza<R, zzj> {
        public zza(GoogleApiClient googleApiClient) {
            super(ActivityRecognition.zzNX, googleApiClient);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C05031();
        API = new Api("ActivityRecognition.API", zzNY, zzNX, new Scope[0]);
        ActivityRecognitionApi = new com.google.android.gms.location.internal.zza();
    }

    private ActivityRecognition() {
    }
}
