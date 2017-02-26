package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.overlay.zzf;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.internal.zzie.zza;
import org.json.JSONObject;

@zzgd
public class zzbd implements zzbb {
    private final zzid zzoA;

    /* renamed from: com.google.android.gms.internal.zzbd.1 */
    class C01841 implements Runnable {
        final /* synthetic */ String zzrb;
        final /* synthetic */ JSONObject zzrc;
        final /* synthetic */ zzbd zzrd;

        C01841(zzbd com_google_android_gms_internal_zzbd, String str, JSONObject jSONObject) {
            this.zzrd = com_google_android_gms_internal_zzbd;
            this.zzrb = str;
            this.zzrc = jSONObject;
        }

        public void run() {
            this.zzrd.zzoA.zza(this.zzrb, this.zzrc);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.2 */
    class C01852 implements Runnable {
        final /* synthetic */ String zzrb;
        final /* synthetic */ zzbd zzrd;
        final /* synthetic */ String zzre;

        C01852(zzbd com_google_android_gms_internal_zzbd, String str, String str2) {
            this.zzrd = com_google_android_gms_internal_zzbd;
            this.zzrb = str;
            this.zzre = str2;
        }

        public void run() {
            this.zzrd.zzoA.zza(this.zzrb, this.zzre);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.3 */
    class C01863 implements Runnable {
        final /* synthetic */ zzbd zzrd;
        final /* synthetic */ String zzrf;

        C01863(zzbd com_google_android_gms_internal_zzbd, String str) {
            this.zzrd = com_google_android_gms_internal_zzbd;
            this.zzrf = str;
        }

        public void run() {
            this.zzrd.zzoA.loadData(this.zzrf, "text/html", "UTF-8");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.4 */
    class C01874 implements Runnable {
        final /* synthetic */ zzbd zzrd;
        final /* synthetic */ String zzrf;

        C01874(zzbd com_google_android_gms_internal_zzbd, String str) {
            this.zzrd = com_google_android_gms_internal_zzbd;
            this.zzrf = str;
        }

        public void run() {
            this.zzrd.zzoA.loadData(this.zzrf, "text/html", "UTF-8");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.5 */
    class C01885 implements Runnable {
        final /* synthetic */ String zzqY;
        final /* synthetic */ zzbd zzrd;

        C01885(zzbd com_google_android_gms_internal_zzbd, String str) {
            this.zzrd = com_google_android_gms_internal_zzbd;
            this.zzqY = str;
        }

        public void run() {
            this.zzrd.zzoA.loadUrl(this.zzqY);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbd.6 */
    class C04436 implements zza {
        final /* synthetic */ zzbd zzrd;
        final /* synthetic */ zzbb.zza zzrg;

        C04436(zzbd com_google_android_gms_internal_zzbd, zzbb.zza com_google_android_gms_internal_zzbb_zza) {
            this.zzrd = com_google_android_gms_internal_zzbd;
            this.zzrg = com_google_android_gms_internal_zzbb_zza;
        }

        public void zza(zzid com_google_android_gms_internal_zzid, boolean z) {
            this.zzrg.zzcf();
        }
    }

    public zzbd(Context context, VersionInfoParcel versionInfoParcel) {
        this.zzoA = zzo.zzbw().zza(context, new AdSizeParcel(), false, false, null, versionInfoParcel);
        this.zzoA.setWillNotDraw(true);
    }

    private void runOnUiThread(Runnable runnable) {
        if (zzk.zzcA().zzgw()) {
            runnable.run();
        } else {
            zzhl.zzGk.post(runnable);
        }
    }

    public void destroy() {
        this.zzoA.destroy();
    }

    public void zza(com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza, zzf com_google_android_gms_ads_internal_overlay_zzf, zzde com_google_android_gms_internal_zzde, com.google.android.gms.ads.internal.overlay.zzk com_google_android_gms_ads_internal_overlay_zzk, boolean z, zzdi com_google_android_gms_internal_zzdi, zzdk com_google_android_gms_internal_zzdk, zzd com_google_android_gms_ads_internal_zzd, zzev com_google_android_gms_internal_zzev) {
        this.zzoA.zzgF().zzb(com_google_android_gms_ads_internal_client_zza, com_google_android_gms_ads_internal_overlay_zzf, com_google_android_gms_internal_zzde, com_google_android_gms_ads_internal_overlay_zzk, z, com_google_android_gms_internal_zzdi, com_google_android_gms_internal_zzdk, new zzd(false), com_google_android_gms_internal_zzev);
    }

    public void zza(zzbb.zza com_google_android_gms_internal_zzbb_zza) {
        this.zzoA.zzgF().zza(new C04436(this, com_google_android_gms_internal_zzbb_zza));
    }

    public void zza(String str, zzdg com_google_android_gms_internal_zzdg) {
        this.zzoA.zzgF().zza(str, com_google_android_gms_internal_zzdg);
    }

    public void zza(String str, String str2) {
        runOnUiThread(new C01852(this, str, str2));
    }

    public void zza(String str, JSONObject jSONObject) {
        runOnUiThread(new C01841(this, str, jSONObject));
    }

    public void zzb(String str, zzdg com_google_android_gms_internal_zzdg) {
        this.zzoA.zzgF().zzb(str, com_google_android_gms_internal_zzdg);
    }

    public zzbf zzce() {
        return new zzbg(this);
    }

    public void zzr(String str) {
        runOnUiThread(new C01863(this, String.format("<!DOCTYPE html><html><head><script src=\"%s\"></script></head><body></body></html>", new Object[]{str})));
    }

    public void zzs(String str) {
        runOnUiThread(new C01885(this, str));
    }

    public void zzt(String str) {
        runOnUiThread(new C01874(this, str));
    }
}
