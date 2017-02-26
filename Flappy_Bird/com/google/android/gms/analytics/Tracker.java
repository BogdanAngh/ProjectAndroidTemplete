package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzad;
import com.google.android.gms.analytics.internal.zzal;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzd;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zznx;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Tracker extends zzd {
    private boolean zzIH;
    private final Map<String, String> zzII;
    private final zzad zzIJ;
    private final zza zzIK;
    private ExceptionReporter zzIL;
    private zzal zzIM;
    private final Map<String, String> zzyn;

    /* renamed from: com.google.android.gms.analytics.Tracker.1 */
    class C01051 implements Runnable {
        final /* synthetic */ Map zzIN;
        final /* synthetic */ boolean zzIO;
        final /* synthetic */ String zzIP;
        final /* synthetic */ long zzIQ;
        final /* synthetic */ boolean zzIR;
        final /* synthetic */ boolean zzIS;
        final /* synthetic */ String zzIT;
        final /* synthetic */ Tracker zzIU;

        C01051(Tracker tracker, Map map, boolean z, String str, long j, boolean z2, boolean z3, String str2) {
            this.zzIU = tracker;
            this.zzIN = map;
            this.zzIO = z;
            this.zzIP = str;
            this.zzIQ = j;
            this.zzIR = z2;
            this.zzIS = z3;
            this.zzIT = str2;
        }

        public void run() {
            boolean z = true;
            if (this.zzIU.zzIK.zzhq()) {
                this.zzIN.put("sc", "start");
            }
            zzam.zzc(this.zzIN, "cid", this.zzIU.zzhg().getClientId());
            String str = (String) this.zzIN.get("sf");
            if (str != null) {
                double zza = zzam.zza(str, 100.0d);
                if (zzam.zza(zza, (String) this.zzIN.get("cid"))) {
                    this.zzIU.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(zza));
                    return;
                }
            }
            com.google.android.gms.analytics.internal.zza zzb = this.zzIU.zzhW();
            if (this.zzIO) {
                zzam.zzb(this.zzIN, "ate", zzb.zzhy());
                zzam.zzb(this.zzIN, "adid", zzb.zzhC());
            } else {
                this.zzIN.remove("ate");
                this.zzIN.remove("adid");
            }
            zznx zzix = this.zzIU.zzhX().zzix();
            zzam.zzb(this.zzIN, "an", zzix.zzjL());
            zzam.zzb(this.zzIN, "av", zzix.zzjN());
            zzam.zzb(this.zzIN, "aid", zzix.zzsK());
            zzam.zzb(this.zzIN, "aiid", zzix.zzwi());
            this.zzIN.put("v", "1");
            this.zzIN.put("_v", zze.zzJB);
            zzam.zzb(this.zzIN, "ul", this.zzIU.zzhY().zzjE().getLanguage());
            zzam.zzb(this.zzIN, "sr", this.zzIU.zzhY().zzjF());
            boolean z2 = this.zzIP.equals("transaction") || this.zzIP.equals("item");
            if (z2 || this.zzIU.zzIJ.zzkb()) {
                long zzbj = zzam.zzbj((String) this.zzIN.get("ht"));
                if (zzbj == 0) {
                    zzbj = this.zzIQ;
                }
                if (this.zzIR) {
                    this.zzIU.zzhQ().zzc("Dry run enabled. Would have sent hit", new zzab(this.zzIU, this.zzIN, zzbj, this.zzIS));
                    return;
                }
                String str2 = (String) this.zzIN.get("cid");
                Map hashMap = new HashMap();
                zzam.zza(hashMap, "uid", this.zzIN);
                zzam.zza(hashMap, "an", this.zzIN);
                zzam.zza(hashMap, "aid", this.zzIN);
                zzam.zza(hashMap, "av", this.zzIN);
                zzam.zza(hashMap, "aiid", this.zzIN);
                String str3 = this.zzIT;
                if (TextUtils.isEmpty((CharSequence) this.zzIN.get("adid"))) {
                    z = false;
                }
                this.zzIN.put("_s", String.valueOf(this.zzIU.zzhl().zza(new zzh(0, str2, str3, z, 0, hashMap))));
                this.zzIU.zzhl().zza(new zzab(this.zzIU, this.zzIN, zzbj, this.zzIS));
                return;
            }
            this.zzIU.zzhQ().zzg(this.zzIN, "Too many hits sent too quickly, rate limiting invoked");
        }
    }

    private class zza extends zzd implements zza {
        final /* synthetic */ Tracker zzIU;
        private boolean zzIV;
        private int zzIW;
        private long zzIX;
        private boolean zzIY;
        private long zzIZ;

        protected zza(Tracker tracker, zzf com_google_android_gms_analytics_internal_zzf) {
            this.zzIU = tracker;
            super(com_google_android_gms_analytics_internal_zzf);
            this.zzIX = -1;
        }

        private void zzhr() {
            if (this.zzIX >= 0 || this.zzIV) {
                zzhg().zza(this.zzIU.zzIK);
            } else {
                zzhg().zzb(this.zzIU.zzIK);
            }
        }

        public void enableAutoActivityTracking(boolean enabled) {
            this.zzIV = enabled;
            zzhr();
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.zzIX = sessionTimeout;
            zzhr();
        }

        protected void zzhn() {
        }

        public synchronized boolean zzhq() {
            boolean z;
            z = this.zzIY;
            this.zzIY = false;
            return z;
        }

        boolean zzhs() {
            return zzhP().elapsedRealtime() >= this.zzIZ + Math.max(1000, this.zzIX);
        }

        public void zzn(Activity activity) {
            if (this.zzIW == 0 && zzhs()) {
                this.zzIY = true;
            }
            this.zzIW++;
            if (this.zzIV) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    this.zzIU.setCampaignParamsOnNextHit(intent.getData());
                }
                Map hashMap = new HashMap();
                hashMap.put("&t", "screenview");
                this.zzIU.set("&cd", this.zzIU.zzIM != null ? this.zzIU.zzIM.zzq(activity) : activity.getClass().getCanonicalName());
                if (TextUtils.isEmpty((CharSequence) hashMap.get("&dr"))) {
                    CharSequence zzp = Tracker.zzp(activity);
                    if (!TextUtils.isEmpty(zzp)) {
                        hashMap.put("&dr", zzp);
                    }
                }
                this.zzIU.send(hashMap);
            }
        }

        public void zzo(Activity activity) {
            this.zzIW--;
            this.zzIW = Math.max(0, this.zzIW);
            if (this.zzIW == 0) {
                this.zzIZ = zzhP().elapsedRealtime();
            }
        }
    }

    Tracker(zzf analytics, String trackingId, zzad rateLimiter) {
        super(analytics);
        this.zzyn = new HashMap();
        this.zzII = new HashMap();
        if (trackingId != null) {
            this.zzyn.put("&tid", trackingId);
        }
        this.zzyn.put("useSecure", "1");
        this.zzyn.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        if (rateLimiter == null) {
            this.zzIJ = new zzad("tracking");
        } else {
            this.zzIJ = rateLimiter;
        }
        this.zzIK = new zza(this, analytics);
    }

    private static void zza(Map<String, String> map, Map<String, String> map2) {
        zzu.zzu(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zzb = zzb(entry);
                if (zzb != null) {
                    map2.put(zzb, entry.getValue());
                }
            }
        }
    }

    private static boolean zza(Entry<String, String> entry) {
        String str = (String) entry.getKey();
        String str2 = (String) entry.getValue();
        return str.startsWith("&") && str.length() >= 2;
    }

    private static String zzb(Entry<String, String> entry) {
        return !zza((Entry) entry) ? null : ((String) entry.getKey()).substring(1);
    }

    private static void zzb(Map<String, String> map, Map<String, String> map2) {
        zzu.zzu(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zzb = zzb(entry);
                if (!(zzb == null || map2.containsKey(zzb))) {
                    map2.put(zzb, entry.getValue());
                }
            }
        }
    }

    private boolean zzho() {
        return this.zzIL != null;
    }

    static String zzp(Activity activity) {
        zzu.zzu(activity);
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        CharSequence stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return !TextUtils.isEmpty(stringExtra) ? stringExtra : null;
    }

    public void enableAdvertisingIdCollection(boolean enabled) {
        this.zzIH = enabled;
    }

    public void enableAutoActivityTracking(boolean enabled) {
        this.zzIK.enableAutoActivityTracking(enabled);
    }

    public void enableExceptionReporting(boolean enable) {
        synchronized (this) {
            if (zzho() == enable) {
                return;
            }
            if (enable) {
                this.zzIL = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), getContext());
                Thread.setDefaultUncaughtExceptionHandler(this.zzIL);
                zzaT("Uncaught exceptions will be reported to Google Analytics");
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zzIL.zzhh());
                zzaT("Uncaught exceptions will not be reported to Google Analytics");
            }
        }
    }

    public String get(String key) {
        zzia();
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (this.zzyn.containsKey(key)) {
            return (String) this.zzyn.get(key);
        }
        if (key.equals("&ul")) {
            return zzam.zza(Locale.getDefault());
        }
        if (key.equals("&cid")) {
            return zzhV().zziP();
        }
        if (key.equals("&sr")) {
            return zzhY().zzjF();
        }
        if (key.equals("&aid")) {
            return zzhX().zzix().zzsK();
        }
        if (key.equals("&an")) {
            return zzhX().zzix().zzjL();
        }
        if (key.equals("&av")) {
            return zzhX().zzix().zzjN();
        }
        return key.equals("&aiid") ? zzhX().zzix().zzwi() : null;
    }

    public void send(Map<String, String> params) {
        long currentTimeMillis = zzhP().currentTimeMillis();
        if (zzhg().getAppOptOut()) {
            zzaU("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean isDryRunEnabled = zzhg().isDryRunEnabled();
        Map hashMap = new HashMap();
        zza(this.zzyn, hashMap);
        zza(params, hashMap);
        boolean zze = zzam.zze((String) this.zzyn.get("useSecure"), true);
        zzb(this.zzII, hashMap);
        this.zzII.clear();
        String str = (String) hashMap.get("t");
        if (TextUtils.isEmpty(str)) {
            zzhQ().zzg(hashMap, "Missing hit type parameter");
            return;
        }
        String str2 = (String) hashMap.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzhQ().zzg(hashMap, "Missing tracking id parameter");
            return;
        }
        boolean zzhp = zzhp();
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt((String) this.zzyn.get("&a")) + 1;
                if (parseInt >= Integer.MAX_VALUE) {
                    parseInt = 1;
                }
                this.zzyn.put("&a", Integer.toString(parseInt));
            }
        }
        zzhS().zze(new C01051(this, hashMap, zzhp, str, currentTimeMillis, isDryRunEnabled, zze, str2));
    }

    public void set(String key, String value) {
        zzu.zzb((Object) key, (Object) "Key should be non-null");
        if (!TextUtils.isEmpty(key)) {
            this.zzyn.put(key, value);
        }
    }

    public void setAnonymizeIp(boolean anonymize) {
        set("&aip", zzam.zzH(anonymize));
    }

    public void setAppId(String appId) {
        set("&aid", appId);
    }

    public void setAppInstallerId(String appInstallerId) {
        set("&aiid", appInstallerId);
    }

    public void setAppName(String appName) {
        set("&an", appName);
    }

    public void setAppVersion(String appVersion) {
        set("&av", appVersion);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null && !uri.isOpaque()) {
            Object queryParameter = uri.getQueryParameter("referrer");
            if (!TextUtils.isEmpty(queryParameter)) {
                Uri parse = Uri.parse("http://hostname/?" + queryParameter);
                String queryParameter2 = parse.getQueryParameter("utm_id");
                if (queryParameter2 != null) {
                    this.zzII.put("&ci", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("anid");
                if (queryParameter2 != null) {
                    this.zzII.put("&anid", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_campaign");
                if (queryParameter2 != null) {
                    this.zzII.put("&cn", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_content");
                if (queryParameter2 != null) {
                    this.zzII.put("&cc", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_medium");
                if (queryParameter2 != null) {
                    this.zzII.put("&cm", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_source");
                if (queryParameter2 != null) {
                    this.zzII.put("&cs", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_term");
                if (queryParameter2 != null) {
                    this.zzII.put("&ck", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("dclid");
                if (queryParameter2 != null) {
                    this.zzII.put("&dclid", queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("gclid");
                if (queryParameter2 != null) {
                    this.zzII.put("&gclid", queryParameter2);
                }
                String queryParameter3 = parse.getQueryParameter("aclid");
                if (queryParameter3 != null) {
                    this.zzII.put("&aclid", queryParameter3);
                }
            }
        }
    }

    public void setClientId(String clientId) {
        set("&cid", clientId);
    }

    public void setEncoding(String encoding) {
        set("&de", encoding);
    }

    public void setHostname(String hostname) {
        set("&dh", hostname);
    }

    public void setLanguage(String language) {
        set("&ul", language);
    }

    public void setLocation(String location) {
        set("&dl", location);
    }

    public void setPage(String page) {
        set("&dp", page);
    }

    public void setReferrer(String referrer) {
        set("&dr", referrer);
    }

    public void setSampleRate(double sampleRate) {
        set("&sf", Double.toString(sampleRate));
    }

    public void setScreenColors(String screenColors) {
        set("&sd", screenColors);
    }

    public void setScreenName(String screenName) {
        set("&cd", screenName);
    }

    public void setScreenResolution(int width, int height) {
        if (width >= 0 || height >= 0) {
            set("&sr", width + "x" + height);
        } else {
            zzaW("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.zzIK.setSessionTimeout(1000 * sessionTimeout);
    }

    public void setTitle(String title) {
        set("&dt", title);
    }

    public void setUseSecure(boolean useSecure) {
        set("useSecure", zzam.zzH(useSecure));
    }

    public void setViewportSize(String viewportSize) {
        set("&vp", viewportSize);
    }

    void zza(zzal com_google_android_gms_analytics_internal_zzal) {
        zzaT("Loading Tracker config values");
        this.zzIM = com_google_android_gms_analytics_internal_zzal;
        if (this.zzIM.zzky()) {
            String trackingId = this.zzIM.getTrackingId();
            set("&tid", trackingId);
            zza("trackingId loaded", trackingId);
        }
        if (this.zzIM.zzkz()) {
            trackingId = Double.toString(this.zzIM.zzkA());
            set("&sf", trackingId);
            zza("Sample frequency loaded", trackingId);
        }
        if (this.zzIM.zzkB()) {
            int sessionTimeout = this.zzIM.getSessionTimeout();
            setSessionTimeout((long) sessionTimeout);
            zza("Session timeout loaded", Integer.valueOf(sessionTimeout));
        }
        if (this.zzIM.zzkC()) {
            boolean zzkD = this.zzIM.zzkD();
            enableAutoActivityTracking(zzkD);
            zza("Auto activity tracking loaded", Boolean.valueOf(zzkD));
        }
        if (this.zzIM.zzkE()) {
            zzkD = this.zzIM.zzkF();
            if (zzkD) {
                set("&aip", "1");
            }
            zza("Anonymize ip loaded", Boolean.valueOf(zzkD));
        }
        enableExceptionReporting(this.zzIM.zzkG());
    }

    protected void zzhn() {
        this.zzIK.zza();
        String zzjL = zzhm().zzjL();
        if (zzjL != null) {
            set("&an", zzjL);
        }
        zzjL = zzhm().zzjN();
        if (zzjL != null) {
            set("&av", zzjL);
        }
    }

    boolean zzhp() {
        return this.zzIH;
    }
}
