package com.google.android.gms.auth.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.internal.CredentialsClientImpl;
import com.google.android.gms.auth.api.credentials.internal.zzc;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.internal.zzje;
import com.google.android.gms.internal.zzjf;
import com.google.android.gms.internal.zzjg;
import com.google.android.gms.internal.zzjj;
import com.google.android.gms.internal.zzjm;
import com.google.android.gms.internal.zzjn;
import com.google.android.gms.internal.zzjp;
import com.google.android.gms.internal.zzjq;

public final class Auth {
    public static final ClientKey<CredentialsClientImpl> CLIENT_KEY_CREDENTIALS_API;
    public static final Api<NoOptions> CREDENTIALS_API;
    public static final CredentialsApi CredentialsApi;
    public static final ClientKey<zzjj> zzOE;
    public static final ClientKey<zzjg> zzOF;
    public static final ClientKey<zzjq> zzOG;
    private static final com.google.android.gms.common.api.Api.zza<zzjj, zza> zzOH;
    private static final com.google.android.gms.common.api.Api.zza<CredentialsClientImpl, NoOptions> zzOI;
    private static final com.google.android.gms.common.api.Api.zza<zzjg, NoOptions> zzOJ;
    private static final com.google.android.gms.common.api.Api.zza<zzjq, NoOptions> zzOK;
    public static final Api<zza> zzOL;
    public static final Api<NoOptions> zzOM;
    public static final Api<NoOptions> zzON;
    public static final com.google.android.gms.auth.api.proxy.zza zzOO;
    public static final zzje zzOP;
    public static final zzjn zzOQ;

    /* renamed from: com.google.android.gms.auth.api.Auth.1 */
    static class C03971 implements com.google.android.gms.common.api.Api.zza<zzjj, zza> {
        C03971() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public zzjj zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, zza com_google_android_gms_auth_api_Auth_zza, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzjj(context, looper, com_google_android_gms_common_internal_zze, com_google_android_gms_auth_api_Auth_zza, connectionCallbacks, onConnectionFailedListener);
        }
    }

    /* renamed from: com.google.android.gms.auth.api.Auth.2 */
    static class C03982 implements com.google.android.gms.common.api.Api.zza<CredentialsClientImpl, NoOptions> {
        C03982() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzd(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public CredentialsClientImpl zzd(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new CredentialsClientImpl(context, looper, connectionCallbacks, onConnectionFailedListener);
        }
    }

    /* renamed from: com.google.android.gms.auth.api.Auth.3 */
    static class C03993 implements com.google.android.gms.common.api.Api.zza<zzjg, NoOptions> {
        C03993() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zze(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzjg zze(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzjg(context, looper, com_google_android_gms_common_internal_zze, connectionCallbacks, onConnectionFailedListener);
        }
    }

    /* renamed from: com.google.android.gms.auth.api.Auth.4 */
    static class C04004 implements com.google.android.gms.common.api.Api.zza<zzjq, NoOptions> {
        C04004() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public /* synthetic */ Client zza(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzf(context, looper, com_google_android_gms_common_internal_zze, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzjq zzf(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzjq(context, looper, connectionCallbacks, onConnectionFailedListener);
        }
    }

    public static final class zza implements Optional {
        private final Bundle zzOR;

        public Bundle zzkY() {
            return new Bundle(this.zzOR);
        }
    }

    static {
        zzOE = new ClientKey();
        CLIENT_KEY_CREDENTIALS_API = new ClientKey();
        zzOF = new ClientKey();
        zzOG = new ClientKey();
        zzOH = new C03971();
        zzOI = new C03982();
        zzOJ = new C03993();
        zzOK = new C04004();
        zzOL = new Api("Auth.PROXY_API", zzOH, zzOE, new Scope[0]);
        CREDENTIALS_API = new Api("Auth.CREDENTIALS_API", zzOI, CLIENT_KEY_CREDENTIALS_API, new Scope[0]);
        zzOM = new Api("Auth.SIGN_IN_API", zzOK, zzOG, new Scope[0]);
        zzON = new Api("Auth.ACCOUNT_STATUS_API", zzOJ, zzOF, new Scope[0]);
        zzOO = new zzjm();
        CredentialsApi = new zzc();
        zzOP = new zzjf();
        zzOQ = new zzjp();
    }

    private Auth() {
    }
}
