package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzav;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzgq;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzkw;
import com.google.android.gms.internal.zzla;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.internal.zzlw;
import com.google.android.gms.internal.zzlx;
import org.json.JSONObject;

@zzji
public class zzb extends zzkw implements com.google.android.gms.ads.internal.request.zzc.zza {
    private final Context mContext;
    private final zzav zzbnx;
    zzgq zzbwc;
    private AdRequestInfoParcel zzbws;
    AdResponseParcel zzcgg;
    private Runnable zzcgh;
    private final Object zzcgi;
    private final com.google.android.gms.ads.internal.request.zza.zza zzcjh;
    private final com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza zzcji;
    zzld zzcjj;

    /* renamed from: com.google.android.gms.ads.internal.request.zzb.1 */
    class C10971 implements Runnable {
        final /* synthetic */ zzb zzcjk;

        C10971(zzb com_google_android_gms_ads_internal_request_zzb) {
            this.zzcjk = com_google_android_gms_ads_internal_request_zzb;
        }

        public void run() {
            synchronized (this.zzcjk.zzcgi) {
                if (this.zzcjk.zzcjj == null) {
                    return;
                }
                this.zzcjk.onStop();
                this.zzcjk.zzd(2, "Timed out waiting for ad response.");
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzb.2 */
    class C10982 implements Runnable {
        final /* synthetic */ zzb zzcjk;
        final /* synthetic */ zzlw zzcjl;

        C10982(zzb com_google_android_gms_ads_internal_request_zzb, zzlw com_google_android_gms_internal_zzlw) {
            this.zzcjk = com_google_android_gms_ads_internal_request_zzb;
            this.zzcjl = com_google_android_gms_internal_zzlw;
        }

        public void run() {
            synchronized (this.zzcjk.zzcgi) {
                this.zzcjk.zzcjj = this.zzcjk.zza(this.zzcjk.zzcji.zzari, this.zzcjl);
                if (this.zzcjk.zzcjj == null) {
                    this.zzcjk.zzd(0, "Could not start the ad request service.");
                    zzlb.zzcvl.removeCallbacks(this.zzcjk.zzcgh);
                }
            }
        }
    }

    @zzji
    static final class zza extends Exception {
        private final int zzcgw;

        public zza(String str, int i) {
            super(str);
            this.zzcgw = i;
        }

        public int getErrorCode() {
            return this.zzcgw;
        }
    }

    public zzb(Context context, com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza, zzav com_google_android_gms_internal_zzav, com.google.android.gms.ads.internal.request.zza.zza com_google_android_gms_ads_internal_request_zza_zza) {
        this.zzcgi = new Object();
        this.zzcjh = com_google_android_gms_ads_internal_request_zza_zza;
        this.mContext = context;
        this.zzcji = com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza;
        this.zzbnx = com_google_android_gms_internal_zzav;
    }

    private void zzd(int i, String str) {
        if (i == 3 || i == -1) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdh(str);
        } else {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi(str);
        }
        if (this.zzcgg == null) {
            this.zzcgg = new AdResponseParcel(i);
        } else {
            this.zzcgg = new AdResponseParcel(i, this.zzcgg.zzbvq);
        }
        this.zzcjh.zza(new com.google.android.gms.internal.zzko.zza(this.zzbws != null ? this.zzbws : new AdRequestInfoParcel(this.zzcji, null, -1), this.zzcgg, this.zzbwc, null, i, -1, this.zzcgg.zzclf, null));
    }

    public void onStop() {
        synchronized (this.zzcgi) {
            if (this.zzcjj != null) {
                this.zzcjj.cancel();
            }
        }
    }

    zzld zza(VersionInfoParcel versionInfoParcel, zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel) {
        return zzc.zza(this.mContext, versionInfoParcel, com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, this);
    }

    protected AdSizeParcel zzb(AdRequestInfoParcel adRequestInfoParcel) throws zza {
        int i;
        if (this.zzcgg.zzazu) {
            for (AdSizeParcel adSizeParcel : adRequestInfoParcel.zzarm.zzazs) {
                if (adSizeParcel.zzazu) {
                    return new AdSizeParcel(adSizeParcel, adRequestInfoParcel.zzarm.zzazs);
                }
            }
        }
        if (this.zzcgg.zzcle == null) {
            throw new zza("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] split = this.zzcgg.zzcle.split("x");
        if (split.length != 2) {
            String str = "Invalid ad size format from the ad response: ";
            String valueOf = String.valueOf(this.zzcgg.zzcle);
            throw new zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), 0);
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            for (AdSizeParcel adSizeParcel2 : adRequestInfoParcel.zzarm.zzazs) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                i = adSizeParcel2.width == -1 ? (int) (((float) adSizeParcel2.widthPixels) / f) : adSizeParcel2.width;
                int i2 = adSizeParcel2.height == -2 ? (int) (((float) adSizeParcel2.heightPixels) / f) : adSizeParcel2.height;
                if (parseInt == i && parseInt2 == i2 && !adSizeParcel2.zzazu) {
                    return new AdSizeParcel(adSizeParcel2, adRequestInfoParcel.zzarm.zzazs);
                }
            }
            str = "The ad size from the ad response was not one of the requested sizes: ";
            valueOf = String.valueOf(this.zzcgg.zzcle);
            throw new zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), 0);
        } catch (NumberFormatException e) {
            str = "Invalid ad size number from the ad response: ";
            valueOf = String.valueOf(this.zzcgg.zzcle);
            throw new zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), 0);
        }
    }

    public void zzb(@NonNull AdResponseParcel adResponseParcel) {
        com.google.android.gms.ads.internal.util.client.zzb.zzdg("Received ad response.");
        this.zzcgg = adResponseParcel;
        long elapsedRealtime = zzu.zzgs().elapsedRealtime();
        synchronized (this.zzcgi) {
            this.zzcjj = null;
        }
        zzu.zzgq().zzd(this.mContext, this.zzcgg.zzcks);
        try {
            if (this.zzcgg.errorCode == -2 || this.zzcgg.errorCode == -3) {
                JSONObject jSONObject;
                zzta();
                AdSizeParcel zzb = this.zzbws.zzarm.zzazs != null ? zzb(this.zzbws) : null;
                zzu.zzgq().zzaf(this.zzcgg.zzcll);
                zzu.zzgq().zzag(this.zzcgg.zzcly);
                if (!TextUtils.isEmpty(this.zzcgg.zzclj)) {
                    try {
                        jSONObject = new JSONObject(this.zzcgg.zzclj);
                    } catch (Throwable e) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzb("Error parsing the JSON for Active View.", e);
                    }
                    this.zzcjh.zza(new com.google.android.gms.internal.zzko.zza(this.zzbws, this.zzcgg, this.zzbwc, zzb, -2, elapsedRealtime, this.zzcgg.zzclf, jSONObject));
                    zzlb.zzcvl.removeCallbacks(this.zzcgh);
                    return;
                }
                jSONObject = null;
                this.zzcjh.zza(new com.google.android.gms.internal.zzko.zza(this.zzbws, this.zzcgg, this.zzbwc, zzb, -2, elapsedRealtime, this.zzcgg.zzclf, jSONObject));
                zzlb.zzcvl.removeCallbacks(this.zzcgh);
                return;
            }
            throw new zza("There was a problem getting an ad response. ErrorCode: " + this.zzcgg.errorCode, this.zzcgg.errorCode);
        } catch (zza e2) {
            zzd(e2.getErrorCode(), e2.getMessage());
            zzlb.zzcvl.removeCallbacks(this.zzcgh);
        }
    }

    public void zzfp() {
        com.google.android.gms.ads.internal.util.client.zzb.zzdg("AdLoaderBackgroundTask started.");
        this.zzcgh = new C10971(this);
        zzlb.zzcvl.postDelayed(this.zzcgh, ((Long) zzdr.zzbhj.get()).longValue());
        zzlw com_google_android_gms_internal_zzlx = new zzlx();
        long elapsedRealtime = zzu.zzgs().elapsedRealtime();
        zzla.zza(new C10982(this, com_google_android_gms_internal_zzlx));
        this.zzbws = new AdRequestInfoParcel(this.zzcji, this.zzbnx.zzaz().zzb(this.mContext), elapsedRealtime);
        com_google_android_gms_internal_zzlx.zzg(this.zzbws);
    }

    protected void zzta() throws zza {
        if (this.zzcgg.errorCode != -3) {
            if (TextUtils.isEmpty(this.zzcgg.body)) {
                throw new zza("No fill from ad server.", 3);
            }
            zzu.zzgq().zzc(this.mContext, this.zzcgg.zzckc);
            if (this.zzcgg.zzclb) {
                try {
                    this.zzbwc = new zzgq(this.zzcgg.body);
                    zzu.zzgq().zzah(this.zzbwc.zzbvo);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Could not parse mediation config.", e);
                    String str = "Could not parse mediation config: ";
                    String valueOf = String.valueOf(this.zzcgg.body);
                    throw new zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), 0);
                }
            }
            zzu.zzgq().zzah(this.zzcgg.zzbvo);
            if (!TextUtils.isEmpty(this.zzcgg.zzckt) && ((Boolean) zzdr.zzbkn.get()).booleanValue()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdg("Received cookie from server. Setting webview cookie in CookieManager.");
                CookieManager zzal = zzu.zzgo().zzal(this.mContext);
                if (zzal != null) {
                    zzal.setCookie("googleads.g.doubleclick.net", this.zzcgg.zzckt);
                }
            }
        }
    }
}
