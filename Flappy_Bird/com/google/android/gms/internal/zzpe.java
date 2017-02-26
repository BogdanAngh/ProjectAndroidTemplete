package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.zze;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

public final class zzpe implements People {

    private static abstract class zza extends com.google.android.gms.plus.Plus.zza<LoadPeopleResult> {

        /* renamed from: com.google.android.gms.internal.zzpe.zza.1 */
        class C08171 implements LoadPeopleResult {
            final /* synthetic */ Status zzOl;
            final /* synthetic */ zza zzaHO;

            C08171(zza com_google_android_gms_internal_zzpe_zza, Status status) {
                this.zzaHO = com_google_android_gms_internal_zzpe_zza;
                this.zzOl = status;
            }

            public String getNextPageToken() {
                return null;
            }

            public PersonBuffer getPersonBuffer() {
                return null;
            }

            public Status getStatus() {
                return this.zzOl;
            }

            public void release() {
            }
        }

        private zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaQ(x0);
        }

        public LoadPeopleResult zzaQ(Status status) {
            return new C08171(this, status);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpe.1 */
    class C10481 extends zza {
        final /* synthetic */ String zzaHD;
        final /* synthetic */ int zzaHK;
        final /* synthetic */ zzpe zzaHL;

        C10481(zzpe com_google_android_gms_internal_zzpe, GoogleApiClient googleApiClient, int i, String str) {
            this.zzaHL = com_google_android_gms_internal_zzpe;
            this.zzaHK = i;
            this.zzaHD = str;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            setCancelToken(com_google_android_gms_plus_internal_zze.zza(this, this.zzaHK, this.zzaHD));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpe.2 */
    class C10492 extends zza {
        final /* synthetic */ String zzaHD;
        final /* synthetic */ zzpe zzaHL;

        C10492(zzpe com_google_android_gms_internal_zzpe, GoogleApiClient googleApiClient, String str) {
            this.zzaHL = com_google_android_gms_internal_zzpe;
            this.zzaHD = str;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            setCancelToken(com_google_android_gms_plus_internal_zze.zzq(this, this.zzaHD));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpe.3 */
    class C10503 extends zza {
        final /* synthetic */ zzpe zzaHL;

        C10503(zzpe com_google_android_gms_internal_zzpe, GoogleApiClient googleApiClient) {
            this.zzaHL = com_google_android_gms_internal_zzpe;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            com_google_android_gms_plus_internal_zze.zzj(this);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpe.4 */
    class C10514 extends zza {
        final /* synthetic */ zzpe zzaHL;
        final /* synthetic */ Collection zzaHM;

        C10514(zzpe com_google_android_gms_internal_zzpe, GoogleApiClient googleApiClient, Collection collection) {
            this.zzaHL = com_google_android_gms_internal_zzpe;
            this.zzaHM = collection;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            com_google_android_gms_plus_internal_zze.zza((zzb) this, this.zzaHM);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpe.5 */
    class C10525 extends zza {
        final /* synthetic */ zzpe zzaHL;
        final /* synthetic */ String[] zzaHN;

        C10525(zzpe com_google_android_gms_internal_zzpe, GoogleApiClient googleApiClient, String[] strArr) {
            this.zzaHL = com_google_android_gms_internal_zzpe;
            this.zzaHN = strArr;
            super(null);
        }

        protected void zza(zze com_google_android_gms_plus_internal_zze) {
            com_google_android_gms_plus_internal_zze.zzd(this, this.zzaHN);
        }
    }

    public Person getCurrentPerson(GoogleApiClient googleApiClient) {
        return Plus.zzf(googleApiClient, true).zzxu();
    }

    public PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, Collection<String> personIds) {
        return googleApiClient.zza(new C10514(this, googleApiClient, personIds));
    }

    public PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, String... personIds) {
        return googleApiClient.zza(new C10525(this, googleApiClient, personIds));
    }

    public PendingResult<LoadPeopleResult> loadConnected(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new C10503(this, googleApiClient));
    }

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, int orderBy, String pageToken) {
        return googleApiClient.zza(new C10481(this, googleApiClient, orderBy, pageToken));
    }

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, String pageToken) {
        return googleApiClient.zza(new C10492(this, googleApiClient, pageToken));
    }
}
