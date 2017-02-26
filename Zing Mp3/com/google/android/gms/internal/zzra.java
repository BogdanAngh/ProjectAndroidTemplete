package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.internal.zzqo.zza;

public class zzra implements zzre {
    private final zzrf zA;
    private boolean zB;

    /* renamed from: com.google.android.gms.internal.zzra.1 */
    class C14821 extends zza {
        final /* synthetic */ zzra zC;

        C14821(zzra com_google_android_gms_internal_zzra, zzre com_google_android_gms_internal_zzre) {
            this.zC = com_google_android_gms_internal_zzra;
            super(com_google_android_gms_internal_zzre);
        }

        public void zzaso() {
            this.zC.onConnectionSuspended(1);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzra.2 */
    class C14832 extends zza {
        final /* synthetic */ zzra zC;

        C14832(zzra com_google_android_gms_internal_zzra, zzre com_google_android_gms_internal_zzre) {
            this.zC = com_google_android_gms_internal_zzra;
            super(com_google_android_gms_internal_zzre);
        }

        public void zzaso() {
            this.zC.zA.AC.zzn(null);
        }
    }

    public zzra(zzrf com_google_android_gms_internal_zzrf) {
        this.zB = false;
        this.zA = com_google_android_gms_internal_zzrf;
    }

    private <A extends zzb> void zzd(zza<? extends Result, A> com_google_android_gms_internal_zzqo_zza__extends_com_google_android_gms_common_api_Result__A) throws DeadObjectException {
        this.zA.yW.Ap.zzb(com_google_android_gms_internal_zzqo_zza__extends_com_google_android_gms_common_api_Result__A);
        zzb zzb = this.zA.yW.zzb(com_google_android_gms_internal_zzqo_zza__extends_com_google_android_gms_common_api_Result__A.zzaqv());
        if (zzb.isConnected() || !this.zA.Ay.containsKey(com_google_android_gms_internal_zzqo_zza__extends_com_google_android_gms_common_api_Result__A.zzaqv())) {
            if (zzb instanceof zzag) {
                zzb = ((zzag) zzb).zzawt();
            }
            com_google_android_gms_internal_zzqo_zza__extends_com_google_android_gms_common_api_Result__A.zzb(zzb);
            return;
        }
        com_google_android_gms_internal_zzqo_zza__extends_com_google_android_gms_common_api_Result__A.zzaa(new Status(17));
    }

    public void begin() {
    }

    public void connect() {
        if (this.zB) {
            this.zB = false;
            this.zA.zza(new C14832(this, this));
        }
    }

    public boolean disconnect() {
        if (this.zB) {
            return false;
        }
        if (this.zA.yW.zzata()) {
            this.zB = true;
            for (zzsf zzaud : this.zA.yW.Ao) {
                zzaud.zzaud();
            }
            return false;
        }
        this.zA.zzh(null);
        return true;
    }

    public void onConnected(Bundle bundle) {
    }

    public void onConnectionSuspended(int i) {
        this.zA.zzh(null);
        this.zA.AC.zzc(i, this.zB);
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zza(T t) {
        return zzb(t);
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
    }

    void zzasn() {
        if (this.zB) {
            this.zB = false;
            this.zA.yW.Ap.release();
            disconnect();
        }
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzb(T t) {
        try {
            zzd(t);
        } catch (DeadObjectException e) {
            this.zA.zza(new C14821(this, this));
        }
        return t;
    }
}
