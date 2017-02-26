package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.AttestationData;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetApi.AttestationResult;

public class zzpj implements SafetyNetApi {

    static class zza implements AttestationResult {
        private final Status zzOt;
        private final AttestationData zzaJB;

        public zza(Status status, AttestationData attestationData) {
            this.zzOt = status;
            this.zzaJB = attestationData;
        }

        public String getJwsResult() {
            return this.zzaJB == null ? null : this.zzaJB.getJwsResult();
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    static abstract class zzb extends zzpg<AttestationResult> {
        protected zzph zzaJC;

        /* renamed from: com.google.android.gms.internal.zzpj.zzb.1 */
        class C08501 extends zzpf {
            final /* synthetic */ zzb zzaJD;

            C08501(zzb com_google_android_gms_internal_zzpj_zzb) {
                this.zzaJD = com_google_android_gms_internal_zzpj_zzb;
            }

            public void zza(Status status, AttestationData attestationData) {
                this.zzaJD.setResult(new zza(status, attestationData));
            }
        }

        public zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
            this.zzaJC = new C08501(this);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzaR(x0);
        }

        protected AttestationResult zzaR(Status status) {
            return new zza(status, null);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzpj.1 */
    class C10531 extends zzb {
        final /* synthetic */ zzpj zzaJA;
        final /* synthetic */ byte[] zzaJz;

        C10531(zzpj com_google_android_gms_internal_zzpj, GoogleApiClient googleApiClient, byte[] bArr) {
            this.zzaJA = com_google_android_gms_internal_zzpj;
            this.zzaJz = bArr;
            super(googleApiClient);
        }

        protected void zza(zzpk com_google_android_gms_internal_zzpk) throws RemoteException {
            com_google_android_gms_internal_zzpk.zza(this.zzaJC, this.zzaJz);
        }
    }

    public PendingResult<AttestationResult> attest(GoogleApiClient googleApiClient, byte[] nonce) {
        return googleApiClient.zza(new C10531(this, googleApiClient, nonce));
    }
}
