package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Payments;
import com.google.android.gms.wallet.Wallet.zzb;

public class zzqw implements Payments {

    /* renamed from: com.google.android.gms.internal.zzqw.1 */
    class C10541 extends zzb {
        final /* synthetic */ zzqw zzaSo;
        final /* synthetic */ int zzaww;

        C10541(zzqw com_google_android_gms_internal_zzqw, GoogleApiClient googleApiClient, int i) {
            this.zzaSo = com_google_android_gms_internal_zzqw;
            this.zzaww = i;
            super(googleApiClient);
        }

        protected void zza(zzqx com_google_android_gms_internal_zzqx) {
            com_google_android_gms_internal_zzqx.zzjB(this.zzaww);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqw.2 */
    class C10552 extends zzb {
        final /* synthetic */ zzqw zzaSo;
        final /* synthetic */ MaskedWalletRequest zzaSp;
        final /* synthetic */ int zzaww;

        C10552(zzqw com_google_android_gms_internal_zzqw, GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int i) {
            this.zzaSo = com_google_android_gms_internal_zzqw;
            this.zzaSp = maskedWalletRequest;
            this.zzaww = i;
            super(googleApiClient);
        }

        protected void zza(zzqx com_google_android_gms_internal_zzqx) {
            com_google_android_gms_internal_zzqx.zza(this.zzaSp, this.zzaww);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqw.3 */
    class C10563 extends zzb {
        final /* synthetic */ zzqw zzaSo;
        final /* synthetic */ FullWalletRequest zzaSq;
        final /* synthetic */ int zzaww;

        C10563(zzqw com_google_android_gms_internal_zzqw, GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int i) {
            this.zzaSo = com_google_android_gms_internal_zzqw;
            this.zzaSq = fullWalletRequest;
            this.zzaww = i;
            super(googleApiClient);
        }

        protected void zza(zzqx com_google_android_gms_internal_zzqx) {
            com_google_android_gms_internal_zzqx.zza(this.zzaSq, this.zzaww);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqw.4 */
    class C10574 extends zzb {
        final /* synthetic */ zzqw zzaSo;
        final /* synthetic */ String zzaSr;
        final /* synthetic */ String zzaSs;
        final /* synthetic */ int zzaww;

        C10574(zzqw com_google_android_gms_internal_zzqw, GoogleApiClient googleApiClient, String str, String str2, int i) {
            this.zzaSo = com_google_android_gms_internal_zzqw;
            this.zzaSr = str;
            this.zzaSs = str2;
            this.zzaww = i;
            super(googleApiClient);
        }

        protected void zza(zzqx com_google_android_gms_internal_zzqx) {
            com_google_android_gms_internal_zzqx.zze(this.zzaSr, this.zzaSs, this.zzaww);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqw.5 */
    class C10585 extends zzb {
        final /* synthetic */ zzqw zzaSo;
        final /* synthetic */ NotifyTransactionStatusRequest zzaSt;

        C10585(zzqw com_google_android_gms_internal_zzqw, GoogleApiClient googleApiClient, NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
            this.zzaSo = com_google_android_gms_internal_zzqw;
            this.zzaSt = notifyTransactionStatusRequest;
            super(googleApiClient);
        }

        protected void zza(zzqx com_google_android_gms_internal_zzqx) {
            com_google_android_gms_internal_zzqx.zza(this.zzaSt);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqw.6 */
    class C10596 extends zzb {
        final /* synthetic */ zzqw zzaSo;
        final /* synthetic */ int zzaww;

        C10596(zzqw com_google_android_gms_internal_zzqw, GoogleApiClient googleApiClient, int i) {
            this.zzaSo = com_google_android_gms_internal_zzqw;
            this.zzaww = i;
            super(googleApiClient);
        }

        protected void zza(zzqx com_google_android_gms_internal_zzqx) {
            com_google_android_gms_internal_zzqx.zzjC(this.zzaww);
            setResult(Status.zzXP);
        }
    }

    public void changeMaskedWallet(GoogleApiClient googleApiClient, String googleTransactionId, String merchantTransactionId, int requestCode) {
        googleApiClient.zza(new C10574(this, googleApiClient, googleTransactionId, merchantTransactionId, requestCode));
    }

    public void checkForPreAuthorization(GoogleApiClient googleApiClient, int requestCode) {
        googleApiClient.zza(new C10541(this, googleApiClient, requestCode));
    }

    public void isNewUser(GoogleApiClient googleApiClient, int requestCode) {
        googleApiClient.zza(new C10596(this, googleApiClient, requestCode));
    }

    public void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest request, int requestCode) {
        googleApiClient.zza(new C10563(this, googleApiClient, request, requestCode));
    }

    public void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest request, int requestCode) {
        googleApiClient.zza(new C10552(this, googleApiClient, request, requestCode));
    }

    public void notifyTransactionStatus(GoogleApiClient googleApiClient, NotifyTransactionStatusRequest request) {
        googleApiClient.zza(new C10585(this, googleApiClient, request));
    }
}
