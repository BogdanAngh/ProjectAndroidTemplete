package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.cache.CacheEntryParcel;
import com.google.android.gms.ads.internal.cache.CacheOffering;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.overlay.zzp;
import com.google.android.gms.ads.internal.zzu;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@zzji
public class zzme extends WebViewClient {
    private static final String[] zzcza;
    private static final String[] zzczb;
    private final Object zzako;
    private boolean zzasy;
    private com.google.android.gms.ads.internal.client.zza zzayj;
    protected zzmd zzbnz;
    private zzfa zzbpi;
    private zzfi zzbqr;
    private com.google.android.gms.ads.internal.zze zzbqt;
    private zzhq zzbqu;
    private zzfg zzbqw;
    private zzhw zzbym;
    private zza zzcgo;
    private final HashMap<String, List<zzfe>> zzczc;
    private zzg zzczd;
    private zzb zzcze;
    private zzc zzczf;
    private boolean zzczg;
    private boolean zzczh;
    private OnGlobalLayoutListener zzczi;
    private OnScrollChangedListener zzczj;
    private boolean zzczk;
    private zzp zzczl;
    private final zzhu zzczm;
    private zze zzczn;
    @Nullable
    protected com.google.android.gms.ads.internal.safebrowsing.zzc zzczo;
    private boolean zzczp;
    private boolean zzczq;
    private boolean zzczr;
    private int zzczs;

    public interface zza {
        void zza(zzmd com_google_android_gms_internal_zzmd, boolean z);
    }

    public interface zze {
        void zzff();
    }

    public interface zzc {
        void zzfg();
    }

    public interface zzb {
        void zzk(zzmd com_google_android_gms_internal_zzmd);
    }

    /* renamed from: com.google.android.gms.internal.zzme.1 */
    class C14611 implements Runnable {
        final /* synthetic */ zzme zzczt;

        C14611(zzme com_google_android_gms_internal_zzme) {
            this.zzczt = com_google_android_gms_internal_zzme;
        }

        public void run() {
            if (this.zzczt.zzczo != null) {
                this.zzczt.zzczo.zzp(this.zzczt.zzbnz.getView());
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzme.2 */
    class C14622 implements Runnable {
        final /* synthetic */ zzme zzczt;

        C14622(zzme com_google_android_gms_internal_zzme) {
            this.zzczt = com_google_android_gms_internal_zzme;
        }

        public void run() {
            this.zzczt.zzbnz.zzxp();
            com.google.android.gms.ads.internal.overlay.zzd zzxa = this.zzczt.zzbnz.zzxa();
            if (zzxa != null) {
                zzxa.zzpo();
            }
            if (this.zzczt.zzczf != null) {
                this.zzczt.zzczf.zzfg();
                this.zzczt.zzczf = null;
            }
        }
    }

    private static class zzd implements zzg {
        private zzg zzczd;
        private zzmd zzczu;

        public zzd(zzmd com_google_android_gms_internal_zzmd, zzg com_google_android_gms_ads_internal_overlay_zzg) {
            this.zzczu = com_google_android_gms_internal_zzmd;
            this.zzczd = com_google_android_gms_ads_internal_overlay_zzg;
        }

        public void onPause() {
        }

        public void onResume() {
        }

        public void zzeq() {
            this.zzczd.zzeq();
            this.zzczu.zzww();
        }

        public void zzer() {
            this.zzczd.zzer();
            this.zzczu.zzps();
        }
    }

    static {
        zzcza = new String[]{"UNKNOWN", "HOST_LOOKUP", "UNSUPPORTED_AUTH_SCHEME", "AUTHENTICATION", "PROXY_AUTHENTICATION", "CONNECT", "IO", "TIMEOUT", "REDIRECT_LOOP", "UNSUPPORTED_SCHEME", "FAILED_SSL_HANDSHAKE", "BAD_URL", "FILE", "FILE_NOT_FOUND", "TOO_MANY_REQUESTS"};
        zzczb = new String[]{"NOT_YET_VALID", "EXPIRED", "ID_MISMATCH", "UNTRUSTED", "DATE_INVALID", "INVALID"};
    }

    public zzme(zzmd com_google_android_gms_internal_zzmd, boolean z) {
        this(com_google_android_gms_internal_zzmd, z, new zzhu(com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zzmd.zzwz(), new zzdj(com_google_android_gms_internal_zzmd.getContext())), null);
    }

    zzme(zzmd com_google_android_gms_internal_zzmd, boolean z, zzhu com_google_android_gms_internal_zzhu, zzhq com_google_android_gms_internal_zzhq) {
        this.zzczc = new HashMap();
        this.zzako = new Object();
        this.zzczg = false;
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzasy = z;
        this.zzczm = com_google_android_gms_internal_zzhu;
        this.zzbqu = com_google_android_gms_internal_zzhq;
    }

    private void zzb(Context context, String str, String str2, String str3) {
        if (((Boolean) zzdr.zzbgy.get()).booleanValue()) {
            Bundle bundle = new Bundle();
            bundle.putString(NotificationCompatApi24.CATEGORY_ERROR, str);
            bundle.putString("code", str2);
            bundle.putString("host", zzdl(str3));
            zzu.zzgm().zza(context, this.zzbnz.zzxf().zzda, "gmob-apps", bundle, true);
        }
    }

    private String zzdl(String str) {
        if (TextUtils.isEmpty(str)) {
            return BuildConfig.FLAVOR;
        }
        Uri parse = Uri.parse(str);
        return parse.getHost() != null ? parse.getHost() : BuildConfig.FLAVOR;
    }

    private static boolean zzi(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    private void zzye() {
        if (this.zzcze != null) {
            this.zzcze.zzk(this.zzbnz);
            this.zzcze = null;
        }
    }

    public final void onLoadResource(WebView webView, String str) {
        String str2 = "Loading resource: ";
        String valueOf = String.valueOf(str);
        zzkx.m1697v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        Uri parse = Uri.parse(str);
        if ("gmsg".equalsIgnoreCase(parse.getScheme()) && "mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
            zzj(parse);
        }
    }

    public final void onPageFinished(WebView webView, String str) {
        synchronized (this.zzako) {
            if (this.zzczp) {
                zzkx.m1697v("Blank page loaded, 1...");
                this.zzbnz.zzxh();
                return;
            }
            this.zzczq = true;
            zzye();
            zzyf();
        }
    }

    public final void onReceivedError(WebView webView, int i, String str, String str2) {
        String valueOf = (i >= 0 || (-i) - 1 >= zzcza.length) ? String.valueOf(i) : zzcza[(-i) - 1];
        zzb(this.zzbnz.getContext(), "http_err", valueOf, str2);
        super.onReceivedError(webView, i, str, str2);
    }

    public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        if (sslError != null) {
            int primaryError = sslError.getPrimaryError();
            String valueOf = (primaryError < 0 || primaryError >= zzczb.length) ? String.valueOf(primaryError) : zzczb[primaryError];
            zzb(this.zzbnz.getContext(), "ssl_err", valueOf, zzu.zzgo().zza(sslError));
        }
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    public final void reset() {
        if (this.zzczo != null) {
            this.zzczo.zzuf();
            this.zzczo = null;
        }
        synchronized (this.zzako) {
            this.zzczc.clear();
            this.zzayj = null;
            this.zzczd = null;
            this.zzcgo = null;
            this.zzcze = null;
            this.zzbpi = null;
            this.zzczg = false;
            this.zzasy = false;
            this.zzczh = false;
            this.zzczk = false;
            this.zzbqw = null;
            this.zzczl = null;
            this.zzczf = null;
            if (this.zzbqu != null) {
                this.zzbqu.zzt(true);
                this.zzbqu = null;
            }
        }
    }

    @TargetApi(11)
    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        try {
            CacheOffering zzak = CacheOffering.zzak(str);
            if (zzak == null) {
                return null;
            }
            CacheEntryParcel zza = zzu.zzgr().zza(zzak);
            return (zza == null || !zza.zzju()) ? null : new WebResourceResponse(BuildConfig.FLAVOR, BuildConfig.FLAVOR, zza.zzjv());
        } catch (Throwable th) {
            return null;
        }
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case C1569R.styleable.AppCompatTheme_panelBackground /*79*/:
            case C1569R.styleable.AppCompatTheme_colorAccent /*85*/:
            case C1569R.styleable.AppCompatTheme_colorControlNormal /*86*/:
            case C1569R.styleable.AppCompatTheme_colorControlActivated /*87*/:
            case C1569R.styleable.AppCompatTheme_colorControlHighlight /*88*/:
            case C1569R.styleable.AppCompatTheme_colorButtonNormal /*89*/:
            case C1569R.styleable.AppCompatTheme_colorSwitchThumbNormal /*90*/:
            case C1569R.styleable.AppCompatTheme_controlBackground /*91*/:
            case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
            case TransportMediator.KEYCODE_MEDIA_PAUSE /*127*/:
            case AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS /*128*/:
            case 129:
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
            case 222:
                return true;
            default:
                return false;
        }
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        String str2 = "AdWebView shouldOverrideUrlLoading: ";
        String valueOf = String.valueOf(str);
        zzkx.m1697v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        Uri parse = Uri.parse(str);
        if ("gmsg".equalsIgnoreCase(parse.getScheme()) && "mobileads.google.com".equalsIgnoreCase(parse.getHost())) {
            zzj(parse);
        } else if (this.zzczg && webView == this.zzbnz.getWebView() && zzi(parse)) {
            if (this.zzayj != null && ((Boolean) zzdr.zzbfo.get()).booleanValue()) {
                this.zzayj.onAdClicked();
                if (this.zzczo != null) {
                    this.zzczo.zzcu(str);
                }
                this.zzayj = null;
            }
            return super.shouldOverrideUrlLoading(webView, str);
        } else if (this.zzbnz.getWebView().willNotDraw()) {
            str2 = "AdWebView unable to handle URL: ";
            valueOf = String.valueOf(str);
            com.google.android.gms.ads.internal.util.client.zzb.zzdi(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else {
            Uri uri;
            try {
                zzav zzxe = this.zzbnz.zzxe();
                if (zzxe != null && zzxe.zzc(parse)) {
                    parse = zzxe.zza(parse, this.zzbnz.getContext(), this.zzbnz.getView());
                }
                uri = parse;
            } catch (zzaw e) {
                String str3 = "Unable to append parameter to URL: ";
                str2 = String.valueOf(str);
                com.google.android.gms.ads.internal.util.client.zzb.zzdi(str2.length() != 0 ? str3.concat(str2) : new String(str3));
                uri = parse;
            }
            if (this.zzbqt == null || this.zzbqt.zzfe()) {
                zza(new AdLauncherIntentInfoParcel("android.intent.action.VIEW", uri.toString(), null, null, null, null, null));
            } else {
                this.zzbqt.zzy(str);
            }
        }
        return true;
    }

    public void zza(int i, int i2, boolean z) {
        this.zzczm.zze(i, i2);
        if (this.zzbqu != null) {
            this.zzbqu.zza(i, i2, z);
        }
    }

    public final void zza(OnGlobalLayoutListener onGlobalLayoutListener, OnScrollChangedListener onScrollChangedListener) {
        synchronized (this.zzako) {
            this.zzczh = true;
            this.zzbnz.zzxp();
            this.zzczi = onGlobalLayoutListener;
            this.zzczj = onScrollChangedListener;
        }
    }

    public void zza(com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzfa com_google_android_gms_internal_zzfa, zzp com_google_android_gms_ads_internal_overlay_zzp, boolean z, zzfg com_google_android_gms_internal_zzfg, @Nullable zzfi com_google_android_gms_internal_zzfi, com.google.android.gms.ads.internal.zze com_google_android_gms_ads_internal_zze, zzhw com_google_android_gms_internal_zzhw, @Nullable com.google.android.gms.ads.internal.safebrowsing.zzc com_google_android_gms_ads_internal_safebrowsing_zzc) {
        if (com_google_android_gms_ads_internal_zze == null) {
            com_google_android_gms_ads_internal_zze = new com.google.android.gms.ads.internal.zze(this.zzbnz.getContext());
        }
        this.zzbqu = new zzhq(this.zzbnz, com_google_android_gms_internal_zzhw);
        this.zzczo = com_google_android_gms_ads_internal_safebrowsing_zzc;
        zza("/appEvent", new zzez(com_google_android_gms_internal_zzfa));
        zza("/backButton", zzfd.zzbpu);
        zza("/refresh", zzfd.zzbpv);
        zza("/canOpenURLs", zzfd.zzbpk);
        zza("/canOpenIntents", zzfd.zzbpl);
        zza("/click", zzfd.zzbpm);
        zza("/close", zzfd.zzbpn);
        zza("/customClose", zzfd.zzbpp);
        zza("/instrument", zzfd.zzbpz);
        zza("/delayPageLoaded", zzfd.zzbqb);
        zza("/delayPageClosed", zzfd.zzbqc);
        zza("/getLocationInfo", zzfd.zzbqd);
        zza("/httpTrack", zzfd.zzbpq);
        zza("/log", zzfd.zzbpr);
        zza("/mraid", new zzfk(com_google_android_gms_ads_internal_zze, this.zzbqu));
        zza("/mraidLoaded", this.zzczm);
        zza("/open", new zzfl(com_google_android_gms_internal_zzfg, com_google_android_gms_ads_internal_zze, this.zzbqu));
        zza("/precache", zzfd.zzbpy);
        zza("/touch", zzfd.zzbpt);
        zza("/video", zzfd.zzbpw);
        zza("/videoMeta", zzfd.zzbpx);
        zza("/appStreaming", zzfd.zzbpo);
        if (com_google_android_gms_internal_zzfi != null) {
            zza("/setInterstitialProperties", new zzfh(com_google_android_gms_internal_zzfi));
        }
        this.zzayj = com_google_android_gms_ads_internal_client_zza;
        this.zzczd = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzbpi = com_google_android_gms_internal_zzfa;
        this.zzbqw = com_google_android_gms_internal_zzfg;
        this.zzczl = com_google_android_gms_ads_internal_overlay_zzp;
        this.zzbqt = com_google_android_gms_ads_internal_zze;
        this.zzbym = com_google_android_gms_internal_zzhw;
        this.zzbqr = com_google_android_gms_internal_zzfi;
        zzao(z);
    }

    public final void zza(AdLauncherIntentInfoParcel adLauncherIntentInfoParcel) {
        zzg com_google_android_gms_ads_internal_overlay_zzg = null;
        boolean zzxg = this.zzbnz.zzxg();
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!zzxg || this.zzbnz.zzeg().zzazr) ? this.zzayj : null;
        if (!zzxg) {
            com_google_android_gms_ads_internal_overlay_zzg = this.zzczd;
        }
        zza(new AdOverlayInfoParcel(adLauncherIntentInfoParcel, com_google_android_gms_ads_internal_client_zza, com_google_android_gms_ads_internal_overlay_zzg, this.zzczl, this.zzbnz.zzxf()));
    }

    public void zza(AdOverlayInfoParcel adOverlayInfoParcel) {
        boolean z = false;
        boolean zzou = this.zzbqu != null ? this.zzbqu.zzou() : false;
        com.google.android.gms.ads.internal.overlay.zze zzgk = zzu.zzgk();
        Context context = this.zzbnz.getContext();
        if (!zzou) {
            z = true;
        }
        zzgk.zza(context, adOverlayInfoParcel, z);
        if (this.zzczo != null) {
            String str = adOverlayInfoParcel.url;
            if (str == null && adOverlayInfoParcel.zzcbj != null) {
                str = adOverlayInfoParcel.zzcbj.url;
            }
            this.zzczo.zzcu(str);
        }
    }

    public void zza(zza com_google_android_gms_internal_zzme_zza) {
        this.zzcgo = com_google_android_gms_internal_zzme_zza;
    }

    public void zza(zzb com_google_android_gms_internal_zzme_zzb) {
        this.zzcze = com_google_android_gms_internal_zzme_zzb;
    }

    public void zza(zzc com_google_android_gms_internal_zzme_zzc) {
        this.zzczf = com_google_android_gms_internal_zzme_zzc;
    }

    public void zza(zze com_google_android_gms_internal_zzme_zze) {
        this.zzczn = com_google_android_gms_internal_zzme_zze;
    }

    public void zza(String str, zzfe com_google_android_gms_internal_zzfe) {
        synchronized (this.zzako) {
            List list = (List) this.zzczc.get(str);
            if (list == null) {
                list = new CopyOnWriteArrayList();
                this.zzczc.put(str, list);
            }
            list.add(com_google_android_gms_internal_zzfe);
        }
    }

    public final void zza(boolean z, int i) {
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!this.zzbnz.zzxg() || this.zzbnz.zzeg().zzazr) ? this.zzayj : null;
        zza(new AdOverlayInfoParcel(com_google_android_gms_ads_internal_client_zza, this.zzczd, this.zzczl, this.zzbnz, z, i, this.zzbnz.zzxf()));
    }

    public final void zza(boolean z, int i, String str) {
        zzg com_google_android_gms_ads_internal_overlay_zzg = null;
        boolean zzxg = this.zzbnz.zzxg();
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!zzxg || this.zzbnz.zzeg().zzazr) ? this.zzayj : null;
        if (!zzxg) {
            com_google_android_gms_ads_internal_overlay_zzg = new zzd(this.zzbnz, this.zzczd);
        }
        zza(new AdOverlayInfoParcel(com_google_android_gms_ads_internal_client_zza, com_google_android_gms_ads_internal_overlay_zzg, this.zzbpi, this.zzczl, this.zzbnz, z, i, str, this.zzbnz.zzxf(), this.zzbqw));
    }

    public final void zza(boolean z, int i, String str, String str2) {
        boolean zzxg = this.zzbnz.zzxg();
        com.google.android.gms.ads.internal.client.zza com_google_android_gms_ads_internal_client_zza = (!zzxg || this.zzbnz.zzeg().zzazr) ? this.zzayj : null;
        zza(new AdOverlayInfoParcel(com_google_android_gms_ads_internal_client_zza, zzxg ? null : new zzd(this.zzbnz, this.zzczd), this.zzbpi, this.zzczl, this.zzbnz, z, i, str, str2, this.zzbnz.zzxf(), this.zzbqw));
    }

    public void zzao(boolean z) {
        this.zzczg = z;
    }

    public void zzb(String str, zzfe com_google_android_gms_internal_zzfe) {
        synchronized (this.zzako) {
            List list = (List) this.zzczc.get(str);
            if (list == null) {
                return;
            }
            list.remove(com_google_android_gms_internal_zzfe);
        }
    }

    public void zzd(int i, int i2) {
        if (this.zzbqu != null) {
            this.zzbqu.zzd(i, i2);
        }
    }

    public boolean zzic() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzasy;
        }
        return z;
    }

    public void zzj(Uri uri) {
        String path = uri.getPath();
        List<zzfe> list = (List) this.zzczc.get(path);
        if (list != null) {
            Map zzg = zzu.zzgm().zzg(uri);
            if (com.google.android.gms.ads.internal.util.client.zzb.zzbi(2)) {
                String str = "Received GMSG: ";
                path = String.valueOf(path);
                zzkx.m1697v(path.length() != 0 ? str.concat(path) : new String(str));
                for (String path2 : zzg.keySet()) {
                    str = (String) zzg.get(path2);
                    zzkx.m1697v(new StringBuilder((String.valueOf(path2).length() + 4) + String.valueOf(str).length()).append("  ").append(path2).append(": ").append(str).toString());
                }
            }
            for (zzfe zza : list) {
                zza.zza(this.zzbnz, zzg);
            }
            return;
        }
        String valueOf = String.valueOf(uri);
        zzkx.m1697v(new StringBuilder(String.valueOf(valueOf).length() + 32).append("No GMSG handler found for GMSG: ").append(valueOf).toString());
    }

    public void zzo(zzmd com_google_android_gms_internal_zzmd) {
        this.zzbnz = com_google_android_gms_internal_zzmd;
    }

    public final void zzpo() {
        synchronized (this.zzako) {
            this.zzczg = false;
            this.zzasy = true;
            zzu.zzgm().runOnUiThread(new C14622(this));
        }
    }

    public com.google.android.gms.ads.internal.zze zzxu() {
        return this.zzbqt;
    }

    public boolean zzxv() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzczh;
        }
        return z;
    }

    public OnGlobalLayoutListener zzxw() {
        OnGlobalLayoutListener onGlobalLayoutListener;
        synchronized (this.zzako) {
            onGlobalLayoutListener = this.zzczi;
        }
        return onGlobalLayoutListener;
    }

    public OnScrollChangedListener zzxx() {
        OnScrollChangedListener onScrollChangedListener;
        synchronized (this.zzako) {
            onScrollChangedListener = this.zzczj;
        }
        return onScrollChangedListener;
    }

    public boolean zzxy() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzczk;
        }
        return z;
    }

    public void zzxz() {
        synchronized (this.zzako) {
            zzkx.m1697v("Loading blank page in WebView, 2...");
            this.zzczp = true;
            this.zzbnz.zzdj("about:blank");
        }
    }

    public void zzya() {
        if (this.zzczo != null) {
            zzlb.zzcvl.post(new C14611(this));
        }
    }

    public void zzyb() {
        synchronized (this.zzako) {
            this.zzczk = true;
        }
        this.zzczs++;
        zzyf();
    }

    public void zzyc() {
        this.zzczs--;
        zzyf();
    }

    public void zzyd() {
        this.zzczr = true;
        zzyf();
    }

    public final void zzyf() {
        if (this.zzcgo != null && ((this.zzczq && this.zzczs <= 0) || this.zzczr)) {
            this.zzcgo.zza(this.zzbnz, !this.zzczr);
            this.zzcgo = null;
        }
        this.zzbnz.zzxq();
    }

    public zze zzyg() {
        return this.zzczn;
    }
}
