package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.util.List;

@zzgd
public final class zzbz {
    public static final zzbv<String> zztC;
    public static final zzbv<String> zztD;
    public static final zzbv<Boolean> zztE;
    public static final zzbv<String> zztF;
    public static final zzbv<Boolean> zztG;
    public static final zzbv<Boolean> zztH;
    public static final zzbv<Boolean> zztI;
    public static final zzbv<String> zztJ;
    public static final zzbv<String> zztK;
    public static final zzbv<String> zztL;
    public static final zzbv<Boolean> zztM;
    public static final zzbv<String> zztN;
    public static final zzbv<Integer> zztO;
    public static final zzbv<Integer> zztP;
    public static final zzbv<Long> zztQ;
    public static final zzbv<Long> zztR;
    public static final zzbv<Integer> zztS;
    public static final zzbv<String> zztT;
    public static final zzbv<Boolean> zztU;
    public static final zzbv<String> zztV;
    public static final zzbv<String> zztW;
    public static final zzbv<String> zztX;
    public static final zzbv<Boolean> zztY;
    public static final zzbv<Integer> zztZ;
    public static final zzbv<String> zzua;
    public static final zzbv<Boolean> zzub;
    public static final zzbv<Boolean> zzuc;
    public static final zzbv<Integer> zzud;
    public static final zzbv<Integer> zzue;
    public static final zzbv<Integer> zzuf;
    public static final zzbv<Integer> zzug;
    public static final zzbv<Integer> zzuh;
    public static final zzbv<String> zzui;
    public static final zzbv<Boolean> zzuj;
    public static final zzbv<Boolean> zzuk;
    public static final zzbv<Boolean> zzul;
    public static final zzbv<String> zzum;
    public static final zzbv<Boolean> zzun;
    public static final zzbv<Boolean> zzuo;
    public static final zzbv<Integer> zzup;
    public static final zzbv<String> zzuq;
    public static final zzbv<String> zzur;
    public static final zzbv<Boolean> zzus;
    public static final zzbv<Boolean> zzut;
    public static final zzbv<Boolean> zzuu;
    public static final zzbv<Boolean> zzuv;
    public static final zzbv<Long> zzuw;
    public static final zzbv<Boolean> zzux;

    static {
        zztC = zzbv.zzO("gads:sdk_core_experiment_id");
        zztD = zzbv.zzc("gads:sdk_core_location", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/sdk-core-v40.html");
        zztE = zzbv.zza("gads:request_builder:singleton_webview", Boolean.valueOf(false));
        zztF = zzbv.zzO("gads:request_builder:singleton_webview_experiment_id");
        zztG = zzbv.zza("gads:sdk_crash_report_enabled", Boolean.valueOf(false));
        zztH = zzbv.zza("gads:sdk_crash_report_full_stacktrace", Boolean.valueOf(false));
        zztI = zzbv.zza("gads:block_autoclicks", Boolean.valueOf(false));
        zztJ = zzbv.zzO("gads:block_autoclicks_experiment_id");
        zztK = zzbv.zzP("gads:prefetch:experiment_id");
        zztL = zzbv.zzO("gads:spam_app_context:experiment_id");
        zztM = zzbv.zza("gads:spam_app_context:enabled", Boolean.valueOf(false));
        zztN = zzbv.zzO("gads:video_stream_cache:experiment_id");
        zztO = zzbv.zza("gads:video_stream_cache:limit_count", 5);
        zztP = zzbv.zza("gads:video_stream_cache:limit_space", 8388608);
        zztQ = zzbv.zzb("gads:video_stream_cache:limit_time_sec", 300);
        zztR = zzbv.zzb("gads:video_stream_cache:notify_interval_millis", 1000);
        zztS = zzbv.zza("gads:video_stream_cache:connect_timeout_millis", (int) StatusCodes.AUTH_DISABLED);
        zztT = zzbv.zzP("gads:spam_ad_id_decorator:experiment_id");
        zztU = zzbv.zza("gads:spam_ad_id_decorator:enabled", Boolean.valueOf(false));
        zztV = zzbv.zzc("gad:mraid:url_banner", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/mraid/v2/mraid_app_banner.js");
        zztW = zzbv.zzc("gad:mraid:url_expanded_banner", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/mraid/v2/mraid_app_expanded_banner.js");
        zztX = zzbv.zzc("gad:mraid:url_interstitial", "https://googleads.g.doubleclick.net/mads/static/mad/sdk/native/mraid/v2/mraid_app_interstitial.js");
        zztY = zzbv.zza("gads:enabled_sdk_csi", Boolean.valueOf(false));
        zztZ = zzbv.zza("gads:sdk_csi_batch_size", 20);
        zzua = zzbv.zzc("gads:sdk_csi_server", "https://csi.gstatic.com/csi");
        zzub = zzbv.zza("gads:sdk_csi_write_to_file", Boolean.valueOf(false));
        zzuc = zzbv.zza("gads:enable_content_fetching", Boolean.valueOf(true));
        zzud = zzbv.zza("gads:content_length_weight", 1);
        zzue = zzbv.zza("gads:content_age_weight", 1);
        zzuf = zzbv.zza("gads:min_content_len", 11);
        zzug = zzbv.zza("gads:fingerprint_number", 10);
        zzuh = zzbv.zza("gads:sleep_sec", 10);
        zzui = zzbv.zzO("gads:kitkat_interstitial_workaround:experiment_id");
        zzuj = zzbv.zza("gads:kitkat_interstitial_workaround:enabled", Boolean.valueOf(true));
        zzuk = zzbv.zza("gads:interstitial_follow_url", Boolean.valueOf(true));
        zzul = zzbv.zza("gads:interstitial_follow_url:register_click", Boolean.valueOf(true));
        zzum = zzbv.zzO("gads:interstitial_follow_url:experiment_id");
        zzun = zzbv.zza("gads:analytics_enabled", Boolean.valueOf(true));
        zzuo = zzbv.zza("gads:ad_key_enabled", Boolean.valueOf(false));
        zzup = zzbv.zza("gads:webview_cache_version", 0);
        zzuq = zzbv.zzP("gads:pan:experiment_id");
        zzur = zzbv.zzc("gads:native:engine_url", "//googleads.g.doubleclick.net/mads/static/mad/sdk/native/native_ads.html");
        zzus = zzbv.zza("gads:ad_manager_creator:enabled", Boolean.valueOf(true));
        zzut = zzbv.zza("gads:log:verbose_enabled", Boolean.valueOf(false));
        zzuu = zzbv.zza("gads:sdk_less_mediation:enabled", Boolean.valueOf(true));
        zzuv = zzbv.zza("gads:device_info_caching:enabled", Boolean.valueOf(true));
        zzuw = zzbv.zzb("gads:device_info_caching_expiry_ms:expiry", 300000);
        zzux = zzbv.zza("gads:gen204_signals:enabled", Boolean.valueOf(false));
    }

    public static List<String> zzdb() {
        return zzo.zzbD().zzdb();
    }

    public static void zzw(Context context) {
        zzo.zzbE().zzw(context);
    }

    public static List<String> zzx(Context context) {
        return zzk.zzcA().zzP(context) ? zzo.zzbD().zzda() : null;
    }
}
