package com.google.android.gms.cast;

import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.internal.zze;
import com.google.android.gms.cast.internal.zzm;
import com.google.android.gms.cast.internal.zzn;
import com.google.android.gms.cast.internal.zzo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.GamesStatusCodes;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONObject;

public class RemoteMediaPlayer implements MessageReceivedCallback {
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_CANCELED = 2101;
    public static final int STATUS_FAILED = 2100;
    public static final int STATUS_REPLACED = 2103;
    public static final int STATUS_SUCCEEDED = 0;
    public static final int STATUS_TIMED_OUT = 2102;
    private final zzm zzSt;
    private final zza zzSu;
    private OnPreloadStatusUpdatedListener zzSv;
    private OnQueueStatusUpdatedListener zzSw;
    private OnMetadataUpdatedListener zzSx;
    private OnStatusUpdatedListener zzSy;
    private final Object zzqt;

    public interface OnMetadataUpdatedListener {
        void onMetadataUpdated();
    }

    public interface OnPreloadStatusUpdatedListener {
        void onPreloadStatusUpdated();
    }

    public interface OnQueueStatusUpdatedListener {
        void onQueueStatusUpdated();
    }

    public interface OnStatusUpdatedListener {
        void onStatusUpdated();
    }

    public interface MediaChannelResult extends Result {
        JSONObject getCustomData();
    }

    private class zza implements zzn {
        private GoogleApiClient zzSW;
        private long zzSX;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        private final class zza implements ResultCallback<Status> {
            private final long zzSY;
            final /* synthetic */ zza zzSZ;

            zza(zza com_google_android_gms_cast_RemoteMediaPlayer_zza, long j) {
                this.zzSZ = com_google_android_gms_cast_RemoteMediaPlayer_zza;
                this.zzSY = j;
            }

            public /* synthetic */ void onResult(Result x0) {
                zzm((Status) x0);
            }

            public void zzm(Status status) {
                if (!status.isSuccess()) {
                    this.zzSZ.zzSz.zzSt.zzb(this.zzSY, status.getStatusCode());
                }
            }
        }

        public zza(RemoteMediaPlayer remoteMediaPlayer) {
            this.zzSz = remoteMediaPlayer;
            this.zzSX = 0;
        }

        public void zza(String str, String str2, long j, String str3) throws IOException {
            if (this.zzSW == null) {
                throw new IOException("No GoogleApiClient available");
            }
            Cast.CastApi.sendMessage(this.zzSW, str, str2).setResultCallback(new zza(this, j));
        }

        public void zzb(GoogleApiClient googleApiClient) {
            this.zzSW = googleApiClient;
        }

        public long zzlu() {
            long j = this.zzSX + 1;
            this.zzSX = j;
            return j;
        }
    }

    private static final class zzc implements MediaChannelResult {
        private final Status zzOt;
        private final JSONObject zzRJ;

        zzc(Status status, JSONObject jSONObject) {
            this.zzOt = status;
            this.zzRJ = jSONObject;
        }

        public JSONObject getCustomData() {
            return this.zzRJ;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.1 */
    class C08461 extends zzm {
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C08461(RemoteMediaPlayer remoteMediaPlayer, String str) {
            this.zzSz = remoteMediaPlayer;
            super(str);
        }

        protected void onMetadataUpdated() {
            this.zzSz.onMetadataUpdated();
        }

        protected void onPreloadStatusUpdated() {
            this.zzSz.onPreloadStatusUpdated();
        }

        protected void onQueueStatusUpdated() {
            this.zzSz.onQueueStatusUpdated();
        }

        protected void onStatusUpdated() {
            this.zzSz.onStatusUpdated();
        }
    }

    private static abstract class zzb extends com.google.android.gms.cast.internal.zzb<MediaChannelResult> {
        zzo zzTa;

        /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.zzb.1 */
        class C04081 implements zzo {
            final /* synthetic */ zzb zzTb;

            C04081(zzb com_google_android_gms_cast_RemoteMediaPlayer_zzb) {
                this.zzTb = com_google_android_gms_cast_RemoteMediaPlayer_zzb;
            }

            public void zza(long j, int i, Object obj) {
                this.zzTb.setResult(new zzc(new Status(i), obj instanceof JSONObject ? (JSONObject) obj : null));
            }

            public void zzy(long j) {
                this.zzTb.setResult(this.zzTb.zzn(new Status(RemoteMediaPlayer.STATUS_REPLACED)));
            }
        }

        /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.zzb.2 */
        class C06062 implements MediaChannelResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ zzb zzTb;

            C06062(zzb com_google_android_gms_cast_RemoteMediaPlayer_zzb, Status status) {
                this.zzTb = com_google_android_gms_cast_RemoteMediaPlayer_zzb;
                this.zzOl = status;
            }

            public JSONObject getCustomData() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }
        }

        zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
            this.zzTa = new C04081(this);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzn(x0);
        }

        public MediaChannelResult zzn(Status status) {
            return new C06062(this, status);
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.10 */
    class AnonymousClass10 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass10(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, (int) RemoteMediaPlayer.STATUS_SUCCEEDED, null, (int) RemoteMediaPlayer.RESUME_STATE_PLAY, null, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.11 */
    class AnonymousClass11 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ int zzSF;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass11(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, int i, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSF = i;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, (int) RemoteMediaPlayer.STATUS_SUCCEEDED, null, (int) RemoteMediaPlayer.STATUS_SUCCEEDED, Integer.valueOf(this.zzSF), this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.12 */
    class AnonymousClass12 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ MediaInfo zzSM;
        final /* synthetic */ boolean zzSN;
        final /* synthetic */ long zzSO;
        final /* synthetic */ long[] zzSP;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass12(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaInfo mediaInfo, boolean z, long j, long[] jArr, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSM = mediaInfo;
            this.zzSN = z;
            this.zzSO = j;
            this.zzSP = jArr;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSM, this.zzSN, this.zzSO, this.zzSP, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.13 */
    class AnonymousClass13 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ int zzSQ;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass13(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSQ = i;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                if (this.zzSz.zzaH(this.zzSQ) == -1) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_SUCCEEDED)));
                    return;
                }
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    zzm zzg = this.zzSz.zzSt;
                    zzo com_google_android_gms_cast_internal_zzo = this.zzTa;
                    int[] iArr = new int[RemoteMediaPlayer.RESUME_STATE_PLAY];
                    iArr[RemoteMediaPlayer.STATUS_SUCCEEDED] = this.zzSQ;
                    zzg.zza(com_google_android_gms_cast_internal_zzo, iArr, this.zzSG);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                } finally {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.14 */
    class AnonymousClass14 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ int zzSQ;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass14(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSQ = i;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                if (this.zzSz.zzaH(this.zzSQ) == -1) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_SUCCEEDED)));
                    return;
                }
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSQ, null, (int) RemoteMediaPlayer.STATUS_SUCCEEDED, null, this.zzSG);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                } finally {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.15 */
    class AnonymousClass15 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ int zzSQ;
        final /* synthetic */ int zzSR;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass15(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, int i2, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSQ = i;
            this.zzSR = i2;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            int i = RemoteMediaPlayer.STATUS_SUCCEEDED;
            synchronized (this.zzSz.zzqt) {
                int zza = this.zzSz.zzaH(this.zzSQ);
                if (zza == -1) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_SUCCEEDED)));
                } else if (this.zzSR < 0) {
                    Object[] objArr = new Object[RemoteMediaPlayer.RESUME_STATE_PLAY];
                    objArr[RemoteMediaPlayer.STATUS_SUCCEEDED] = Integer.valueOf(this.zzSR);
                    setResult(zzn(new Status(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE, String.format(Locale.ROOT, "Invalid request: Invalid newIndex %d.", objArr))));
                } else if (zza == this.zzSR) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_SUCCEEDED)));
                } else {
                    MediaQueueItem queueItem = this.zzSz.getMediaStatus().getQueueItem(this.zzSR > zza ? this.zzSR + RemoteMediaPlayer.RESUME_STATE_PLAY : this.zzSR);
                    if (queueItem != null) {
                        i = queueItem.getItemId();
                    }
                    this.zzSz.zzSu.zzb(this.zzSA);
                    try {
                        zzm zzg = this.zzSz.zzSt;
                        zzo com_google_android_gms_cast_internal_zzo = this.zzTa;
                        int[] iArr = new int[RemoteMediaPlayer.RESUME_STATE_PLAY];
                        iArr[RemoteMediaPlayer.STATUS_SUCCEEDED] = this.zzSQ;
                        zzg.zza(com_google_android_gms_cast_internal_zzo, iArr, i, this.zzSG);
                    } catch (IOException e) {
                        setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    } finally {
                        this.zzSz.zzSu.zzb(null);
                    }
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.16 */
    class AnonymousClass16 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass16(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.17 */
    class AnonymousClass17 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass17(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zzb(this.zzTa, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.18 */
    class AnonymousClass18 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass18(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zzc(this.zzTa, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.19 */
    class AnonymousClass19 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ long zzSS;
        final /* synthetic */ int zzST;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass19(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, long j, int i, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSS = j;
            this.zzST = i;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSS, this.zzST, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.20 */
    class AnonymousClass20 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ double zzSU;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass20(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, double d, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSU = d;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSU, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IllegalStateException e) {
                    try {
                        setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                        this.zzSz.zzSu.zzb(null);
                    } catch (Throwable th) {
                        this.zzSz.zzSu.zzb(null);
                    }
                } catch (IllegalArgumentException e2) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e3) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.21 */
    class AnonymousClass21 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ boolean zzSV;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass21(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, boolean z, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSV = z;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSV, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IllegalStateException e) {
                    try {
                        setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                        this.zzSz.zzSu.zzb(null);
                    } catch (Throwable th) {
                        this.zzSz.zzSu.zzb(null);
                    }
                } catch (IOException e2) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.22 */
    class AnonymousClass22 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        AnonymousClass22(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.2 */
    class C09132 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ long[] zzSB;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09132(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, long[] jArr) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSB = jArr;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSB);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.3 */
    class C09143 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ TextTrackStyle zzSC;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09143(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, TextTrackStyle textTrackStyle) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSC = textTrackStyle;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSC);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.4 */
    class C09154 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ MediaQueueItem[] zzSD;
        final /* synthetic */ int zzSE;
        final /* synthetic */ int zzSF;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09154(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem[] mediaQueueItemArr, int i, int i2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSD = mediaQueueItemArr;
            this.zzSE = i;
            this.zzSF = i2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSD, this.zzSE, this.zzSF, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.5 */
    class C09165 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ MediaQueueItem[] zzSH;
        final /* synthetic */ int zzSI;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09165(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSH = mediaQueueItemArr;
            this.zzSI = i;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSH, this.zzSI, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.6 */
    class C09176 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ MediaQueueItem[] zzSJ;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09176(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem[] mediaQueueItemArr, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSJ = mediaQueueItemArr;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, (int) RemoteMediaPlayer.STATUS_SUCCEEDED, this.zzSJ, (int) RemoteMediaPlayer.STATUS_SUCCEEDED, null, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.7 */
    class C09187 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ int[] zzSK;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09187(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, int[] iArr, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSK = iArr;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSK, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.8 */
    class C09198 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ int zzSI;
        final /* synthetic */ int[] zzSL;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09198(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, int[] iArr, int i, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSL = iArr;
            this.zzSI = i;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, this.zzSL, this.zzSI, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.9 */
    class C09209 extends zzb {
        final /* synthetic */ GoogleApiClient zzSA;
        final /* synthetic */ JSONObject zzSG;
        final /* synthetic */ RemoteMediaPlayer zzSz;

        C09209(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
            this.zzSz = remoteMediaPlayer;
            this.zzSA = googleApiClient2;
            this.zzSG = jSONObject;
            super(googleApiClient);
        }

        protected void zza(zze com_google_android_gms_cast_internal_zze) {
            synchronized (this.zzSz.zzqt) {
                this.zzSz.zzSu.zzb(this.zzSA);
                try {
                    this.zzSz.zzSt.zza(this.zzTa, (int) RemoteMediaPlayer.STATUS_SUCCEEDED, null, -1, null, this.zzSG);
                    this.zzSz.zzSu.zzb(null);
                } catch (IOException e) {
                    setResult(zzn(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.zzSz.zzSu.zzb(null);
                } catch (Throwable th) {
                    this.zzSz.zzSu.zzb(null);
                }
            }
        }
    }

    public RemoteMediaPlayer() {
        this.zzqt = new Object();
        this.zzSu = new zza(this);
        this.zzSt = new C08461(this, null);
        this.zzSt.zza(this.zzSu);
    }

    private void onMetadataUpdated() {
        if (this.zzSx != null) {
            this.zzSx.onMetadataUpdated();
        }
    }

    private void onPreloadStatusUpdated() {
        if (this.zzSv != null) {
            this.zzSv.onPreloadStatusUpdated();
        }
    }

    private void onQueueStatusUpdated() {
        if (this.zzSw != null) {
            this.zzSw.onQueueStatusUpdated();
        }
    }

    private void onStatusUpdated() {
        if (this.zzSy != null) {
            this.zzSy.onStatusUpdated();
        }
    }

    private int zzaH(int i) {
        MediaStatus mediaStatus = getMediaStatus();
        for (int i2 = STATUS_SUCCEEDED; i2 < mediaStatus.getQueueItemCount(); i2 += RESUME_STATE_PLAY) {
            if (mediaStatus.getQueueItem(i2).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    public long getApproximateStreamPosition() {
        long approximateStreamPosition;
        synchronized (this.zzqt) {
            approximateStreamPosition = this.zzSt.getApproximateStreamPosition();
        }
        return approximateStreamPosition;
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo;
        synchronized (this.zzqt) {
            mediaInfo = this.zzSt.getMediaInfo();
        }
        return mediaInfo;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatus;
        synchronized (this.zzqt) {
            mediaStatus = this.zzSt.getMediaStatus();
        }
        return mediaStatus;
    }

    public String getNamespace() {
        return this.zzSt.getNamespace();
    }

    public long getStreamDuration() {
        long streamDuration;
        synchronized (this.zzqt) {
            streamDuration = this.zzSt.getStreamDuration();
        }
        return streamDuration;
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo) {
        return load(apiClient, mediaInfo, true, 0, null, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay) {
        return load(apiClient, mediaInfo, autoplay, 0, null, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition) {
        return load(apiClient, mediaInfo, autoplay, playPosition, null, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition, JSONObject customData) {
        return load(apiClient, mediaInfo, autoplay, playPosition, null, customData);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition, long[] activeTrackIds, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass12(this, apiClient, apiClient, mediaInfo, autoplay, playPosition, activeTrackIds, customData));
    }

    public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
        this.zzSt.zzbB(message);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient apiClient) {
        return pause(apiClient, null);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass16(this, apiClient, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient apiClient) {
        return play(apiClient, null);
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass18(this, apiClient, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> queueAppendItem(GoogleApiClient apiClient, MediaQueueItem item, JSONObject customData) throws IllegalArgumentException {
        MediaQueueItem[] mediaQueueItemArr = new MediaQueueItem[RESUME_STATE_PLAY];
        mediaQueueItemArr[STATUS_SUCCEEDED] = item;
        return queueInsertItems(apiClient, mediaQueueItemArr, STATUS_SUCCEEDED, customData);
    }

    public PendingResult<MediaChannelResult> queueInsertItems(GoogleApiClient apiClient, MediaQueueItem[] itemsToInsert, int insertBeforeItemId, JSONObject customData) throws IllegalArgumentException {
        return apiClient.zzb(new C09165(this, apiClient, apiClient, itemsToInsert, insertBeforeItemId, customData));
    }

    public PendingResult<MediaChannelResult> queueJumpToItem(GoogleApiClient apiClient, int itemId, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass14(this, apiClient, itemId, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> queueLoad(GoogleApiClient apiClient, MediaQueueItem[] items, int startIndex, int repeatMode, JSONObject customData) throws IllegalArgumentException {
        return apiClient.zzb(new C09154(this, apiClient, apiClient, items, startIndex, repeatMode, customData));
    }

    public PendingResult<MediaChannelResult> queueMoveItemToNewIndex(GoogleApiClient apiClient, int itemId, int newIndex, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass15(this, apiClient, itemId, newIndex, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> queueNext(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass10(this, apiClient, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> queuePrev(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.zzb(new C09209(this, apiClient, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> queueRemoveItem(GoogleApiClient apiClient, int itemId, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass13(this, apiClient, itemId, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> queueRemoveItems(GoogleApiClient apiClient, int[] itemIdsToRemove, JSONObject customData) throws IllegalArgumentException {
        return apiClient.zzb(new C09187(this, apiClient, apiClient, itemIdsToRemove, customData));
    }

    public PendingResult<MediaChannelResult> queueReorderItems(GoogleApiClient apiClient, int[] itemIdsToReorder, int insertBeforeItemId, JSONObject customData) throws IllegalArgumentException {
        return apiClient.zzb(new C09198(this, apiClient, apiClient, itemIdsToReorder, insertBeforeItemId, customData));
    }

    public PendingResult<MediaChannelResult> queueSetRepeatMode(GoogleApiClient apiClient, int repeatMode, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass11(this, apiClient, apiClient, repeatMode, customData));
    }

    public PendingResult<MediaChannelResult> queueUpdateItems(GoogleApiClient apiClient, MediaQueueItem[] itemsToUpdate, JSONObject customData) {
        return apiClient.zzb(new C09176(this, apiClient, apiClient, itemsToUpdate, customData));
    }

    public PendingResult<MediaChannelResult> requestStatus(GoogleApiClient apiClient) {
        return apiClient.zzb(new AnonymousClass22(this, apiClient, apiClient));
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position) {
        return seek(apiClient, position, STATUS_SUCCEEDED, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position, int resumeState) {
        return seek(apiClient, position, resumeState, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position, int resumeState, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass19(this, apiClient, apiClient, position, resumeState, customData));
    }

    public PendingResult<MediaChannelResult> setActiveMediaTracks(GoogleApiClient apiClient, long[] trackIds) {
        if (trackIds != null) {
            return apiClient.zzb(new C09132(this, apiClient, apiClient, trackIds));
        }
        throw new IllegalArgumentException("trackIds cannot be null");
    }

    public void setOnMetadataUpdatedListener(OnMetadataUpdatedListener listener) {
        this.zzSx = listener;
    }

    public void setOnPreloadStatusUpdatedListener(OnPreloadStatusUpdatedListener listener) {
        this.zzSv = listener;
    }

    public void setOnQueueStatusUpdatedListener(OnQueueStatusUpdatedListener listener) {
        this.zzSw = listener;
    }

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener listener) {
        this.zzSy = listener;
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient apiClient, boolean muteState) {
        return setStreamMute(apiClient, muteState, null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient apiClient, boolean muteState, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass21(this, apiClient, apiClient, muteState, customData));
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient apiClient, double volume) throws IllegalArgumentException {
        return setStreamVolume(apiClient, volume, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient apiClient, double volume, JSONObject customData) throws IllegalArgumentException {
        if (!Double.isInfinite(volume) && !Double.isNaN(volume)) {
            return apiClient.zzb(new AnonymousClass20(this, apiClient, apiClient, volume, customData));
        }
        throw new IllegalArgumentException("Volume cannot be " + volume);
    }

    public PendingResult<MediaChannelResult> setTextTrackStyle(GoogleApiClient apiClient, TextTrackStyle trackStyle) {
        if (trackStyle != null) {
            return apiClient.zzb(new C09143(this, apiClient, apiClient, trackStyle));
        }
        throw new IllegalArgumentException("trackStyle cannot be null");
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient apiClient) {
        return stop(apiClient, null);
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.zzb(new AnonymousClass17(this, apiClient, apiClient, customData));
    }
}
