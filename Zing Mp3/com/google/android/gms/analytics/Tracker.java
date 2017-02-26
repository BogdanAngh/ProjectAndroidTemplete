package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzad;
import com.google.android.gms.analytics.internal.zzan;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzd;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzms;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Tracker extends zzd {
    private boolean bu;
    private final Map<String, String> bv;
    private final zzad bw;
    private final zza bx;
    private ExceptionReporter by;
    private zzan bz;
    private final Map<String, String> zzbly;

    /* renamed from: com.google.android.gms.analytics.Tracker.1 */
    class C11451 implements Runnable {
        final /* synthetic */ Map bA;
        final /* synthetic */ boolean bB;
        final /* synthetic */ String bC;
        final /* synthetic */ long bD;
        final /* synthetic */ boolean bE;
        final /* synthetic */ boolean bF;
        final /* synthetic */ String bG;
        final /* synthetic */ Tracker bH;

        C11451(Tracker tracker, Map map, boolean z, String str, long j, boolean z2, boolean z3, String str2) {
            this.bH = tracker;
            this.bA = map;
            this.bB = z;
            this.bC = str;
            this.bD = j;
            this.bE = z2;
            this.bF = z3;
            this.bG = str2;
        }

        public void run() {
            boolean z = true;
            if (this.bH.bx.zzaab()) {
                this.bA.put("sc", TtmlNode.START);
            }
            zzao.zzd(this.bA, "cid", this.bH.zzza().zzze());
            String str = (String) this.bA.get("sf");
            if (str != null) {
                double zza = zzao.zza(str, 100.0d);
                if (zzao.zza(zza, (String) this.bA.get("cid"))) {
                    this.bH.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(zza));
                    return;
                }
            }
            com.google.android.gms.analytics.internal.zza zzb = this.bH.zzacg();
            if (this.bB) {
                zzao.zzb(this.bA, "ate", zzb.zzabc());
                zzao.zzc(this.bA, "adid", zzb.zzabn());
            } else {
                this.bA.remove("ate");
                this.bA.remove("adid");
            }
            zzms zzadg = this.bH.zzach().zzadg();
            zzao.zzc(this.bA, "an", zzadg.zzaae());
            zzao.zzc(this.bA, "av", zzadg.zzaaf());
            zzao.zzc(this.bA, "aid", zzadg.zzup());
            zzao.zzc(this.bA, "aiid", zzadg.zzaag());
            this.bA.put("v", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            this.bA.put("_v", zze.cS);
            zzao.zzc(this.bA, "ul", this.bH.zzaci().zzafl().getLanguage());
            zzao.zzc(this.bA, "sr", this.bH.zzaci().zzafm());
            boolean z2 = this.bC.equals("transaction") || this.bC.equals("item");
            if (z2 || this.bH.bw.zzagf()) {
                long zzfj = zzao.zzfj((String) this.bA.get("ht"));
                if (zzfj == 0) {
                    zzfj = this.bD;
                }
                if (this.bE) {
                    this.bH.zzaca().zzc("Dry run enabled. Would have sent hit", new zzab(this.bH, this.bA, zzfj, this.bF));
                    return;
                }
                String str2 = (String) this.bA.get("cid");
                Map hashMap = new HashMap();
                zzao.zza(hashMap, "uid", this.bA);
                zzao.zza(hashMap, "an", this.bA);
                zzao.zza(hashMap, "aid", this.bA);
                zzao.zza(hashMap, "av", this.bA);
                zzao.zza(hashMap, "aiid", this.bA);
                String str3 = this.bG;
                if (TextUtils.isEmpty((CharSequence) this.bA.get("adid"))) {
                    z = false;
                }
                this.bA.put("_s", String.valueOf(this.bH.zzzg().zza(new zzh(0, str2, str3, z, 0, hashMap))));
                this.bH.zzzg().zza(new zzab(this.bH, this.bA, zzfj, this.bF));
                return;
            }
            this.bH.zzaca().zzh(this.bA, "Too many hits sent too quickly, rate limiting invoked");
        }
    }

    private class zza extends zzd implements zza {
        final /* synthetic */ Tracker bH;
        private boolean bI;
        private int bJ;
        private long bK;
        private boolean bL;
        private long bM;

        protected zza(Tracker tracker, zzf com_google_android_gms_analytics_internal_zzf) {
            this.bH = tracker;
            super(com_google_android_gms_analytics_internal_zzf);
            this.bK = -1;
        }

        private void zzaac() {
            if (this.bK >= 0 || this.bI) {
                zzza().zza(this.bH.bx);
            } else {
                zzza().zzb(this.bH.bx);
            }
        }

        public void enableAutoActivityTracking(boolean z) {
            this.bI = z;
            zzaac();
        }

        public void setSessionTimeout(long j) {
            this.bK = j;
            zzaac();
        }

        public synchronized boolean zzaab() {
            boolean z;
            z = this.bL;
            this.bL = false;
            return z;
        }

        boolean zzaad() {
            return zzabz().elapsedRealtime() >= this.bM + Math.max(1000, this.bK);
        }

        public void zzo(Activity activity) {
            if (this.bJ == 0 && zzaad()) {
                this.bL = true;
            }
            this.bJ++;
            if (this.bI) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    this.bH.setCampaignParamsOnNextHit(intent.getData());
                }
                Map hashMap = new HashMap();
                hashMap.put("&t", "screenview");
                this.bH.set("&cd", this.bH.bz != null ? this.bH.bz.zzr(activity) : activity.getClass().getCanonicalName());
                if (TextUtils.isEmpty((CharSequence) hashMap.get("&dr"))) {
                    CharSequence zzq = Tracker.zzq(activity);
                    if (!TextUtils.isEmpty(zzq)) {
                        hashMap.put("&dr", zzq);
                    }
                }
                this.bH.send(hashMap);
            }
        }

        public void zzp(Activity activity) {
            this.bJ--;
            this.bJ = Math.max(0, this.bJ);
            if (this.bJ == 0) {
                this.bM = zzabz().elapsedRealtime();
            }
        }

        protected void zzzy() {
        }
    }

    Tracker(zzf com_google_android_gms_analytics_internal_zzf, String str, zzad com_google_android_gms_analytics_internal_zzad) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzbly = new HashMap();
        this.bv = new HashMap();
        if (str != null) {
            this.zzbly.put("&tid", str);
        }
        this.zzbly.put("useSecure", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        this.zzbly.put("&a", Integer.toString(new Random().nextInt(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) + 1));
        if (com_google_android_gms_analytics_internal_zzad == null) {
            this.bw = new zzad("tracking", zzabz());
        } else {
            this.bw = com_google_android_gms_analytics_internal_zzad;
        }
        this.bx = new zza(this, com_google_android_gms_analytics_internal_zzf);
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
        zzaa.zzy(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zzb = zzb(entry);
                if (zzb != null) {
                    map2.put(zzb, (String) entry.getValue());
                }
            }
        }
    }

    private static void zzc(Map<String, String> map, Map<String, String> map2) {
        zzaa.zzy(map2);
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                String zzb = zzb(entry);
                if (!(zzb == null || map2.containsKey(zzb))) {
                    map2.put(zzb, (String) entry.getValue());
                }
            }
        }
    }

    static String zzq(Activity activity) {
        zzaa.zzy(activity);
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        CharSequence stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return !TextUtils.isEmpty(stringExtra) ? stringExtra : null;
    }

    private boolean zzzz() {
        return this.by != null;
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.bu = z;
    }

    public void enableAutoActivityTracking(boolean z) {
        this.bx.enableAutoActivityTracking(z);
    }

    public void enableExceptionReporting(boolean z) {
        synchronized (this) {
            if (zzzz() == z) {
                return;
            }
            if (z) {
                this.by = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), getContext());
                Thread.setDefaultUncaughtExceptionHandler(this.by);
                zzes("Uncaught exceptions will be reported to Google Analytics");
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.by.zzzb());
                zzes("Uncaught exceptions will not be reported to Google Analytics");
            }
        }
    }

    public String get(String str) {
        zzacj();
        return TextUtils.isEmpty(str) ? null : this.zzbly.containsKey(str) ? (String) this.zzbly.get(str) : str.equals("&ul") ? zzao.zza(Locale.getDefault()) : str.equals("&cid") ? zzacf().zzady() : str.equals("&sr") ? zzaci().zzafm() : str.equals("&aid") ? zzach().zzadg().zzup() : str.equals("&an") ? zzach().zzadg().zzaae() : str.equals("&av") ? zzach().zzadg().zzaaf() : str.equals("&aiid") ? zzach().zzadg().zzaag() : null;
    }

    public void send(Map<String, String> map) {
        long currentTimeMillis = zzabz().currentTimeMillis();
        if (zzza().getAppOptOut()) {
            zzet("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean isDryRunEnabled = zzza().isDryRunEnabled();
        Map hashMap = new HashMap();
        zzb(this.zzbly, hashMap);
        zzb(map, hashMap);
        boolean zzg = zzao.zzg((String) this.zzbly.get("useSecure"), true);
        zzc(this.bv, hashMap);
        this.bv.clear();
        String str = (String) hashMap.get("t");
        if (TextUtils.isEmpty(str)) {
            zzaca().zzh(hashMap, "Missing hit type parameter");
            return;
        }
        String str2 = (String) hashMap.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzaca().zzh(hashMap, "Missing tracking id parameter");
            return;
        }
        boolean zzaaa = zzaaa();
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int parseInt = Integer.parseInt((String) this.zzbly.get("&a")) + 1;
                if (parseInt >= ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
                    parseInt = 1;
                }
                this.zzbly.put("&a", Integer.toString(parseInt));
            }
        }
        zzacc().zzg(new C11451(this, hashMap, zzaaa, str, currentTimeMillis, isDryRunEnabled, zzg, str2));
    }

    public void set(String str, String str2) {
        zzaa.zzb((Object) str, (Object) "Key should be non-null");
        if (!TextUtils.isEmpty(str)) {
            this.zzbly.put(str, str2);
        }
    }

    public void setAnonymizeIp(boolean z) {
        set("&aip", zzao.zzax(z));
    }

    public void setAppId(String str) {
        set("&aid", str);
    }

    public void setAppInstallerId(String str) {
        set("&aiid", str);
    }

    public void setAppName(String str) {
        set("&an", str);
    }

    public void setAppVersion(String str) {
        set("&av", str);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null && !uri.isOpaque()) {
            CharSequence queryParameter = uri.getQueryParameter("referrer");
            if (!TextUtils.isEmpty(queryParameter)) {
                String str = "http://hostname/?";
                String valueOf = String.valueOf(queryParameter);
                Uri parse = Uri.parse(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                str = parse.getQueryParameter("utm_id");
                if (str != null) {
                    this.bv.put("&ci", str);
                }
                str = parse.getQueryParameter("anid");
                if (str != null) {
                    this.bv.put("&anid", str);
                }
                str = parse.getQueryParameter("utm_campaign");
                if (str != null) {
                    this.bv.put("&cn", str);
                }
                str = parse.getQueryParameter("utm_content");
                if (str != null) {
                    this.bv.put("&cc", str);
                }
                str = parse.getQueryParameter("utm_medium");
                if (str != null) {
                    this.bv.put("&cm", str);
                }
                str = parse.getQueryParameter("utm_source");
                if (str != null) {
                    this.bv.put("&cs", str);
                }
                str = parse.getQueryParameter("utm_term");
                if (str != null) {
                    this.bv.put("&ck", str);
                }
                str = parse.getQueryParameter("dclid");
                if (str != null) {
                    this.bv.put("&dclid", str);
                }
                str = parse.getQueryParameter("gclid");
                if (str != null) {
                    this.bv.put("&gclid", str);
                }
                valueOf = parse.getQueryParameter("aclid");
                if (valueOf != null) {
                    this.bv.put("&aclid", valueOf);
                }
            }
        }
    }

    public void setClientId(String str) {
        set("&cid", str);
    }

    public void setEncoding(String str) {
        set("&de", str);
    }

    public void setHostname(String str) {
        set("&dh", str);
    }

    public void setLanguage(String str) {
        set("&ul", str);
    }

    public void setLocation(String str) {
        set("&dl", str);
    }

    public void setPage(String str) {
        set("&dp", str);
    }

    public void setReferrer(String str) {
        set("&dr", str);
    }

    public void setSampleRate(double d) {
        set("&sf", Double.toString(d));
    }

    public void setScreenColors(String str) {
        set("&sd", str);
    }

    public void setScreenName(String str) {
        set("&cd", str);
    }

    public void setScreenResolution(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            set("&sr", i + "x" + i2);
        } else {
            zzev("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long j) {
        this.bx.setSessionTimeout(1000 * j);
    }

    public void setTitle(String str) {
        set("&dt", str);
    }

    public void setUseSecure(boolean z) {
        set("useSecure", zzao.zzax(z));
    }

    public void setViewportSize(String str) {
        set("&vp", str);
    }

    void zza(zzan com_google_android_gms_analytics_internal_zzan) {
        zzes("Loading Tracker config values");
        this.bz = com_google_android_gms_analytics_internal_zzan;
        if (this.bz.zzahc()) {
            String trackingId = this.bz.getTrackingId();
            set("&tid", trackingId);
            zza("trackingId loaded", trackingId);
        }
        if (this.bz.zzahd()) {
            trackingId = Double.toString(this.bz.zzahe());
            set("&sf", trackingId);
            zza("Sample frequency loaded", trackingId);
        }
        if (this.bz.zzahf()) {
            int sessionTimeout = this.bz.getSessionTimeout();
            setSessionTimeout((long) sessionTimeout);
            zza("Session timeout loaded", Integer.valueOf(sessionTimeout));
        }
        if (this.bz.zzahg()) {
            boolean zzahh = this.bz.zzahh();
            enableAutoActivityTracking(zzahh);
            zza("Auto activity tracking loaded", Boolean.valueOf(zzahh));
        }
        if (this.bz.zzahi()) {
            zzahh = this.bz.zzahj();
            if (zzahh) {
                set("&aip", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            }
            zza("Anonymize ip loaded", Boolean.valueOf(zzahh));
        }
        enableExceptionReporting(this.bz.zzahk());
    }

    boolean zzaaa() {
        return this.bu;
    }

    protected void zzzy() {
        this.bx.initialize();
        String zzaae = zzzh().zzaae();
        if (zzaae != null) {
            set("&an", zzaae);
        }
        zzaae = zzzh().zzaaf();
        if (zzaae != null) {
            set("&av", zzaae);
        }
    }
}
