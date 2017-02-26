package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;

public final class zzc implements CredentialsApi {

    private static class zza extends zza {
        private zzb<Status> zzPg;

        zza(zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status) {
            this.zzPg = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status;
        }

        public void onStatusResult(Status status) {
            this.zzPg.zzm(status);
        }
    }

    /* renamed from: com.google.android.gms.auth.api.credentials.internal.zzc.1 */
    class C08521 extends zzd<CredentialRequestResult> {
        final /* synthetic */ CredentialRequest zzPc;
        final /* synthetic */ zzc zzPd;

        /* renamed from: com.google.android.gms.auth.api.credentials.internal.zzc.1.1 */
        class C08451 extends zza {
            final /* synthetic */ C08521 zzPe;

            C08451(C08521 c08521) {
                this.zzPe = c08521;
            }

            public void onCredentialResult(Status status, Credential credential) {
                this.zzPe.setResult(new zzb(status, credential));
            }
        }

        C08521(zzc com_google_android_gms_auth_api_credentials_internal_zzc, GoogleApiClient googleApiClient, CredentialRequest credentialRequest) {
            this.zzPd = com_google_android_gms_auth_api_credentials_internal_zzc;
            this.zzPc = credentialRequest;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzk(x0);
        }

        protected void zza(Context context, ICredentialsService iCredentialsService) throws RemoteException {
            iCredentialsService.performCredentialsRequestOperation(new C08451(this), this.zzPc);
        }

        protected CredentialRequestResult zzk(Status status) {
            return zzb.zzj(status);
        }
    }

    /* renamed from: com.google.android.gms.auth.api.credentials.internal.zzc.2 */
    class C08532 extends zzd<Status> {
        final /* synthetic */ zzc zzPd;
        final /* synthetic */ Credential zzPf;

        C08532(zzc com_google_android_gms_auth_api_credentials_internal_zzc, GoogleApiClient googleApiClient, Credential credential) {
            this.zzPd = com_google_android_gms_auth_api_credentials_internal_zzc;
            this.zzPf = credential;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(Context context, ICredentialsService iCredentialsService) throws RemoteException {
            iCredentialsService.performCredentialsSaveOperation(new zza(this), new SaveRequest(this.zzPf));
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.auth.api.credentials.internal.zzc.3 */
    class C08543 extends zzd<Status> {
        final /* synthetic */ zzc zzPd;
        final /* synthetic */ Credential zzPf;

        C08543(zzc com_google_android_gms_auth_api_credentials_internal_zzc, GoogleApiClient googleApiClient, Credential credential) {
            this.zzPd = com_google_android_gms_auth_api_credentials_internal_zzc;
            this.zzPf = credential;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(Context context, ICredentialsService iCredentialsService) throws RemoteException {
            iCredentialsService.performCredentialsDeleteOperation(new zza(this), new DeleteRequest(this.zzPf));
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.auth.api.credentials.internal.zzc.4 */
    class C08554 extends zzd<Status> {
        final /* synthetic */ zzc zzPd;

        C08554(zzc com_google_android_gms_auth_api_credentials_internal_zzc, GoogleApiClient googleApiClient) {
            this.zzPd = com_google_android_gms_auth_api_credentials_internal_zzc;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(Context context, ICredentialsService iCredentialsService) throws RemoteException {
            iCredentialsService.performDisableAutoSignInOperation(new zza(this));
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    public PendingResult<Status> delete(GoogleApiClient client, Credential credential) {
        return client.zzb(new C08543(this, client, credential));
    }

    public PendingResult<Status> disableAutoSignIn(GoogleApiClient client) {
        return client.zzb(new C08554(this, client));
    }

    public PendingResult<CredentialRequestResult> request(GoogleApiClient client, CredentialRequest request) {
        return client.zza(new C08521(this, client, request));
    }

    public PendingResult<Status> save(GoogleApiClient client, Credential credential) {
        return client.zzb(new C08532(this, client, credential));
    }
}
