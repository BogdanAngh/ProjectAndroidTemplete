package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.Moments.LoadMomentsResult;
import com.google.android.gms.plus.internal.zze;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;

public final class zzpd implements Moments {

    private static abstract class zza extends com.google.android.gms.plus.Plus.zza<LoadMomentsResult> {

        /* renamed from: com.google.android.gms.internal.zzpd.zza.1 */
        class C08161 implements LoadMomentsResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ zza zzaHJ;

            C08161(zza com_google_android_gms_internal_zzpd_zza, Status status) {
                this.zzaHJ = com_google_android_gms_internal_zzpd_zza;
                this.zzOl = status;
            }

            public MomentBuffer getMomentBuffer() {
                return null;
            }

            public String getNextPageToken() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public String getUpdated() {
                return null;
            }

            public void release() {
            }
        }

        private zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaP(x0);
        }

        public LoadMomentsResult zzaP(Status status) {
            return new C08161(this, status);
        }
    }

    private static abstract class zzb extends com.google.android.gms.plus.Plus.zza<Status> {
        private zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    private static abstract class zzc extends com.google.android.gms.plus.Plus.zza<Status> {
        private zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpd.1 */
    class C10441 extends zza {
        final /* synthetic */ zzpd zzaHC;

        C10441(zzpd com_google_android_gms_internal_zzpd, GoogleApiClient googleApiClient) {
            this.zzaHC = com_google_android_gms_internal_zzpd;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            com_google_android_gms_plus_internal_zze.zzi(this);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpd.2 */
    class C10452 extends zza {
        final /* synthetic */ zzpd zzaHC;
        final /* synthetic */ String zzaHD;
        final /* synthetic */ Uri zzaHE;
        final /* synthetic */ String zzaHF;
        final /* synthetic */ String zzaHG;
        final /* synthetic */ int zzard;

        C10452(zzpd com_google_android_gms_internal_zzpd, GoogleApiClient googleApiClient, int i, String str, Uri uri, String str2, String str3) {
            this.zzaHC = com_google_android_gms_internal_zzpd;
            this.zzard = i;
            this.zzaHD = str;
            this.zzaHE = uri;
            this.zzaHF = str2;
            this.zzaHG = str3;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            com_google_android_gms_plus_internal_zze.zza(this, this.zzard, this.zzaHD, this.zzaHE, this.zzaHF, this.zzaHG);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpd.3 */
    class C10463 extends zzc {
        final /* synthetic */ zzpd zzaHC;
        final /* synthetic */ Moment zzaHH;

        C10463(zzpd com_google_android_gms_internal_zzpd, GoogleApiClient googleApiClient, Moment moment) {
            this.zzaHC = com_google_android_gms_internal_zzpd;
            this.zzaHH = moment;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            com_google_android_gms_plus_internal_zze.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaHH);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpd.4 */
    class C10474 extends zzb {
        final /* synthetic */ zzpd zzaHC;
        final /* synthetic */ String zzaHI;

        C10474(zzpd com_google_android_gms_internal_zzpd, GoogleApiClient googleApiClient, String str) {
            this.zzaHC = com_google_android_gms_internal_zzpd;
            this.zzaHI = str;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            com_google_android_gms_plus_internal_zze.zzdX(this.zzaHI);
            setResult(Status.zzXP);
        }
    }

    public PendingResult<LoadMomentsResult> load(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new C10441(this, googleApiClient));
    }

    public PendingResult<LoadMomentsResult> load(GoogleApiClient googleApiClient, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
        return googleApiClient.zza(new C10452(this, googleApiClient, maxResults, pageToken, targetUrl, type, userId));
    }

    public PendingResult<Status> remove(GoogleApiClient googleApiClient, String momentId) {
        return googleApiClient.zzb(new C10474(this, googleApiClient, momentId));
    }

    public PendingResult<Status> write(GoogleApiClient googleApiClient, Moment moment) {
        return googleApiClient.zzb(new C10463(this, googleApiClient, moment));
    }
}
