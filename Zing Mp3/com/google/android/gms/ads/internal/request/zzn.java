package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzfe;
import com.google.android.gms.internal.zzff;
import com.google.android.gms.internal.zzfj;
import com.google.android.gms.internal.zzge;
import com.google.android.gms.internal.zzgh;
import com.google.android.gms.internal.zzgi;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzjj;
import com.google.android.gms.internal.zzjm;
import com.google.android.gms.internal.zzkw;
import com.google.android.gms.internal.zzlg;
import com.google.android.gms.internal.zzmd;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzn extends zzkw {
    private static final Object zzaox;
    private static zzgh zzchl;
    static final long zzcmk;
    static boolean zzcml;
    private static zzff zzcmm;
    private static zzfj zzcmn;
    private static zzfe zzcmo;
    private final Context mContext;
    private final Object zzcgi;
    private final com.google.android.gms.ads.internal.request.zza.zza zzcjh;
    private final com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza zzcji;
    private com.google.android.gms.internal.zzgh.zzc zzcmp;

    /* renamed from: com.google.android.gms.ads.internal.request.zzn.1 */
    class C11021 implements Runnable {
        final /* synthetic */ com.google.android.gms.internal.zzko.zza zzamk;
        final /* synthetic */ zzn zzcmq;

        C11021(zzn com_google_android_gms_ads_internal_request_zzn, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza) {
            this.zzcmq = com_google_android_gms_ads_internal_request_zzn;
            this.zzamk = com_google_android_gms_internal_zzko_zza;
        }

        public void run() {
            this.zzcmq.zzcjh.zza(this.zzamk);
            if (this.zzcmq.zzcmp != null) {
                this.zzcmq.zzcmp.release();
                this.zzcmq.zzcmp = null;
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzn.2 */
    class C11052 implements Runnable {
        final /* synthetic */ zzn zzcmq;
        final /* synthetic */ JSONObject zzcmr;
        final /* synthetic */ String zzcms;

        /* renamed from: com.google.android.gms.ads.internal.request.zzn.2.1 */
        class C11031 implements com.google.android.gms.internal.zzlw.zzc<zzgi> {
            final /* synthetic */ C11052 zzcmt;

            C11031(C11052 c11052) {
                this.zzcmt = c11052;
            }

            public void zzb(zzgi com_google_android_gms_internal_zzgi) {
                try {
                    com_google_android_gms_internal_zzgi.zza("AFMA_getAdapterLessMediationAd", this.zzcmt.zzcmr);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Error requesting an ad url", e);
                    zzn.zzcmn.zzbe(this.zzcmt.zzcms);
                }
            }

            public /* synthetic */ void zzd(Object obj) {
                zzb((zzgi) obj);
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.request.zzn.2.2 */
        class C11042 implements com.google.android.gms.internal.zzlw.zza {
            final /* synthetic */ C11052 zzcmt;

            C11042(C11052 c11052) {
                this.zzcmt = c11052;
            }

            public void run() {
                zzn.zzcmn.zzbe(this.zzcmt.zzcms);
            }
        }

        C11052(zzn com_google_android_gms_ads_internal_request_zzn, JSONObject jSONObject, String str) {
            this.zzcmq = com_google_android_gms_ads_internal_request_zzn;
            this.zzcmr = jSONObject;
            this.zzcms = str;
        }

        public void run() {
            this.zzcmq.zzcmp = zzn.zzchl.zzny();
            this.zzcmq.zzcmp.zza(new C11031(this), new C11042(this));
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzn.3 */
    class C11063 implements Runnable {
        final /* synthetic */ zzn zzcmq;

        C11063(zzn com_google_android_gms_ads_internal_request_zzn) {
            this.zzcmq = com_google_android_gms_ads_internal_request_zzn;
        }

        public void run() {
            if (this.zzcmq.zzcmp != null) {
                this.zzcmq.zzcmp.release();
                this.zzcmq.zzcmp = null;
            }
        }
    }

    public static class zza implements zzlg<zzge> {
        public void zza(zzge com_google_android_gms_internal_zzge) {
            zzn.zzc(com_google_android_gms_internal_zzge);
        }

        public /* synthetic */ void zzd(Object obj) {
            zza((zzge) obj);
        }
    }

    public static class zzb implements zzlg<zzge> {
        public void zza(zzge com_google_android_gms_internal_zzge) {
            zzn.zzb(com_google_android_gms_internal_zzge);
        }

        public /* synthetic */ void zzd(Object obj) {
            zza((zzge) obj);
        }
    }

    public static class zzc implements zzfe {
        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            String str = (String) map.get("request_id");
            String str2 = "Invalid request: ";
            String valueOf = String.valueOf((String) map.get("errors"));
            com.google.android.gms.ads.internal.util.client.zzb.zzdi(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            zzn.zzcmn.zzbe(str);
        }
    }

    static {
        zzcmk = TimeUnit.SECONDS.toMillis(10);
        zzaox = new Object();
        zzcml = false;
        zzchl = null;
        zzcmm = null;
        zzcmn = null;
        zzcmo = null;
    }

    public zzn(Context context, com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza, com.google.android.gms.ads.internal.request.zza.zza com_google_android_gms_ads_internal_request_zza_zza) {
        super(true);
        this.zzcgi = new Object();
        this.zzcjh = com_google_android_gms_ads_internal_request_zza_zza;
        this.mContext = context;
        this.zzcji = com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza;
        synchronized (zzaox) {
            if (!zzcml) {
                zzcmn = new zzfj();
                zzcmm = new zzff(context.getApplicationContext(), com_google_android_gms_ads_internal_request_AdRequestInfoParcel_zza.zzari);
                zzcmo = new zzc();
                zzchl = new zzgh(this.mContext.getApplicationContext(), this.zzcji.zzari, (String) zzdr.zzbcx.get(), new zzb(), new zza());
                zzcml = true;
            }
        }
    }

    private JSONObject zza(AdRequestInfoParcel adRequestInfoParcel, String str) {
        Throwable e;
        Object obj;
        Map hashMap;
        JSONObject jSONObject = null;
        Bundle bundle = adRequestInfoParcel.zzcju.extras.getBundle("sdk_less_server_data");
        if (bundle != null) {
            JSONObject zza = zzjm.zza(this.mContext, new zzjj().zzf(adRequestInfoParcel).zza(zzu.zzgv().zzv(this.mContext)));
            if (zza != null) {
                Info advertisingIdInfo;
                try {
                    advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
                } catch (IOException e2) {
                    e = e2;
                    com.google.android.gms.ads.internal.util.client.zzb.zzc("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("request_param", zza);
                    hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzu.zzgm().zzap(hashMap);
                    return jSONObject;
                } catch (IllegalStateException e3) {
                    e = e3;
                    com.google.android.gms.ads.internal.util.client.zzb.zzc("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("request_param", zza);
                    hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                        }
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzu.zzgm().zzap(hashMap);
                    return jSONObject;
                } catch (GooglePlayServicesNotAvailableException e4) {
                    e = e4;
                    com.google.android.gms.ads.internal.util.client.zzb.zzc("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("request_param", zza);
                    hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                        }
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzu.zzgm().zzap(hashMap);
                    return jSONObject;
                } catch (GooglePlayServicesRepairableException e5) {
                    e = e5;
                    com.google.android.gms.ads.internal.util.client.zzb.zzc("Cannot get advertising id info", e);
                    obj = jSONObject;
                    hashMap = new HashMap();
                    hashMap.put("request_id", str);
                    hashMap.put("request_param", zza);
                    hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bundle);
                    if (advertisingIdInfo != null) {
                        hashMap.put("adid", advertisingIdInfo.getId());
                        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                        }
                        hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                    }
                    jSONObject = zzu.zzgm().zzap(hashMap);
                    return jSONObject;
                }
                hashMap = new HashMap();
                hashMap.put("request_id", str);
                hashMap.put("request_param", zza);
                hashMap.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bundle);
                if (advertisingIdInfo != null) {
                    hashMap.put("adid", advertisingIdInfo.getId());
                    if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
                    }
                    hashMap.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 0 : 1));
                }
                try {
                    jSONObject = zzu.zzgm().zzap(hashMap);
                } catch (JSONException e6) {
                }
            }
        }
        return jSONObject;
    }

    protected static void zzb(zzge com_google_android_gms_internal_zzge) {
        com_google_android_gms_internal_zzge.zza("/loadAd", zzcmn);
        com_google_android_gms_internal_zzge.zza("/fetchHttpRequest", zzcmm);
        com_google_android_gms_internal_zzge.zza("/invalidRequest", zzcmo);
    }

    protected static void zzc(zzge com_google_android_gms_internal_zzge) {
        com_google_android_gms_internal_zzge.zzb("/loadAd", zzcmn);
        com_google_android_gms_internal_zzge.zzb("/fetchHttpRequest", zzcmm);
        com_google_android_gms_internal_zzge.zzb("/invalidRequest", zzcmo);
    }

    private AdResponseParcel zze(AdRequestInfoParcel adRequestInfoParcel) {
        String zzvr = zzu.zzgm().zzvr();
        JSONObject zza = zza(adRequestInfoParcel, zzvr);
        if (zza == null) {
            return new AdResponseParcel(0);
        }
        long elapsedRealtime = zzu.zzgs().elapsedRealtime();
        Future zzbd = zzcmn.zzbd(zzvr);
        com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C11052(this, zza, zzvr));
        try {
            JSONObject jSONObject = (JSONObject) zzbd.get(zzcmk - (zzu.zzgs().elapsedRealtime() - elapsedRealtime), TimeUnit.MILLISECONDS);
            if (jSONObject == null) {
                return new AdResponseParcel(-1);
            }
            AdResponseParcel zza2 = zzjm.zza(this.mContext, adRequestInfoParcel, jSONObject.toString());
            return (zza2.errorCode == -3 || !TextUtils.isEmpty(zza2.body)) ? zza2 : new AdResponseParcel(3);
        } catch (CancellationException e) {
            return new AdResponseParcel(-1);
        } catch (InterruptedException e2) {
            return new AdResponseParcel(-1);
        } catch (TimeoutException e3) {
            return new AdResponseParcel(2);
        } catch (ExecutionException e4) {
            return new AdResponseParcel(0);
        }
    }

    public void onStop() {
        synchronized (this.zzcgi) {
            com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C11063(this));
        }
    }

    public void zzfp() {
        com.google.android.gms.ads.internal.util.client.zzb.zzdg("SdkLessAdLoaderBackgroundTask started.");
        AdRequestInfoParcel adRequestInfoParcel = new AdRequestInfoParcel(this.zzcji, null, -1);
        AdResponseParcel zze = zze(adRequestInfoParcel);
        AdSizeParcel adSizeParcel = null;
        com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C11021(this, new com.google.android.gms.internal.zzko.zza(adRequestInfoParcel, zze, null, adSizeParcel, zze.errorCode, zzu.zzgs().elapsedRealtime(), zze.zzclf, null)));
    }
}
