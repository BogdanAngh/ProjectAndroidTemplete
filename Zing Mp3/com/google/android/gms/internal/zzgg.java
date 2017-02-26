package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import com.facebook.ads.AudienceNetworkActivity;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.overlay.zzp;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzme.zza;
import org.json.JSONObject;

@zzji
public class zzgg implements zzge {
    private final zzmd zzbnz;

    /* renamed from: com.google.android.gms.internal.zzgg.1 */
    class C13361 implements Runnable {
        final /* synthetic */ String zzbtj;
        final /* synthetic */ JSONObject zzbtk;
        final /* synthetic */ zzgg zzbtl;

        C13361(zzgg com_google_android_gms_internal_zzgg, String str, JSONObject jSONObject) {
            this.zzbtl = com_google_android_gms_internal_zzgg;
            this.zzbtj = str;
            this.zzbtk = jSONObject;
        }

        public void run() {
            this.zzbtl.zzbnz.zza(this.zzbtj, this.zzbtk);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgg.2 */
    class C13372 implements Runnable {
        final /* synthetic */ String zzbtj;
        final /* synthetic */ zzgg zzbtl;
        final /* synthetic */ String zzbtm;

        C13372(zzgg com_google_android_gms_internal_zzgg, String str, String str2) {
            this.zzbtl = com_google_android_gms_internal_zzgg;
            this.zzbtj = str;
            this.zzbtm = str2;
        }

        public void run() {
            this.zzbtl.zzbnz.zzi(this.zzbtj, this.zzbtm);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgg.3 */
    class C13383 implements Runnable {
        final /* synthetic */ zzgg zzbtl;
        final /* synthetic */ String zzbtn;

        C13383(zzgg com_google_android_gms_internal_zzgg, String str) {
            this.zzbtl = com_google_android_gms_internal_zzgg;
            this.zzbtn = str;
        }

        public void run() {
            this.zzbtl.zzbnz.loadData(this.zzbtn, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgg.4 */
    class C13394 implements Runnable {
        final /* synthetic */ zzgg zzbtl;
        final /* synthetic */ String zzbtn;

        C13394(zzgg com_google_android_gms_internal_zzgg, String str) {
            this.zzbtl = com_google_android_gms_internal_zzgg;
            this.zzbtn = str;
        }

        public void run() {
            this.zzbtl.zzbnz.loadData(this.zzbtn, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgg.5 */
    class C13405 implements Runnable {
        final /* synthetic */ String zzbtg;
        final /* synthetic */ zzgg zzbtl;

        C13405(zzgg com_google_android_gms_internal_zzgg, String str) {
            this.zzbtl = com_google_android_gms_internal_zzgg;
            this.zzbtg = str;
        }

        public void run() {
            this.zzbtl.zzbnz.loadUrl(this.zzbtg);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgg.6 */
    class C13416 implements zza {
        final /* synthetic */ zzgg zzbtl;
        final /* synthetic */ zzge.zza zzbto;

        C13416(zzgg com_google_android_gms_internal_zzgg, zzge.zza com_google_android_gms_internal_zzge_zza) {
            this.zzbtl = com_google_android_gms_internal_zzgg;
            this.zzbto = com_google_android_gms_internal_zzge_zza;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, boolean z) {
            this.zzbto.zznx();
        }
    }

    public zzgg(Context context, VersionInfoParcel versionInfoParcel, @Nullable zzav com_google_android_gms_internal_zzav, zzd com_google_android_gms_ads_internal_zzd) {
        this.zzbnz = zzu.zzgn().zza(context, new AdSizeParcel(), false, false, com_google_android_gms_internal_zzav, versionInfoParcel, null, null, com_google_android_gms_ads_internal_zzd);
        this.zzbnz.getWebView().setWillNotDraw(true);
    }

    private void runOnUiThread(Runnable runnable) {
        if (zzm.zzkr().zzwq()) {
            runnable.run();
        } else {
            zzlb.zzcvl.post(runnable);
        }
    }

    public void destroy() {
        this.zzbnz.destroy();
    }

    public void zza(com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzfa com_google_android_gms_internal_zzfa, zzp com_google_android_gms_ads_internal_overlay_zzp, boolean z, zzfg com_google_android_gms_internal_zzfg, zzfi com_google_android_gms_internal_zzfi, zze com_google_android_gms_ads_internal_zze, zzhw com_google_android_gms_internal_zzhw) {
        this.zzbnz.zzxc().zza(com_google_android_gms_ads_internal_client_zza, com_google_android_gms_ads_internal_overlay_zzg, com_google_android_gms_internal_zzfa, com_google_android_gms_ads_internal_overlay_zzp, z, com_google_android_gms_internal_zzfg, com_google_android_gms_internal_zzfi, new zze(this.zzbnz.getContext(), false), com_google_android_gms_internal_zzhw, null);
    }

    public void zza(zzge.zza com_google_android_gms_internal_zzge_zza) {
        this.zzbnz.zzxc().zza(new C13416(this, com_google_android_gms_internal_zzge_zza));
    }

    public void zza(String str, zzfe com_google_android_gms_internal_zzfe) {
        this.zzbnz.zzxc().zza(str, com_google_android_gms_internal_zzfe);
    }

    public void zza(String str, JSONObject jSONObject) {
        runOnUiThread(new C13361(this, str, jSONObject));
    }

    public void zzb(String str, zzfe com_google_android_gms_internal_zzfe) {
        this.zzbnz.zzxc().zzb(str, com_google_android_gms_internal_zzfe);
    }

    public void zzb(String str, JSONObject jSONObject) {
        this.zzbnz.zzb(str, jSONObject);
    }

    public void zzbo(String str) {
        runOnUiThread(new C13383(this, String.format("<!DOCTYPE html><html><head><script src=\"%s\"></script></head><body></body></html>", new Object[]{str})));
    }

    public void zzbp(String str) {
        runOnUiThread(new C13405(this, str));
    }

    public void zzbq(String str) {
        runOnUiThread(new C13394(this, str));
    }

    public void zzi(String str, String str2) {
        runOnUiThread(new C13372(this, str, str2));
    }

    public zzgj zznw() {
        return new zzgk(this);
    }
}
