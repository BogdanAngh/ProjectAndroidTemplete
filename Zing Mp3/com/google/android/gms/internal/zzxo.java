package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.signin.internal.zzg;

public final class zzxo {
    public static final Api<zzxq> API;
    public static final Api<zza> Jb;
    public static final zzf<zzg> aDi;
    static final com.google.android.gms.common.api.Api.zza<zzg, zza> aDj;
    public static final zzf<zzg> hg;
    public static final com.google.android.gms.common.api.Api.zza<zzg, zzxq> hh;
    public static final Scope jn;
    public static final Scope jo;

    /* renamed from: com.google.android.gms.internal.zzxo.1 */
    class C15171 extends com.google.android.gms.common.api.Api.zza<zzg, zzxq> {
        C15171() {
        }

        public zzg zza(Context context, Looper looper, com.google.android.gms.common.internal.zzf com_google_android_gms_common_internal_zzf, zzxq com_google_android_gms_internal_zzxq, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzg(context, looper, true, com_google_android_gms_common_internal_zzf, com_google_android_gms_internal_zzxq == null ? zzxq.aDl : com_google_android_gms_internal_zzxq, connectionCallbacks, onConnectionFailedListener);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzxo.2 */
    class C15182 extends com.google.android.gms.common.api.Api.zza<zzg, zza> {
        C15182() {
        }

        public zzg zza(Context context, Looper looper, com.google.android.gms.common.internal.zzf com_google_android_gms_common_internal_zzf, zza com_google_android_gms_internal_zzxo_zza, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzg(context, looper, false, com_google_android_gms_common_internal_zzf, com_google_android_gms_internal_zzxo_zza.zzcdb(), connectionCallbacks, onConnectionFailedListener);
        }
    }

    public static class zza implements HasOptions {
        private final Bundle aDk;

        public Bundle zzcdb() {
            return this.aDk;
        }
    }

    static {
        hg = new zzf();
        aDi = new zzf();
        hh = new C15171();
        aDj = new C15182();
        jn = new Scope(Scopes.PROFILE);
        jo = new Scope(Scopes.EMAIL);
        API = new Api("SignIn.API", hh, hg);
        Jb = new Api("SignIn.INTERNAL_API", aDj, aDi);
    }
}
