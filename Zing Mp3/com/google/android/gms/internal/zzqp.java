package com.google.android.gms.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

public abstract class zzqp extends zzro implements OnCancelListener {
    protected boolean mStarted;
    protected final GoogleApiAvailability xP;
    private ConnectionResult yA;
    private int yB;
    private final Handler yC;
    protected boolean yz;

    private class zza implements Runnable {
        final /* synthetic */ zzqp yD;

        /* renamed from: com.google.android.gms.internal.zzqp.zza.1 */
        class C14761 extends com.google.android.gms.internal.zzrj.zza {
            final /* synthetic */ Dialog yE;
            final /* synthetic */ zza yF;

            C14761(zza com_google_android_gms_internal_zzqp_zza, Dialog dialog) {
                this.yF = com_google_android_gms_internal_zzqp_zza;
                this.yE = dialog;
            }

            public void zzarr() {
                this.yF.yD.zzarq();
                if (this.yE.isShowing()) {
                    this.yE.dismiss();
                }
            }
        }

        private zza(zzqp com_google_android_gms_internal_zzqp) {
            this.yD = com_google_android_gms_internal_zzqp;
        }

        @MainThread
        public void run() {
            if (!this.yD.mStarted) {
                return;
            }
            if (this.yD.yA.hasResolution()) {
                this.yD.Bf.startActivityForResult(GoogleApiActivity.zzb(this.yD.getActivity(), this.yD.yA.getResolution(), this.yD.yB, false), 1);
            } else if (this.yD.xP.isUserResolvableError(this.yD.yA.getErrorCode())) {
                this.yD.xP.zza(this.yD.getActivity(), this.yD.Bf, this.yD.yA.getErrorCode(), 2, this.yD);
            } else if (this.yD.yA.getErrorCode() == 18) {
                this.yD.xP.zza(this.yD.getActivity().getApplicationContext(), new C14761(this, this.yD.xP.zza(this.yD.getActivity(), this.yD)));
            } else {
                this.yD.zza(this.yD.yA, this.yD.yB);
            }
        }
    }

    protected zzqp(zzrp com_google_android_gms_internal_zzrp) {
        this(com_google_android_gms_internal_zzrp, GoogleApiAvailability.getInstance());
    }

    zzqp(zzrp com_google_android_gms_internal_zzrp, GoogleApiAvailability googleApiAvailability) {
        super(com_google_android_gms_internal_zzrp);
        this.yB = -1;
        this.yC = new Handler(Looper.getMainLooper());
        this.xP = googleApiAvailability;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r6, int r7, android.content.Intent r8) {
        /*
        r5 = this;
        r4 = 18;
        r2 = 13;
        r0 = 1;
        r1 = 0;
        switch(r6) {
            case 1: goto L_0x0027;
            case 2: goto L_0x0010;
            default: goto L_0x0009;
        };
    L_0x0009:
        r0 = r1;
    L_0x000a:
        if (r0 == 0) goto L_0x003d;
    L_0x000c:
        r5.zzarq();
    L_0x000f:
        return;
    L_0x0010:
        r2 = r5.xP;
        r3 = r5.getActivity();
        r2 = r2.isGooglePlayServicesAvailable(r3);
        if (r2 != 0) goto L_0x0047;
    L_0x001c:
        r1 = r5.yA;
        r1 = r1.getErrorCode();
        if (r1 != r4) goto L_0x000a;
    L_0x0024:
        if (r2 != r4) goto L_0x000a;
    L_0x0026:
        goto L_0x000f;
    L_0x0027:
        r3 = -1;
        if (r7 == r3) goto L_0x000a;
    L_0x002a:
        if (r7 != 0) goto L_0x0009;
    L_0x002c:
        if (r8 == 0) goto L_0x0045;
    L_0x002e:
        r0 = "<<ResolutionFailureErrorDetail>>";
        r0 = r8.getIntExtra(r0, r2);
    L_0x0034:
        r2 = new com.google.android.gms.common.ConnectionResult;
        r3 = 0;
        r2.<init>(r0, r3);
        r5.yA = r2;
        goto L_0x0009;
    L_0x003d:
        r0 = r5.yA;
        r1 = r5.yB;
        r5.zza(r0, r1);
        goto L_0x000f;
    L_0x0045:
        r0 = r2;
        goto L_0x0034;
    L_0x0047:
        r0 = r1;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqp.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, null), this.yB);
        zzarq();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.yz = bundle.getBoolean("resolving_error", false);
            if (this.yz) {
                this.yB = bundle.getInt("failed_client_id", -1);
                this.yA = new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution"));
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("resolving_error", this.yz);
        if (this.yz) {
            bundle.putInt("failed_client_id", this.yB);
            bundle.putInt("failed_status", this.yA.getErrorCode());
            bundle.putParcelable("failed_resolution", this.yA.getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zza(ConnectionResult connectionResult, int i);

    protected abstract void zzarm();

    protected void zzarq() {
        this.yB = -1;
        this.yz = false;
        this.yA = null;
        zzarm();
    }

    public void zzb(ConnectionResult connectionResult, int i) {
        if (!this.yz) {
            this.yz = true;
            this.yB = i;
            this.yA = connectionResult;
            this.yC.post(new zza());
        }
    }
}
