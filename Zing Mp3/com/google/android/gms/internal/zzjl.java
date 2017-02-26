package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.ads.AdError;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.request.zzk.zza;
import com.google.android.gms.ads.internal.request.zzl;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzlw.zzc;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public final class zzjl extends zza {
    private static final Object zzaox;
    private static zzjl zzcnk;
    private final Context mContext;
    private final zzjk zzcnl;
    private final zzdk zzcnm;
    private final zzgh zzcnn;

    /* renamed from: com.google.android.gms.internal.zzjl.1 */
    class C14091 implements Callable<Void> {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzjk zzcno;
        final /* synthetic */ AdRequestInfoParcel zzcnp;
        final /* synthetic */ Bundle zzcnq;

        C14091(zzjk com_google_android_gms_internal_zzjk, Context context, AdRequestInfoParcel adRequestInfoParcel, Bundle bundle) {
            this.zzcno = com_google_android_gms_internal_zzjk;
            this.zzang = context;
            this.zzcnp = adRequestInfoParcel;
            this.zzcnq = bundle;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzdo();
        }

        public Void zzdo() throws Exception {
            this.zzcno.zzcna.zza(this.zzang, this.zzcnp.zzcjv.packageName, this.zzcnq);
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjl.2 */
    class C14122 implements Runnable {
        final /* synthetic */ zzdz zzamm;
        final /* synthetic */ zzgh zzanc;
        final /* synthetic */ zzjn zzcnr;
        final /* synthetic */ zzdx zzcns;
        final /* synthetic */ String zzcnt;

        /* renamed from: com.google.android.gms.internal.zzjl.2.1 */
        class C14101 implements zzc<zzgi> {
            final /* synthetic */ zzdx zzcnu;
            final /* synthetic */ C14122 zzcnv;

            C14101(C14122 c14122, zzdx com_google_android_gms_internal_zzdx) {
                this.zzcnv = c14122;
                this.zzcnu = com_google_android_gms_internal_zzdx;
            }

            public void zzb(zzgi com_google_android_gms_internal_zzgi) {
                this.zzcnv.zzamm.zza(this.zzcnu, "jsf");
                this.zzcnv.zzamm.zzma();
                com_google_android_gms_internal_zzgi.zza("/invalidRequest", this.zzcnv.zzcnr.zzcoc);
                com_google_android_gms_internal_zzgi.zza("/loadAdURL", this.zzcnv.zzcnr.zzcod);
                com_google_android_gms_internal_zzgi.zza("/loadAd", this.zzcnv.zzcnr.zzcoe);
                try {
                    com_google_android_gms_internal_zzgi.zzi("AFMA_getAd", this.zzcnv.zzcnt);
                } catch (Throwable e) {
                    zzb.zzb("Error requesting an ad url", e);
                }
            }

            public /* synthetic */ void zzd(Object obj) {
                zzb((zzgi) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzjl.2.2 */
        class C14112 implements zzlw.zza {
            final /* synthetic */ C14122 zzcnv;

            C14112(C14122 c14122) {
                this.zzcnv = c14122;
            }

            public void run() {
            }
        }

        C14122(zzgh com_google_android_gms_internal_zzgh, zzjn com_google_android_gms_internal_zzjn, zzdz com_google_android_gms_internal_zzdz, zzdx com_google_android_gms_internal_zzdx, String str) {
            this.zzanc = com_google_android_gms_internal_zzgh;
            this.zzcnr = com_google_android_gms_internal_zzjn;
            this.zzamm = com_google_android_gms_internal_zzdz;
            this.zzcns = com_google_android_gms_internal_zzdx;
            this.zzcnt = str;
        }

        public void run() {
            zzgh.zzc zzny = this.zzanc.zzny();
            this.zzcnr.zzb(zzny);
            this.zzamm.zza(this.zzcns, "rwc");
            zzny.zza(new C14101(this, this.zzamm.zzlz()), new C14112(this));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjl.3 */
    class C14133 implements Runnable {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzjk zzcno;
        final /* synthetic */ AdRequestInfoParcel zzcnp;
        final /* synthetic */ zzjn zzcnr;

        C14133(zzjk com_google_android_gms_internal_zzjk, Context context, zzjn com_google_android_gms_internal_zzjn, AdRequestInfoParcel adRequestInfoParcel) {
            this.zzcno = com_google_android_gms_internal_zzjk;
            this.zzang = context;
            this.zzcnr = com_google_android_gms_internal_zzjn;
            this.zzcnp = adRequestInfoParcel;
        }

        public void run() {
            this.zzcno.zzcne.zza(this.zzang, this.zzcnr, this.zzcnp.zzari);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjl.4 */
    class C14144 implements zzlg<zzge> {
        final /* synthetic */ zzjl zzcnw;

        C14144(zzjl com_google_android_gms_internal_zzjl) {
            this.zzcnw = com_google_android_gms_internal_zzjl;
        }

        public void zza(zzge com_google_android_gms_internal_zzge) {
            com_google_android_gms_internal_zzge.zza("/log", zzfd.zzbpr);
        }

        public /* synthetic */ void zzd(Object obj) {
            zza((zzge) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjl.5 */
    class C14155 implements Runnable {
        final /* synthetic */ AdRequestInfoParcel zzcnp;
        final /* synthetic */ zzjl zzcnw;
        final /* synthetic */ zzl zzcnx;

        C14155(zzjl com_google_android_gms_internal_zzjl, AdRequestInfoParcel adRequestInfoParcel, zzl com_google_android_gms_ads_internal_request_zzl) {
            this.zzcnw = com_google_android_gms_internal_zzjl;
            this.zzcnp = adRequestInfoParcel;
            this.zzcnx = com_google_android_gms_ads_internal_request_zzl;
        }

        public void run() {
            AdResponseParcel zzd;
            try {
                zzd = this.zzcnw.zzd(this.zzcnp);
            } catch (Throwable e) {
                zzu.zzgq().zza(e, "AdRequestServiceImpl.loadAdAsync");
                zzb.zzc("Could not fetch ad response due to an Exception.", e);
                zzd = null;
            }
            if (zzd == null) {
                zzd = new AdResponseParcel(0);
            }
            try {
                this.zzcnx.zzb(zzd);
            } catch (Throwable e2) {
                zzb.zzc("Fail to forward ad response.", e2);
            }
        }
    }

    static {
        zzaox = new Object();
    }

    zzjl(Context context, zzdk com_google_android_gms_internal_zzdk, zzjk com_google_android_gms_internal_zzjk) {
        this.mContext = context;
        this.zzcnl = com_google_android_gms_internal_zzjk;
        this.zzcnm = com_google_android_gms_internal_zzdk;
        this.zzcnn = new zzgh(context.getApplicationContext() != null ? context.getApplicationContext() : context, VersionInfoParcel.zzwr(), com_google_android_gms_internal_zzdk.zzlo(), new C14144(this), new zzgh.zzb());
    }

    private static AdResponseParcel zza(Context context, zzgh com_google_android_gms_internal_zzgh, zzdk com_google_android_gms_internal_zzdk, zzjk com_google_android_gms_internal_zzjk, AdRequestInfoParcel adRequestInfoParcel) {
        Bundle bundle;
        Future future;
        Throwable e;
        zzb.zzdg("Starting ad request from service using: AFMA_getAd");
        zzdr.initialize(context);
        zzlt zzrl = com_google_android_gms_internal_zzjk.zzcni.zzrl();
        zzdz com_google_android_gms_internal_zzdz = new zzdz(((Boolean) zzdr.zzbeq.get()).booleanValue(), "load_ad", adRequestInfoParcel.zzarm.zzazq);
        if (adRequestInfoParcel.versionCode > 10 && adRequestInfoParcel.zzckl != -1) {
            com_google_android_gms_internal_zzdz.zza(com_google_android_gms_internal_zzdz.zzc(adRequestInfoParcel.zzckl), "cts");
        }
        zzdx zzlz = com_google_android_gms_internal_zzdz.zzlz();
        Bundle bundle2 = (adRequestInfoParcel.versionCode < 4 || adRequestInfoParcel.zzckb == null) ? null : adRequestInfoParcel.zzckb;
        if (!((Boolean) zzdr.zzbfh.get()).booleanValue() || com_google_android_gms_internal_zzjk.zzcna == null) {
            bundle = bundle2;
            future = null;
        } else {
            if (bundle2 == null && ((Boolean) zzdr.zzbfi.get()).booleanValue()) {
                zzkx.m1697v("contentInfo is not present, but we'll still launch the app index task");
                bundle2 = new Bundle();
            }
            if (bundle2 != null) {
                bundle = bundle2;
                future = zzla.zza(new C14091(com_google_android_gms_internal_zzjk, context, adRequestInfoParcel, bundle2));
            } else {
                bundle = bundle2;
                future = null;
            }
        }
        zzlr com_google_android_gms_internal_zzlr = new zzlr(null);
        Bundle bundle3 = adRequestInfoParcel.zzcju.extras;
        Object obj = (bundle3 == null || bundle3.getString("_ad") == null) ? null : 1;
        if (adRequestInfoParcel.zzcks && obj == null) {
            zzlt zza = com_google_android_gms_internal_zzjk.zzcnf.zza(adRequestInfoParcel.applicationInfo);
        } else {
            Object obj2 = com_google_android_gms_internal_zzlr;
        }
        zzjr zzv = zzu.zzgv().zzv(context);
        if (zzv.zzcqe == -1) {
            zzb.zzdg("Device is offline.");
            return new AdResponseParcel(2);
        }
        String string;
        String uuid = adRequestInfoParcel.versionCode >= 7 ? adRequestInfoParcel.zzcki : UUID.randomUUID().toString();
        zzjn com_google_android_gms_internal_zzjn = new zzjn(uuid, adRequestInfoParcel.applicationInfo.packageName);
        if (adRequestInfoParcel.zzcju.extras != null) {
            string = adRequestInfoParcel.zzcju.extras.getString("_ad");
            if (string != null) {
                return zzjm.zza(context, adRequestInfoParcel, string);
            }
        }
        List zza2 = com_google_android_gms_internal_zzjk.zzcnd.zza(adRequestInfoParcel);
        String zzg = com_google_android_gms_internal_zzjk.zzcnj.zzg(adRequestInfoParcel);
        zzjv com_google_android_gms_internal_zzjv = com_google_android_gms_internal_zzjk.zzcnh;
        if (future != null) {
            try {
                zzkx.m1697v("Waiting for app index fetching task.");
                future.get(((Long) zzdr.zzbfj.get()).longValue(), TimeUnit.MILLISECONDS);
                zzkx.m1697v("App index fetching task completed.");
            } catch (ExecutionException e2) {
                e = e2;
                zzb.zzc("Failed to fetch app index signal", e);
            } catch (InterruptedException e3) {
                e = e3;
                zzb.zzc("Failed to fetch app index signal", e);
            } catch (TimeoutException e4) {
                zzb.zzdg("Timed out waiting for app index fetching task");
            }
        }
        zzkk com_google_android_gms_internal_zzkk = com_google_android_gms_internal_zzjk.zzcnc;
        string = adRequestInfoParcel.zzcjv.packageName;
        zzd(zzrl);
        JSONObject zza3 = zzjm.zza(context, new zzjj().zzf(adRequestInfoParcel).zza(zzv).zza(null).zzc(zzc(zza)).zze(zzd(zzrl)).zzcm(zzg).zzk(zza2).zzf(bundle).zzcn(null).zzi(com_google_android_gms_internal_zzjk.zzcnb.zzi(context)));
        if (zza3 == null) {
            return new AdResponseParcel(0);
        }
        if (adRequestInfoParcel.versionCode < 7) {
            try {
                zza3.put("request_id", uuid);
            } catch (JSONException e5) {
            }
        }
        try {
            zza3.put("prefetch_mode", NativeProtocol.WEB_DIALOG_URL);
        } catch (Throwable e6) {
            zzb.zzc("Failed putting prefetch parameters to ad request.", e6);
        }
        String jSONObject = zza3.toString();
        com_google_android_gms_internal_zzdz.zza(zzlz, "arc");
        zzlb.zzcvl.post(new C14122(com_google_android_gms_internal_zzgh, com_google_android_gms_internal_zzjn, com_google_android_gms_internal_zzdz, com_google_android_gms_internal_zzdz.zzlz(), jSONObject));
        AdResponseParcel adResponseParcel;
        try {
            zzjq com_google_android_gms_internal_zzjq = (zzjq) com_google_android_gms_internal_zzjn.zztk().get(10, TimeUnit.SECONDS);
            if (com_google_android_gms_internal_zzjq == null) {
                adResponseParcel = new AdResponseParcel(0);
                return adResponseParcel;
            } else if (com_google_android_gms_internal_zzjq.getErrorCode() != -2) {
                adResponseParcel = new AdResponseParcel(com_google_android_gms_internal_zzjq.getErrorCode());
                zzlb.zzcvl.post(new C14133(com_google_android_gms_internal_zzjk, context, com_google_android_gms_internal_zzjn, adRequestInfoParcel));
                return adResponseParcel;
            } else {
                if (com_google_android_gms_internal_zzdz.zzmd() != null) {
                    com_google_android_gms_internal_zzdz.zza(com_google_android_gms_internal_zzdz.zzmd(), "rur");
                }
                adResponseParcel = null;
                if (!TextUtils.isEmpty(com_google_android_gms_internal_zzjq.zztq())) {
                    adResponseParcel = zzjm.zza(context, adRequestInfoParcel, com_google_android_gms_internal_zzjq.zztq());
                }
                if (adResponseParcel == null && !TextUtils.isEmpty(com_google_android_gms_internal_zzjq.getUrl())) {
                    adResponseParcel = zza(adRequestInfoParcel, context, adRequestInfoParcel.zzari.zzda, com_google_android_gms_internal_zzjq.getUrl(), null, com_google_android_gms_internal_zzjq, com_google_android_gms_internal_zzdz, com_google_android_gms_internal_zzjk);
                }
                if (adResponseParcel == null) {
                    adResponseParcel = new AdResponseParcel(0);
                }
                com_google_android_gms_internal_zzdz.zza(zzlz, "tts");
                adResponseParcel.zzclo = com_google_android_gms_internal_zzdz.zzmb();
                zzlb.zzcvl.post(new C14133(com_google_android_gms_internal_zzjk, context, com_google_android_gms_internal_zzjn, adRequestInfoParcel));
                return adResponseParcel;
            }
        } catch (Exception e7) {
            adResponseParcel = new AdResponseParcel(0);
            return adResponseParcel;
        } finally {
            zzlb.zzcvl.post(new C14133(com_google_android_gms_internal_zzjk, context, com_google_android_gms_internal_zzjn, adRequestInfoParcel));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.ads.internal.request.AdResponseParcel zza(com.google.android.gms.ads.internal.request.AdRequestInfoParcel r13, android.content.Context r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, com.google.android.gms.internal.zzjq r18, com.google.android.gms.internal.zzdz r19, com.google.android.gms.internal.zzjk r20) {
        /*
        if (r19 == 0) goto L_0x00ed;
    L_0x0002:
        r2 = r19.zzlz();
        r3 = r2;
    L_0x0007:
        r8 = new com.google.android.gms.internal.zzjo;	 Catch:{ IOException -> 0x00f8 }
        r2 = r18.zztn();	 Catch:{ IOException -> 0x00f8 }
        r8.<init>(r13, r2);	 Catch:{ IOException -> 0x00f8 }
        r4 = "AdRequestServiceImpl: Sending request: ";
        r2 = java.lang.String.valueOf(r16);	 Catch:{ IOException -> 0x00f8 }
        r5 = r2.length();	 Catch:{ IOException -> 0x00f8 }
        if (r5 == 0) goto L_0x00f1;
    L_0x001c:
        r2 = r4.concat(r2);	 Catch:{ IOException -> 0x00f8 }
    L_0x0020:
        com.google.android.gms.ads.internal.util.client.zzb.zzdg(r2);	 Catch:{ IOException -> 0x00f8 }
        r4 = new java.net.URL;	 Catch:{ IOException -> 0x00f8 }
        r0 = r16;
        r4.<init>(r0);	 Catch:{ IOException -> 0x00f8 }
        r2 = 0;
        r5 = com.google.android.gms.ads.internal.zzu.zzgs();	 Catch:{ IOException -> 0x00f8 }
        r10 = r5.elapsedRealtime();	 Catch:{ IOException -> 0x00f8 }
        r6 = r2;
        r7 = r4;
    L_0x0035:
        if (r20 == 0) goto L_0x003e;
    L_0x0037:
        r0 = r20;
        r2 = r0.zzcng;	 Catch:{ IOException -> 0x00f8 }
        r2.zzts();	 Catch:{ IOException -> 0x00f8 }
    L_0x003e:
        r2 = r7.openConnection();	 Catch:{ IOException -> 0x00f8 }
        r2 = (java.net.HttpURLConnection) r2;	 Catch:{ IOException -> 0x00f8 }
        r4 = com.google.android.gms.ads.internal.zzu.zzgm();	 Catch:{ all -> 0x011d }
        r5 = 0;
        r4.zza(r14, r15, r5, r2);	 Catch:{ all -> 0x011d }
        r4 = android.text.TextUtils.isEmpty(r17);	 Catch:{ all -> 0x011d }
        if (r4 != 0) goto L_0x005f;
    L_0x0052:
        r4 = r18.zztp();	 Catch:{ all -> 0x011d }
        if (r4 == 0) goto L_0x005f;
    L_0x0058:
        r4 = "x-afma-drt-cookie";
        r0 = r17;
        r2.addRequestProperty(r4, r0);	 Catch:{ all -> 0x011d }
    L_0x005f:
        r4 = r13.zzckt;	 Catch:{ all -> 0x011d }
        r5 = android.text.TextUtils.isEmpty(r4);	 Catch:{ all -> 0x011d }
        if (r5 != 0) goto L_0x0071;
    L_0x0067:
        r5 = "Sending webview cookie in ad request header.";
        com.google.android.gms.ads.internal.util.client.zzb.zzdg(r5);	 Catch:{ all -> 0x011d }
        r5 = "Cookie";
        r2.addRequestProperty(r5, r4);	 Catch:{ all -> 0x011d }
    L_0x0071:
        if (r18 == 0) goto L_0x009d;
    L_0x0073:
        r4 = r18.zzto();	 Catch:{ all -> 0x011d }
        r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ all -> 0x011d }
        if (r4 != 0) goto L_0x009d;
    L_0x007d:
        r4 = 1;
        r2.setDoOutput(r4);	 Catch:{ all -> 0x011d }
        r4 = r18.zzto();	 Catch:{ all -> 0x011d }
        r9 = r4.getBytes();	 Catch:{ all -> 0x011d }
        r4 = r9.length;	 Catch:{ all -> 0x011d }
        r2.setFixedLengthStreamingMode(r4);	 Catch:{ all -> 0x011d }
        r5 = 0;
        r4 = new java.io.BufferedOutputStream;	 Catch:{ all -> 0x0117 }
        r12 = r2.getOutputStream();	 Catch:{ all -> 0x0117 }
        r4.<init>(r12);	 Catch:{ all -> 0x0117 }
        r4.write(r9);	 Catch:{ all -> 0x01d4 }
        com.google.android.gms.common.util.zzo.zzb(r4);	 Catch:{ all -> 0x011d }
    L_0x009d:
        r9 = r2.getResponseCode();	 Catch:{ all -> 0x011d }
        r12 = r2.getHeaderFields();	 Catch:{ all -> 0x011d }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r9 < r4) goto L_0x0131;
    L_0x00a9:
        r4 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r9 >= r4) goto L_0x0131;
    L_0x00ad:
        r6 = r7.toString();	 Catch:{ all -> 0x011d }
        r5 = 0;
        r4 = new java.io.InputStreamReader;	 Catch:{ all -> 0x012b }
        r7 = r2.getInputStream();	 Catch:{ all -> 0x012b }
        r4.<init>(r7);	 Catch:{ all -> 0x012b }
        r5 = com.google.android.gms.ads.internal.zzu.zzgm();	 Catch:{ all -> 0x01d1 }
        r5 = r5.zza(r4);	 Catch:{ all -> 0x01d1 }
        com.google.android.gms.common.util.zzo.zzb(r4);	 Catch:{ all -> 0x011d }
        zza(r6, r12, r5, r9);	 Catch:{ all -> 0x011d }
        r8.zzb(r6, r12, r5);	 Catch:{ all -> 0x011d }
        if (r19 == 0) goto L_0x00db;
    L_0x00ce:
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ all -> 0x011d }
        r5 = 0;
        r6 = "ufe";
        r4[r5] = r6;	 Catch:{ all -> 0x011d }
        r0 = r19;
        r0.zza(r3, r4);	 Catch:{ all -> 0x011d }
    L_0x00db:
        r3 = r8.zzj(r10);	 Catch:{ all -> 0x011d }
        r2.disconnect();	 Catch:{ IOException -> 0x00f8 }
        if (r20 == 0) goto L_0x00eb;
    L_0x00e4:
        r0 = r20;
        r2 = r0.zzcng;	 Catch:{ IOException -> 0x00f8 }
        r2.zztt();	 Catch:{ IOException -> 0x00f8 }
    L_0x00eb:
        r2 = r3;
    L_0x00ec:
        return r2;
    L_0x00ed:
        r2 = 0;
        r3 = r2;
        goto L_0x0007;
    L_0x00f1:
        r2 = new java.lang.String;	 Catch:{ IOException -> 0x00f8 }
        r2.<init>(r4);	 Catch:{ IOException -> 0x00f8 }
        goto L_0x0020;
    L_0x00f8:
        r2 = move-exception;
        r3 = "Error while connecting to ad server: ";
        r2 = r2.getMessage();
        r2 = java.lang.String.valueOf(r2);
        r4 = r2.length();
        if (r4 == 0) goto L_0x01ca;
    L_0x0109:
        r2 = r3.concat(r2);
    L_0x010d:
        com.google.android.gms.ads.internal.util.client.zzb.zzdi(r2);
        r2 = new com.google.android.gms.ads.internal.request.AdResponseParcel;
        r3 = 2;
        r2.<init>(r3);
        goto L_0x00ec;
    L_0x0117:
        r3 = move-exception;
        r4 = r5;
    L_0x0119:
        com.google.android.gms.common.util.zzo.zzb(r4);	 Catch:{ all -> 0x011d }
        throw r3;	 Catch:{ all -> 0x011d }
    L_0x011d:
        r3 = move-exception;
        r2.disconnect();	 Catch:{ IOException -> 0x00f8 }
        if (r20 == 0) goto L_0x012a;
    L_0x0123:
        r0 = r20;
        r2 = r0.zzcng;	 Catch:{ IOException -> 0x00f8 }
        r2.zztt();	 Catch:{ IOException -> 0x00f8 }
    L_0x012a:
        throw r3;	 Catch:{ IOException -> 0x00f8 }
    L_0x012b:
        r3 = move-exception;
        r4 = r5;
    L_0x012d:
        com.google.android.gms.common.util.zzo.zzb(r4);	 Catch:{ all -> 0x011d }
        throw r3;	 Catch:{ all -> 0x011d }
    L_0x0131:
        r4 = r7.toString();	 Catch:{ all -> 0x011d }
        r5 = 0;
        zza(r4, r12, r5, r9);	 Catch:{ all -> 0x011d }
        r4 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r9 < r4) goto L_0x018a;
    L_0x013d:
        r4 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r9 >= r4) goto L_0x018a;
    L_0x0141:
        r4 = "Location";
        r4 = r2.getHeaderField(r4);	 Catch:{ all -> 0x011d }
        r5 = android.text.TextUtils.isEmpty(r4);	 Catch:{ all -> 0x011d }
        if (r5 == 0) goto L_0x0166;
    L_0x014d:
        r3 = "No location header to follow redirect.";
        com.google.android.gms.ads.internal.util.client.zzb.zzdi(r3);	 Catch:{ all -> 0x011d }
        r3 = new com.google.android.gms.ads.internal.request.AdResponseParcel;	 Catch:{ all -> 0x011d }
        r4 = 0;
        r3.<init>(r4);	 Catch:{ all -> 0x011d }
        r2.disconnect();	 Catch:{ IOException -> 0x00f8 }
        if (r20 == 0) goto L_0x0164;
    L_0x015d:
        r0 = r20;
        r2 = r0.zzcng;	 Catch:{ IOException -> 0x00f8 }
        r2.zztt();	 Catch:{ IOException -> 0x00f8 }
    L_0x0164:
        r2 = r3;
        goto L_0x00ec;
    L_0x0166:
        r5 = new java.net.URL;	 Catch:{ all -> 0x011d }
        r5.<init>(r4);	 Catch:{ all -> 0x011d }
        r4 = r6 + 1;
        r6 = 5;
        if (r4 <= r6) goto L_0x01b7;
    L_0x0170:
        r3 = "Too many redirects.";
        com.google.android.gms.ads.internal.util.client.zzb.zzdi(r3);	 Catch:{ all -> 0x011d }
        r3 = new com.google.android.gms.ads.internal.request.AdResponseParcel;	 Catch:{ all -> 0x011d }
        r4 = 0;
        r3.<init>(r4);	 Catch:{ all -> 0x011d }
        r2.disconnect();	 Catch:{ IOException -> 0x00f8 }
        if (r20 == 0) goto L_0x0187;
    L_0x0180:
        r0 = r20;
        r2 = r0.zzcng;	 Catch:{ IOException -> 0x00f8 }
        r2.zztt();	 Catch:{ IOException -> 0x00f8 }
    L_0x0187:
        r2 = r3;
        goto L_0x00ec;
    L_0x018a:
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011d }
        r4 = 46;
        r3.<init>(r4);	 Catch:{ all -> 0x011d }
        r4 = "Received error HTTP response code: ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x011d }
        r3 = r3.append(r9);	 Catch:{ all -> 0x011d }
        r3 = r3.toString();	 Catch:{ all -> 0x011d }
        com.google.android.gms.ads.internal.util.client.zzb.zzdi(r3);	 Catch:{ all -> 0x011d }
        r3 = new com.google.android.gms.ads.internal.request.AdResponseParcel;	 Catch:{ all -> 0x011d }
        r4 = 0;
        r3.<init>(r4);	 Catch:{ all -> 0x011d }
        r2.disconnect();	 Catch:{ IOException -> 0x00f8 }
        if (r20 == 0) goto L_0x01b4;
    L_0x01ad:
        r0 = r20;
        r2 = r0.zzcng;	 Catch:{ IOException -> 0x00f8 }
        r2.zztt();	 Catch:{ IOException -> 0x00f8 }
    L_0x01b4:
        r2 = r3;
        goto L_0x00ec;
    L_0x01b7:
        r8.zzk(r12);	 Catch:{ all -> 0x011d }
        r2.disconnect();	 Catch:{ IOException -> 0x00f8 }
        if (r20 == 0) goto L_0x01c6;
    L_0x01bf:
        r0 = r20;
        r2 = r0.zzcng;	 Catch:{ IOException -> 0x00f8 }
        r2.zztt();	 Catch:{ IOException -> 0x00f8 }
    L_0x01c6:
        r6 = r4;
        r7 = r5;
        goto L_0x0035;
    L_0x01ca:
        r2 = new java.lang.String;
        r2.<init>(r3);
        goto L_0x010d;
    L_0x01d1:
        r3 = move-exception;
        goto L_0x012d;
    L_0x01d4:
        r3 = move-exception;
        goto L_0x0119;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzjl.zza(com.google.android.gms.ads.internal.request.AdRequestInfoParcel, android.content.Context, java.lang.String, java.lang.String, java.lang.String, com.google.android.gms.internal.zzjq, com.google.android.gms.internal.zzdz, com.google.android.gms.internal.zzjk):com.google.android.gms.ads.internal.request.AdResponseParcel");
    }

    public static zzjl zza(Context context, zzdk com_google_android_gms_internal_zzdk, zzjk com_google_android_gms_internal_zzjk) {
        zzjl com_google_android_gms_internal_zzjl;
        synchronized (zzaox) {
            if (zzcnk == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                zzcnk = new zzjl(context, com_google_android_gms_internal_zzdk, com_google_android_gms_internal_zzjk);
            }
            com_google_android_gms_internal_zzjl = zzcnk;
        }
        return com_google_android_gms_internal_zzjl;
    }

    private static void zza(String str, Map<String, List<String>> map, String str2, int i) {
        if (zzb.zzbi(2)) {
            zzkx.m1697v(new StringBuilder(String.valueOf(str).length() + 39).append("Http Response: {\n  URL:\n    ").append(str).append("\n  Headers:").toString());
            if (map != null) {
                for (String str3 : map.keySet()) {
                    String str32;
                    zzkx.m1697v(new StringBuilder(String.valueOf(str32).length() + 5).append("    ").append(str32).append(":").toString());
                    for (String str322 : (List) map.get(str322)) {
                        String str4 = "      ";
                        str322 = String.valueOf(str322);
                        zzkx.m1697v(str322.length() != 0 ? str4.concat(str322) : new String(str4));
                    }
                }
            }
            zzkx.m1697v("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += AdError.NETWORK_ERROR_CODE) {
                    zzkx.m1697v(str2.substring(i2, Math.min(str2.length(), i2 + AdError.NETWORK_ERROR_CODE)));
                }
            } else {
                zzkx.m1697v("    null");
            }
            zzkx.m1697v("  Response Code:\n    " + i + "\n}");
        }
    }

    private static Location zzc(zzlt<Location> com_google_android_gms_internal_zzlt_android_location_Location) {
        try {
            return (Location) com_google_android_gms_internal_zzlt_android_location_Location.get(((Long) zzdr.zzbjr.get()).longValue(), TimeUnit.MILLISECONDS);
        } catch (Throwable e) {
            zzb.zzc("Exception caught while getting location", e);
            return null;
        }
    }

    private static Bundle zzd(zzlt<Bundle> com_google_android_gms_internal_zzlt_android_os_Bundle) {
        Bundle bundle = new Bundle();
        try {
            return (Bundle) com_google_android_gms_internal_zzlt_android_os_Bundle.get(((Long) zzdr.zzbkj.get()).longValue(), TimeUnit.MILLISECONDS);
        } catch (Throwable e) {
            zzb.zzc("Exception caught while getting parental controls.", e);
            return bundle;
        }
    }

    public void zza(AdRequestInfoParcel adRequestInfoParcel, zzl com_google_android_gms_ads_internal_request_zzl) {
        zzu.zzgq().zzc(this.mContext, adRequestInfoParcel.zzari);
        zzla.zza(new C14155(this, adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzl));
    }

    public AdResponseParcel zzd(AdRequestInfoParcel adRequestInfoParcel) {
        return zza(this.mContext, this.zzcnn, this.zzcnm, this.zzcnl, adRequestInfoParcel);
    }
}
