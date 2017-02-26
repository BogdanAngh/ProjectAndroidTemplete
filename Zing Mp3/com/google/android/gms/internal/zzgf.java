package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzd;
import java.util.concurrent.Future;

@zzji
public class zzgf {

    /* renamed from: com.google.android.gms.internal.zzgf.1 */
    class C13341 implements Runnable {
        final /* synthetic */ Context zzang;
        final /* synthetic */ VersionInfoParcel zzbtc;
        final /* synthetic */ zza zzbtd;
        final /* synthetic */ zzav zzbte;
        final /* synthetic */ zzd zzbtf;
        final /* synthetic */ String zzbtg;
        final /* synthetic */ zzgf zzbth;

        C13341(zzgf com_google_android_gms_internal_zzgf, Context context, VersionInfoParcel versionInfoParcel, zza com_google_android_gms_internal_zzgf_zza, zzav com_google_android_gms_internal_zzav, zzd com_google_android_gms_ads_internal_zzd, String str) {
            this.zzbth = com_google_android_gms_internal_zzgf;
            this.zzang = context;
            this.zzbtc = versionInfoParcel;
            this.zzbtd = com_google_android_gms_internal_zzgf_zza;
            this.zzbte = com_google_android_gms_internal_zzav;
            this.zzbtf = com_google_android_gms_ads_internal_zzd;
            this.zzbtg = str;
        }

        public void run() {
            this.zzbth.zza(this.zzang, this.zzbtc, this.zzbtd, this.zzbte, this.zzbtf).zzbp(this.zzbtg);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.2 */
    class C13352 implements com.google.android.gms.internal.zzge.zza {
        final /* synthetic */ zza zzbtd;
        final /* synthetic */ zzgf zzbth;

        C13352(zzgf com_google_android_gms_internal_zzgf, zza com_google_android_gms_internal_zzgf_zza) {
            this.zzbth = com_google_android_gms_internal_zzgf;
            this.zzbtd = com_google_android_gms_internal_zzgf_zza;
        }

        public void zznx() {
            this.zzbtd.zzh((zzge) this.zzbtd.zzbti);
        }
    }

    private static class zza<JavascriptEngine> extends zzlq<JavascriptEngine> {
        JavascriptEngine zzbti;

        private zza() {
        }
    }

    private zzge zza(Context context, VersionInfoParcel versionInfoParcel, zza<zzge> com_google_android_gms_internal_zzgf_zza_com_google_android_gms_internal_zzge, zzav com_google_android_gms_internal_zzav, zzd com_google_android_gms_ads_internal_zzd) {
        zzge com_google_android_gms_internal_zzgg = new zzgg(context, versionInfoParcel, com_google_android_gms_internal_zzav, com_google_android_gms_ads_internal_zzd);
        com_google_android_gms_internal_zzgf_zza_com_google_android_gms_internal_zzge.zzbti = com_google_android_gms_internal_zzgg;
        com_google_android_gms_internal_zzgg.zza(new C13352(this, com_google_android_gms_internal_zzgf_zza_com_google_android_gms_internal_zzge));
        return com_google_android_gms_internal_zzgg;
    }

    public Future<zzge> zza(Context context, VersionInfoParcel versionInfoParcel, String str, zzav com_google_android_gms_internal_zzav, zzd com_google_android_gms_ads_internal_zzd) {
        Future com_google_android_gms_internal_zzgf_zza = new zza();
        zzlb.zzcvl.post(new C13341(this, context, versionInfoParcel, com_google_android_gms_internal_zzgf_zza, com_google_android_gms_internal_zzav, com_google_android_gms_ads_internal_zzd, str));
        return com_google_android_gms_internal_zzgf_zza;
    }
}
