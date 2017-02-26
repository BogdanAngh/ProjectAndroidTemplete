package com.google.android.gms.wearable;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.wearable.internal.zzav;
import com.google.android.gms.wearable.internal.zzax;
import com.google.android.gms.wearable.internal.zzbi;
import com.google.android.gms.wearable.internal.zzbk;
import com.google.android.gms.wearable.internal.zzbm;
import com.google.android.gms.wearable.internal.zzd;
import com.google.android.gms.wearable.internal.zzg;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzt;
import com.google.android.gms.wearable.internal.zzu;

public class Wearable {
    public static final Api<WearableOptions> API;
    public static final CapabilityApi CapabilityApi;
    public static final ChannelApi ChannelApi;
    public static final DataApi DataApi;
    public static final MessageApi MessageApi;
    public static final NodeApi NodeApi;
    public static final ClientKey<zzbk> zzNX;
    private static final zza<zzbk, WearableOptions> zzNY;
    public static final zza zzaSZ;
    public static final zzd zzaTa;
    public static final zzg zzaTb;
    public static final zzi zzaTc;

    /* renamed from: com.google.android.gms.wearable.Wearable.1 */
    static class C05361 implements zza<zzbk, WearableOptions> {
        C05361() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public zzbk zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, WearableOptions wearableOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            if (wearableOptions == null) {
                WearableOptions wearableOptions2 = new WearableOptions(null);
            }
            return new zzbk(context, looper, connectionCallbacks, onConnectionFailedListener, com_google_android_gms_common_internal_zze);
        }
    }

    public static final class WearableOptions implements Optional {

        public static class Builder {
            public WearableOptions build() {
                return new WearableOptions();
            }
        }

        private WearableOptions(Builder builder) {
        }
    }

    static {
        DataApi = new zzu();
        CapabilityApi = new zzg();
        MessageApi = new zzav();
        NodeApi = new zzax();
        ChannelApi = new zzi();
        zzaSZ = new zzd();
        zzaTa = new zzt();
        zzaTb = new zzbi();
        zzaTc = new zzbm();
        zzNX = new ClientKey();
        zzNY = new C05361();
        API = new Api("Wearable.API", zzNY, zzNX, new Scope[0]);
    }

    private Wearable() {
    }
}
