package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzbb.zza;
import java.util.concurrent.Future;

@zzgd
public class zzbc {

    /* renamed from: com.google.android.gms.internal.zzbc.1 */
    class C01831 implements Runnable {
        final /* synthetic */ Context zzqV;
        final /* synthetic */ VersionInfoParcel zzqW;
        final /* synthetic */ zzhs zzqX;
        final /* synthetic */ String zzqY;
        final /* synthetic */ zzbc zzqZ;

        C01831(zzbc com_google_android_gms_internal_zzbc, Context context, VersionInfoParcel versionInfoParcel, zzhs com_google_android_gms_internal_zzhs, String str) {
            this.zzqZ = com_google_android_gms_internal_zzbc;
            this.zzqV = context;
            this.zzqW = versionInfoParcel;
            this.zzqX = com_google_android_gms_internal_zzhs;
            this.zzqY = str;
        }

        public void run() {
            this.zzqZ.zza(this.zzqV, this.zzqW, this.zzqX).zzs(this.zzqY);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbc.2 */
    class C04422 implements zza {
        final /* synthetic */ zzhs zzqX;
        final /* synthetic */ zzbc zzqZ;
        final /* synthetic */ zzbb zzra;

        C04422(zzbc com_google_android_gms_internal_zzbc, zzhs com_google_android_gms_internal_zzhs, zzbb com_google_android_gms_internal_zzbb) {
            this.zzqZ = com_google_android_gms_internal_zzbc;
            this.zzqX = com_google_android_gms_internal_zzhs;
            this.zzra = com_google_android_gms_internal_zzbb;
        }

        public void zzcf() {
            this.zzqX.zzf(this.zzra);
        }
    }

    protected zzbb zza(Context context, VersionInfoParcel versionInfoParcel, zzhs<zzbb> com_google_android_gms_internal_zzhs_com_google_android_gms_internal_zzbb) {
        zzbb com_google_android_gms_internal_zzbd = new zzbd(context, versionInfoParcel);
        com_google_android_gms_internal_zzbd.zza(new C04422(this, com_google_android_gms_internal_zzhs_com_google_android_gms_internal_zzbb, com_google_android_gms_internal_zzbd));
        return com_google_android_gms_internal_zzbd;
    }

    public Future<zzbb> zza(Context context, VersionInfoParcel versionInfoParcel, String str) {
        Future com_google_android_gms_internal_zzhs = new zzhs();
        zzhl.zzGk.post(new C01831(this, context, versionInfoParcel, com_google_android_gms_internal_zzhs, str));
        return com_google_android_gms_internal_zzhs;
    }
}
