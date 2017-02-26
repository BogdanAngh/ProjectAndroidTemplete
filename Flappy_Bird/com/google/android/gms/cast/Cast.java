package com.google.android.gms.cast;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.LaunchOptions.Builder;
import com.google.android.gms.cast.internal.zze;
import com.google.android.gms.cast.internal.zzh;
import com.google.android.gms.cast.internal.zzk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.games.GamesStatusCodes;
import java.io.IOException;

public final class Cast {
    public static final int ACTIVE_INPUT_STATE_NO = 0;
    public static final int ACTIVE_INPUT_STATE_UNKNOWN = -1;
    public static final int ACTIVE_INPUT_STATE_YES = 1;
    public static final Api<CastOptions> API;
    public static final CastApi CastApi;
    public static final String EXTRA_APP_NO_LONGER_RUNNING = "com.google.android.gms.cast.EXTRA_APP_NO_LONGER_RUNNING";
    public static final int MAX_MESSAGE_LENGTH = 65536;
    public static final int MAX_NAMESPACE_LENGTH = 128;
    public static final int STANDBY_STATE_NO = 0;
    public static final int STANDBY_STATE_UNKNOWN = -1;
    public static final int STANDBY_STATE_YES = 1;
    private static final com.google.android.gms.common.api.Api.zza<zze, CastOptions> zzNY;

    public interface CastApi {

        public static final class zza implements CastApi {

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.1 */
            class C09041 extends zzh {
                final /* synthetic */ String zzQA;
                final /* synthetic */ zza zzQB;
                final /* synthetic */ String zzQz;

                C09041(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str, String str2) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    this.zzQz = str;
                    this.zzQA = str2;
                    super(googleApiClient);
                }

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                protected void zza(com.google.android.gms.cast.internal.zze r3) throws android.os.RemoteException {
                    /*
                    r2 = this;
                    r0 = r2.zzQz;	 Catch:{ IllegalArgumentException -> 0x000f, IllegalStateException -> 0x0008 }
                    r1 = r2.zzQA;	 Catch:{ IllegalArgumentException -> 0x000f, IllegalStateException -> 0x0008 }
                    r3.zza(r0, r1, r2);	 Catch:{ IllegalArgumentException -> 0x000f, IllegalStateException -> 0x0008 }
                L_0x0007:
                    return;
                L_0x0008:
                    r0 = move-exception;
                L_0x0009:
                    r0 = 2001; // 0x7d1 float:2.804E-42 double:9.886E-321;
                    r2.zzaL(r0);
                    goto L_0x0007;
                L_0x000f:
                    r0 = move-exception;
                    goto L_0x0009;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.Cast.CastApi.zza.1.zza(com.google.android.gms.cast.internal.zze):void");
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.2 */
            class C09052 extends zza {
                final /* synthetic */ zza zzQB;
                final /* synthetic */ String zzQC;

                C09052(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    this.zzQC = str;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    try {
                        com_google_android_gms_cast_internal_zze.zza(this.zzQC, false, (zzb) this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.3 */
            class C09063 extends zza {
                final /* synthetic */ zza zzQB;
                final /* synthetic */ String zzQC;
                final /* synthetic */ LaunchOptions zzQD;

                C09063(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    this.zzQC = str;
                    this.zzQD = launchOptions;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    try {
                        com_google_android_gms_cast_internal_zze.zza(this.zzQC, this.zzQD, (zzb) this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.4 */
            class C09074 extends zza {
                final /* synthetic */ String val$sessionId;
                final /* synthetic */ zza zzQB;
                final /* synthetic */ String zzQC;

                C09074(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str, String str2) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    this.zzQC = str;
                    this.val$sessionId = str2;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    try {
                        com_google_android_gms_cast_internal_zze.zzb(this.zzQC, this.val$sessionId, this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.5 */
            class C09085 extends zza {
                final /* synthetic */ zza zzQB;
                final /* synthetic */ String zzQC;

                C09085(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    this.zzQC = str;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    try {
                        com_google_android_gms_cast_internal_zze.zzb(this.zzQC, null, this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.6 */
            class C09096 extends zza {
                final /* synthetic */ zza zzQB;

                C09096(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    try {
                        com_google_android_gms_cast_internal_zze.zzb(null, null, this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.7 */
            class C09107 extends zzh {
                final /* synthetic */ zza zzQB;

                C09107(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    try {
                        com_google_android_gms_cast_internal_zze.zzd((zzb) this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.8 */
            class C09118 extends zzh {
                final /* synthetic */ zza zzQB;

                C09118(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    try {
                        com_google_android_gms_cast_internal_zze.zza("", (zzb) this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.zza.9 */
            class C09129 extends zzh {
                final /* synthetic */ String val$sessionId;
                final /* synthetic */ zza zzQB;

                C09129(zza com_google_android_gms_cast_Cast_CastApi_zza, GoogleApiClient googleApiClient, String str) {
                    this.zzQB = com_google_android_gms_cast_Cast_CastApi_zza;
                    this.val$sessionId = str;
                    super(googleApiClient);
                }

                protected void zza(zze com_google_android_gms_cast_internal_zze) throws RemoteException {
                    if (TextUtils.isEmpty(this.val$sessionId)) {
                        zzd(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE, "IllegalArgument: sessionId cannot be null or empty");
                        return;
                    }
                    try {
                        com_google_android_gms_cast_internal_zze.zza(this.val$sessionId, (zzb) this);
                    } catch (IllegalStateException e) {
                        zzaL(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            public int getActiveInputState(GoogleApiClient client) throws IllegalStateException {
                return ((zze) client.zza(zzk.zzNX)).zzlP();
            }

            public ApplicationMetadata getApplicationMetadata(GoogleApiClient client) throws IllegalStateException {
                return ((zze) client.zza(zzk.zzNX)).getApplicationMetadata();
            }

            public String getApplicationStatus(GoogleApiClient client) throws IllegalStateException {
                return ((zze) client.zza(zzk.zzNX)).getApplicationStatus();
            }

            public int getStandbyState(GoogleApiClient client) throws IllegalStateException {
                return ((zze) client.zza(zzk.zzNX)).zzlQ();
            }

            public double getVolume(GoogleApiClient client) throws IllegalStateException {
                return ((zze) client.zza(zzk.zzNX)).zzlO();
            }

            public boolean isMute(GoogleApiClient client) throws IllegalStateException {
                return ((zze) client.zza(zzk.zzNX)).isMute();
            }

            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client) {
                return client.zzb(new C09096(this, client));
            }

            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client, String applicationId) {
                return client.zzb(new C09085(this, client, applicationId));
            }

            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client, String applicationId, String sessionId) {
                return client.zzb(new C09074(this, client, applicationId, sessionId));
            }

            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient client, String applicationId) {
                return client.zzb(new C09052(this, client, applicationId));
            }

            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient client, String applicationId, LaunchOptions options) {
                return client.zzb(new C09063(this, client, applicationId, options));
            }

            @Deprecated
            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient client, String applicationId, boolean relaunchIfRunning) {
                return launchApplication(client, applicationId, new Builder().setRelaunchIfRunning(relaunchIfRunning).build());
            }

            public PendingResult<Status> leaveApplication(GoogleApiClient client) {
                return client.zzb(new C09107(this, client));
            }

            public void removeMessageReceivedCallbacks(GoogleApiClient client, String namespace) throws IOException, IllegalArgumentException {
                try {
                    ((zze) client.zza(zzk.zzNX)).zzbC(namespace);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public void requestStatus(GoogleApiClient client) throws IOException, IllegalStateException {
                try {
                    ((zze) client.zza(zzk.zzNX)).zzlN();
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public PendingResult<Status> sendMessage(GoogleApiClient client, String namespace, String message) {
                return client.zzb(new C09041(this, client, namespace, message));
            }

            public void setMessageReceivedCallbacks(GoogleApiClient client, String namespace, MessageReceivedCallback callbacks) throws IOException, IllegalStateException {
                try {
                    ((zze) client.zza(zzk.zzNX)).zza(namespace, callbacks);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public void setMute(GoogleApiClient client, boolean mute) throws IOException, IllegalStateException {
                try {
                    ((zze) client.zza(zzk.zzNX)).zzR(mute);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public void setVolume(GoogleApiClient client, double volume) throws IOException, IllegalArgumentException, IllegalStateException {
                try {
                    ((zze) client.zza(zzk.zzNX)).zzd(volume);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public PendingResult<Status> stopApplication(GoogleApiClient client) {
                return client.zzb(new C09118(this, client));
            }

            public PendingResult<Status> stopApplication(GoogleApiClient client, String sessionId) {
                return client.zzb(new C09129(this, client, sessionId));
            }
        }

        int getActiveInputState(GoogleApiClient googleApiClient) throws IllegalStateException;

        ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException;

        String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException;

        int getStandbyState(GoogleApiClient googleApiClient) throws IllegalStateException;

        double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException;

        boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException;

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, LaunchOptions launchOptions);

        @Deprecated
        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z);

        PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient);

        void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException;

        void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException;

        PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2);

        void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException;

        void setMute(GoogleApiClient googleApiClient, boolean z) throws IOException, IllegalStateException;

        void setVolume(GoogleApiClient googleApiClient, double d) throws IOException, IllegalArgumentException, IllegalStateException;

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient);

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str);
    }

    public static class Listener {
        public void onActiveInputStateChanged(int activeInputState) {
        }

        public void onApplicationDisconnected(int statusCode) {
        }

        public void onApplicationMetadataChanged(ApplicationMetadata applicationMetadata) {
        }

        public void onApplicationStatusChanged() {
        }

        public void onStandbyStateChanged(int standbyState) {
        }

        public void onVolumeChanged() {
        }
    }

    public interface MessageReceivedCallback {
        void onMessageReceived(CastDevice castDevice, String str, String str2);
    }

    /* renamed from: com.google.android.gms.cast.Cast.1 */
    static class C04011 implements com.google.android.gms.common.api.Api.zza<zze, CastOptions> {
        C04011() {
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public zze zza(Context context, Looper looper, com.google.android.gms.common.internal.zze com_google_android_gms_common_internal_zze, CastOptions castOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            zzu.zzb((Object) castOptions, (Object) "Setting the API options is required.");
            return new zze(context, looper, castOptions.zzQE, (long) castOptions.zzQG, castOptions.zzQF, connectionCallbacks, onConnectionFailedListener);
        }
    }

    public interface ApplicationConnectionResult extends Result {
        ApplicationMetadata getApplicationMetadata();

        String getApplicationStatus();

        String getSessionId();

        boolean getWasLaunched();
    }

    public static final class CastOptions implements HasOptions {
        final CastDevice zzQE;
        final Listener zzQF;
        private final int zzQG;

        public static final class Builder {
            CastDevice zzQH;
            Listener zzQI;
            private int zzQJ;

            public Builder(CastDevice castDevice, Listener castListener) {
                zzu.zzb((Object) castDevice, (Object) "CastDevice parameter cannot be null");
                zzu.zzb((Object) castListener, (Object) "CastListener parameter cannot be null");
                this.zzQH = castDevice;
                this.zzQI = castListener;
                this.zzQJ = Cast.STANDBY_STATE_NO;
            }

            public CastOptions build() {
                return new CastOptions();
            }

            public Builder setVerboseLoggingEnabled(boolean enabled) {
                if (enabled) {
                    this.zzQJ |= Cast.STANDBY_STATE_YES;
                } else {
                    this.zzQJ &= -2;
                }
                return this;
            }
        }

        private CastOptions(Builder builder) {
            this.zzQE = builder.zzQH;
            this.zzQF = builder.zzQI;
            this.zzQG = builder.zzQJ;
        }

        @Deprecated
        public static Builder builder(CastDevice castDevice, Listener castListener) {
            return new Builder(castDevice, castListener);
        }
    }

    private static abstract class zza extends com.google.android.gms.cast.internal.zzb<ApplicationConnectionResult> {

        /* renamed from: com.google.android.gms.cast.Cast.zza.1 */
        class C06051 implements ApplicationConnectionResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ zza zzQK;

            C06051(zza com_google_android_gms_cast_Cast_zza, Status status) {
                this.zzQK = com_google_android_gms_cast_Cast_zza;
                this.zzOl = status;
            }

            public ApplicationMetadata getApplicationMetadata() {
                return null;
            }

            public String getApplicationStatus() {
                return null;
            }

            public String getSessionId() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public boolean getWasLaunched() {
                return false;
            }
        }

        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzl(x0);
        }

        public ApplicationConnectionResult zzl(Status status) {
            return new C06051(this, status);
        }
    }

    static {
        zzNY = new C04011();
        API = new Api("Cast.API", zzNY, zzk.zzNX, new Scope[STANDBY_STATE_NO]);
        CastApi = new zza();
    }

    private Cast() {
    }
}
