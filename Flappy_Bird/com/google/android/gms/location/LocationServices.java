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
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.internal.zzd;
import com.google.android.gms.location.internal.zzj;
import com.google.android.gms.location.internal.zzo;

public class LocationServices {
    public static final Api<NoOptions> API;
    public static final FusedLocationProviderApi FusedLocationApi;
    public static final GeofencingApi GeofencingApi;
    public static final SettingsApi SettingsApi;
    private static final ClientKey<zzj> zzNX;
    private static final com.google.android.gms.common.api.Api.zza<zzj, NoOptions> zzNY;

    /* renamed from: com.google.android.gms.location.LocationServices.1 */
    static class C05041 implements com.google.android.gms.common.api.Api.zza<zzj, NoOptions> {
        C05041() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzo(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzj zzo(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzj(context, looper, connectionCallbacks, onConnectionFailedListener, "locationServices", com_google_android_gms_common_internal_zze);
        }
    }

    public static abstract class zza<R extends Result> extends com.google.android.gms.common.api.zza.zza<R, zzj> {
        public zza(GoogleApiClient googleApiClient) {
            super(LocationServices.zzNX, googleApiClient);
        }
    }

    static {
        zzNX = new ClientKey();
        zzNY = new C05041();
        API = new Api("LocationServices.API", zzNY, zzNX, new Scope[0]);
        FusedLocationApi = new zzd();
        GeofencingApi = new com.google.android.gms.location.internal.zze();
        SettingsApi = new zzo();
    }

    private LocationServices() {
    }

    public static zzj zze(GoogleApiClient googleApiClient) {
        boolean z = true;
        zzu.zzb(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        zzj com_google_android_gms_location_internal_zzj = (zzj) googleApiClient.zza(zzNX);
        if (com_google_android_gms_location_internal_zzj == null) {
            z = false;
        }
        zzu.zza(z, (Object) "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
        return com_google_android_gms_location_internal_zzj;
    }
}
