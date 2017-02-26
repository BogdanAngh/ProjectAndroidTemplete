package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.SystemClock;
import android.text.TextUtils;
import com.badlogic.gdx.net.HttpStatus;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.request.zzi.zza;
import com.google.android.gms.ads.internal.request.zzj;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.internal.zzdt.zzd;
import com.google.android.gms.internal.zzhx.zzc;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
public final class zzgf extends zza {
    private static zzgf zzDv;
    private static final Object zzoW;
    private final Context mContext;
    private final zzge zzDw;
    private final zzbr zzDx;
    private final zzdt zzqC;

    /* renamed from: com.google.android.gms.internal.zzgf.1 */
    static class C02351 implements Runnable {
        final /* synthetic */ zzce zzDA;
        final /* synthetic */ zzcd zzDB;
        final /* synthetic */ String zzDC;
        final /* synthetic */ zzdt zzDy;
        final /* synthetic */ zzgh zzDz;

        /* renamed from: com.google.android.gms.internal.zzgf.1.1 */
        class C04681 implements zzc<zzbe> {
            final /* synthetic */ zzcd zzDD;
            final /* synthetic */ C02351 zzDE;

            C04681(C02351 c02351, zzcd com_google_android_gms_internal_zzcd) {
                this.zzDE = c02351;
                this.zzDD = com_google_android_gms_internal_zzcd;
            }

            public void zzb(zzbe com_google_android_gms_internal_zzbe) {
                this.zzDE.zzDA.zza(this.zzDD, "jsf");
                this.zzDE.zzDA.zzdp();
                com_google_android_gms_internal_zzbe.zza("/invalidRequest", this.zzDE.zzDz.zzDO);
                com_google_android_gms_internal_zzbe.zza("/loadAdURL", this.zzDE.zzDz.zzDP);
                try {
                    com_google_android_gms_internal_zzbe.zza("AFMA_buildAdURL", this.zzDE.zzDC);
                } catch (Throwable e) {
                    zzb.zzb("Error requesting an ad url", e);
                }
            }

            public /* synthetic */ void zzc(Object obj) {
                zzb((zzbe) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzgf.1.2 */
        class C04692 implements zzhx.zza {
            final /* synthetic */ C02351 zzDE;

            C04692(C02351 c02351) {
                this.zzDE = c02351;
            }

            public void run() {
            }
        }

        C02351(zzdt com_google_android_gms_internal_zzdt, zzgh com_google_android_gms_internal_zzgh, zzce com_google_android_gms_internal_zzce, zzcd com_google_android_gms_internal_zzcd, String str) {
            this.zzDy = com_google_android_gms_internal_zzdt;
            this.zzDz = com_google_android_gms_internal_zzgh;
            this.zzDA = com_google_android_gms_internal_zzce;
            this.zzDB = com_google_android_gms_internal_zzcd;
            this.zzDC = str;
        }

        public void run() {
            zzd zzdU = this.zzDy.zzdU();
            this.zzDz.zzb(zzdU);
            this.zzDA.zza(this.zzDB, "rwc");
            zzdU.zza(new C04681(this, this.zzDA.zzdo()), new C04692(this));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.2 */
    static class C02362 implements Runnable {
        final /* synthetic */ zzce zzDA;
        final /* synthetic */ zzcd zzDB;
        final /* synthetic */ String zzDC;
        final /* synthetic */ AdRequestInfoParcel zzDF;
        final /* synthetic */ zzbr zzDG;
        final /* synthetic */ zzgh zzDz;
        final /* synthetic */ Context zzqV;

        C02362(Context context, AdRequestInfoParcel adRequestInfoParcel, zzgh com_google_android_gms_internal_zzgh, zzce com_google_android_gms_internal_zzce, zzcd com_google_android_gms_internal_zzcd, String str, zzbr com_google_android_gms_internal_zzbr) {
            this.zzqV = context;
            this.zzDF = adRequestInfoParcel;
            this.zzDz = com_google_android_gms_internal_zzgh;
            this.zzDA = com_google_android_gms_internal_zzce;
            this.zzDB = com_google_android_gms_internal_zzcd;
            this.zzDC = str;
            this.zzDG = com_google_android_gms_internal_zzbr;
        }

        public void run() {
            zzid zza = zzo.zzbw().zza(this.zzqV, new AdSizeParcel(), false, false, null, this.zzDF.zzpJ);
            if (zzo.zzby().zzge()) {
                zza.getWebView().clearCache(true);
            }
            zza.setWillNotDraw(true);
            this.zzDz.zze(zza);
            this.zzDA.zza(this.zzDB, "rwc");
            zzie.zza zzb = zzgf.zza(this.zzDC, this.zzDA, this.zzDA.zzdo());
            zzie zzgF = zza.zzgF();
            zzgF.zza("/invalidRequest", this.zzDz.zzDO);
            zzgF.zza("/loadAdURL", this.zzDz.zzDP);
            zzgF.zza("/log", zzdf.zzwc);
            zzgF.zza(zzb);
            zzb.zzay("Loading the JS library.");
            zza.loadUrl(this.zzDG.zzcW());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.3 */
    static class C02373 implements Runnable {
        final /* synthetic */ zzgh zzDz;

        C02373(zzgh com_google_android_gms_internal_zzgh) {
            this.zzDz = com_google_android_gms_internal_zzgh;
        }

        public void run() {
            this.zzDz.zzfF();
            if (this.zzDz.zzfD() != null) {
                this.zzDz.zzfD().release();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.4 */
    static class C04704 implements zzie.zza {
        final /* synthetic */ zzce zzDA;
        final /* synthetic */ String zzDC;
        final /* synthetic */ zzcd zzDD;

        C04704(zzce com_google_android_gms_internal_zzce, zzcd com_google_android_gms_internal_zzcd, String str) {
            this.zzDA = com_google_android_gms_internal_zzce;
            this.zzDD = com_google_android_gms_internal_zzcd;
            this.zzDC = str;
        }

        public void zza(zzid com_google_android_gms_internal_zzid, boolean z) {
            this.zzDA.zza(this.zzDD, "jsf");
            this.zzDA.zzdp();
            com_google_android_gms_internal_zzid.zza("AFMA_buildAdURL", this.zzDC);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.5 */
    class C04715 implements zzdt.zzb<zzbb> {
        final /* synthetic */ zzgf zzDH;

        C04715(zzgf com_google_android_gms_internal_zzgf) {
            this.zzDH = com_google_android_gms_internal_zzgf;
        }

        public void zza(zzbb com_google_android_gms_internal_zzbb) {
            com_google_android_gms_internal_zzbb.zza("/log", zzdf.zzwc);
        }

        public /* synthetic */ void zzc(Object obj) {
            zza((zzbb) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgf.6 */
    class C04726 extends zzhh {
        final /* synthetic */ AdRequestInfoParcel zzDF;
        final /* synthetic */ zzgf zzDH;
        final /* synthetic */ zzj zzDI;

        C04726(zzgf com_google_android_gms_internal_zzgf, AdRequestInfoParcel adRequestInfoParcel, zzj com_google_android_gms_ads_internal_request_zzj) {
            this.zzDH = com_google_android_gms_internal_zzgf;
            this.zzDF = adRequestInfoParcel;
            this.zzDI = com_google_android_gms_ads_internal_request_zzj;
        }

        public void onStop() {
            try {
                this.zzDI.zzb(new AdResponseParcel(-1));
            } catch (Throwable e) {
                zzb.zzd("Fail to forward ad response.", e);
            }
        }

        public void zzdP() {
            AdResponseParcel zze;
            try {
                zze = this.zzDH.zze(this.zzDF);
            } catch (Throwable e) {
                zzo.zzby().zzc(e, true);
                zzb.zzd("Could not fetch ad response due to an Exception.", e);
                zze = null;
            }
            if (zze == null) {
                zze = new AdResponseParcel(0);
            }
            try {
                this.zzDI.zzb(zze);
            } catch (Throwable e2) {
                zzb.zzd("Fail to forward ad response.", e2);
            }
        }
    }

    static {
        zzoW = new Object();
    }

    zzgf(Context context, zzbr com_google_android_gms_internal_zzbr, zzge com_google_android_gms_internal_zzge) {
        this.mContext = context;
        this.zzDw = com_google_android_gms_internal_zzge;
        this.zzDx = com_google_android_gms_internal_zzbr;
        this.zzqC = new zzdt(context.getApplicationContext() != null ? context.getApplicationContext() : context, new VersionInfoParcel(7571000, 7571000, true), com_google_android_gms_internal_zzbr.zzcW(), new C04715(this), new zzdt.zzc());
    }

    private static AdResponseParcel zza(Context context, zzdt com_google_android_gms_internal_zzdt, zzbr com_google_android_gms_internal_zzbr, zzge com_google_android_gms_internal_zzge, AdRequestInfoParcel adRequestInfoParcel) {
        zzb.zzay("Starting ad request from service.");
        zzbz.zzw(context);
        zzce com_google_android_gms_internal_zzce = new zzce("load_ad");
        zzcd zzdo = com_google_android_gms_internal_zzce.zzdo();
        com_google_android_gms_internal_zzge.zzDs.init();
        zzgk zzC = zzo.zzbB().zzC(context);
        if (zzC.zzEy == -1) {
            zzb.zzay("Device is offline.");
            return new AdResponseParcel(2);
        }
        String uuid = adRequestInfoParcel.versionCode >= 7 ? adRequestInfoParcel.zzCE : UUID.randomUUID().toString();
        zzgh com_google_android_gms_internal_zzgh = new zzgh(uuid, adRequestInfoParcel.applicationInfo.packageName);
        if (adRequestInfoParcel.zzCm.extras != null) {
            String string = adRequestInfoParcel.zzCm.extras.getString("_ad");
            if (string != null) {
                return zzgg.zza(context, adRequestInfoParcel, string);
            }
        }
        Location zzc = com_google_android_gms_internal_zzge.zzDs.zzc(250);
        String zzc2 = com_google_android_gms_internal_zzge.zzDt.zzc(context, adRequestInfoParcel.zzCn.packageName);
        JSONObject zza = zzgg.zza(adRequestInfoParcel, zzC, zzc, com_google_android_gms_internal_zzbr, zzc2, com_google_android_gms_internal_zzge.zzDu.zzar(adRequestInfoParcel.zzCo), com_google_android_gms_internal_zzge.zzDr.zza(adRequestInfoParcel));
        if (adRequestInfoParcel.versionCode < 7) {
            try {
                zza.put("request_id", uuid);
            } catch (JSONException e) {
            }
        }
        if (zza == null) {
            return new AdResponseParcel(0);
        }
        String jSONObject = zza.toString();
        com_google_android_gms_internal_zzce.zza(zzdo, "arc");
        zzcd zzdo2 = com_google_android_gms_internal_zzce.zzdo();
        if (((Boolean) zzbz.zztE.get()).booleanValue()) {
            zzhl.zzGk.post(new C02351(com_google_android_gms_internal_zzdt, com_google_android_gms_internal_zzgh, com_google_android_gms_internal_zzce, zzdo2, jSONObject));
        } else {
            zzhl.zzGk.post(new C02362(context, adRequestInfoParcel, com_google_android_gms_internal_zzgh, com_google_android_gms_internal_zzce, zzdo2, jSONObject, com_google_android_gms_internal_zzbr));
        }
        AdResponseParcel adResponseParcel;
        try {
            zzgj com_google_android_gms_internal_zzgj = (zzgj) com_google_android_gms_internal_zzgh.zzfE().get(10, TimeUnit.SECONDS);
            if (com_google_android_gms_internal_zzgj == null) {
                adResponseParcel = new AdResponseParcel(0);
                return adResponseParcel;
            } else if (com_google_android_gms_internal_zzgj.getErrorCode() != -2) {
                adResponseParcel = new AdResponseParcel(com_google_android_gms_internal_zzgj.getErrorCode());
                zzhl.zzGk.post(new C02373(com_google_android_gms_internal_zzgh));
                return adResponseParcel;
            } else {
                if (com_google_android_gms_internal_zzce.zzds() != null) {
                    com_google_android_gms_internal_zzce.zza(com_google_android_gms_internal_zzce.zzds(), "rur");
                }
                String str = null;
                if (com_google_android_gms_internal_zzgj.zzfI()) {
                    str = com_google_android_gms_internal_zzge.zzDq.zzaq(adRequestInfoParcel.zzCn.packageName);
                }
                zzcd zzdo3 = com_google_android_gms_internal_zzce.zzdo();
                adResponseParcel = zza(adRequestInfoParcel, context, adRequestInfoParcel.zzpJ.zzGG, com_google_android_gms_internal_zzgj.getUrl(), str, zzc2, com_google_android_gms_internal_zzgj);
                if (adResponseParcel.zzCW == 1) {
                    com_google_android_gms_internal_zzge.zzDt.clearToken(context, adRequestInfoParcel.zzCn.packageName);
                }
                com_google_android_gms_internal_zzce.zza(zzdo3, "ufe");
                com_google_android_gms_internal_zzce.zza(zzdo, "tts");
                if (zzhg.zzfY() != null) {
                    zzhg.zzfY().zza(com_google_android_gms_internal_zzce);
                }
                zzhl.zzGk.post(new C02373(com_google_android_gms_internal_zzgh));
                return adResponseParcel;
            }
        } catch (Exception e2) {
            adResponseParcel = new AdResponseParcel(0);
            return adResponseParcel;
        } finally {
            zzhl.zzGk.post(new C02373(com_google_android_gms_internal_zzgh));
        }
    }

    public static AdResponseParcel zza(AdRequestInfoParcel adRequestInfoParcel, Context context, String str, String str2, String str3, String str4, zzgj com_google_android_gms_internal_zzgj) {
        HttpURLConnection httpURLConnection;
        try {
            int responseCode;
            AdResponseParcel adResponseParcel;
            zzgi com_google_android_gms_internal_zzgi = new zzgi(adRequestInfoParcel);
            zzb.zzay("AdRequestServiceImpl: Sending request: " + str2);
            URL url = new URL(str2);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            URL url2 = url;
            int i = 0;
            while (true) {
                httpURLConnection = (HttpURLConnection) url2.openConnection();
                zzo.zzbv().zza(context, str, false, httpURLConnection);
                if (!TextUtils.isEmpty(str3)) {
                    httpURLConnection.addRequestProperty("x-afma-drt-cookie", str3);
                }
                if (!TextUtils.isEmpty(str4)) {
                    httpURLConnection.addRequestProperty("Authorization", "Bearer " + str4);
                }
                if (!(com_google_android_gms_internal_zzgj == null || TextUtils.isEmpty(com_google_android_gms_internal_zzgj.zzfH()))) {
                    httpURLConnection.setDoOutput(true);
                    byte[] bytes = com_google_android_gms_internal_zzgj.zzfH().getBytes();
                    httpURLConnection.setFixedLengthStreamingMode(bytes.length);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    bufferedOutputStream.write(bytes);
                    bufferedOutputStream.close();
                }
                responseCode = httpURLConnection.getResponseCode();
                Map headerFields = httpURLConnection.getHeaderFields();
                if (responseCode < HttpStatus.SC_OK || responseCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
                    zza(url2.toString(), headerFields, null, responseCode);
                    if (responseCode < HttpStatus.SC_MULTIPLE_CHOICES || responseCode >= HttpStatus.SC_BAD_REQUEST) {
                        break;
                    }
                    Object headerField = httpURLConnection.getHeaderField("Location");
                    if (TextUtils.isEmpty(headerField)) {
                        zzb.zzaC("No location header to follow redirect.");
                        adResponseParcel = new AdResponseParcel(0);
                        httpURLConnection.disconnect();
                        return adResponseParcel;
                    }
                    url2 = new URL(headerField);
                    i++;
                    if (i > 5) {
                        zzb.zzaC("Too many redirects.");
                        adResponseParcel = new AdResponseParcel(0);
                        httpURLConnection.disconnect();
                        return adResponseParcel;
                    }
                    com_google_android_gms_internal_zzgi.zzi(headerFields);
                    httpURLConnection.disconnect();
                } else {
                    String url3 = url2.toString();
                    String zza = zzo.zzbv().zza(new InputStreamReader(httpURLConnection.getInputStream()));
                    zza(url3, headerFields, zza, responseCode);
                    com_google_android_gms_internal_zzgi.zza(url3, headerFields, zza);
                    adResponseParcel = com_google_android_gms_internal_zzgi.zzj(elapsedRealtime);
                    httpURLConnection.disconnect();
                    return adResponseParcel;
                }
            }
            zzb.zzaC("Received error HTTP response code: " + responseCode);
            adResponseParcel = new AdResponseParcel(0);
            httpURLConnection.disconnect();
            return adResponseParcel;
        } catch (IOException e) {
            zzb.zzaC("Error while connecting to ad server: " + e.getMessage());
            return new AdResponseParcel(2);
        } catch (Throwable th) {
            httpURLConnection.disconnect();
        }
    }

    public static zzgf zza(Context context, zzbr com_google_android_gms_internal_zzbr, zzge com_google_android_gms_internal_zzge) {
        zzgf com_google_android_gms_internal_zzgf;
        synchronized (zzoW) {
            if (zzDv == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                zzDv = new zzgf(context, com_google_android_gms_internal_zzbr, com_google_android_gms_internal_zzge);
            }
            com_google_android_gms_internal_zzgf = zzDv;
        }
        return com_google_android_gms_internal_zzgf;
    }

    private static zzie.zza zza(String str, zzce com_google_android_gms_internal_zzce, zzcd com_google_android_gms_internal_zzcd) {
        return new C04704(com_google_android_gms_internal_zzce, com_google_android_gms_internal_zzcd, str);
    }

    private static void zza(String str, Map<String, List<String>> map, String str2, int i) {
        if (zzb.zzL(2)) {
            zzb.zzaB("Http Response: {\n  URL:\n    " + str + "\n  Headers:");
            if (map != null) {
                for (String str3 : map.keySet()) {
                    zzb.zzaB("    " + str3 + ":");
                    for (String str32 : (List) map.get(str32)) {
                        zzb.zzaB("      " + str32);
                    }
                }
            }
            zzb.zzaB("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) {
                    zzb.zzaB(str2.substring(i2, Math.min(str2.length(), i2 + GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE)));
                }
            } else {
                zzb.zzaB("    null");
            }
            zzb.zzaB("  Response Code:\n    " + i + "\n}");
        }
    }

    public void zza(AdRequestInfoParcel adRequestInfoParcel, zzj com_google_android_gms_ads_internal_request_zzj) {
        zzo.zzby().zzb(this.mContext, adRequestInfoParcel.zzpJ);
        new C04726(this, adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzj).zzgi();
    }

    public AdResponseParcel zze(AdRequestInfoParcel adRequestInfoParcel) {
        zzhg.zze(this.mContext, adRequestInfoParcel.zzpJ.zzGG);
        return zza(this.mContext, this.zzqC, this.zzDx, this.zzDw, adRequestInfoParcel);
    }
}
