package com.google.android.gms.analytics.internal;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.internal.zzkf;

public final class zzy {
    public static zza<Boolean> zzKZ;
    public static zza<String> zzLA;
    public static zza<Integer> zzLB;
    public static zza<Long> zzLC;
    public static zza<Integer> zzLD;
    public static zza<Integer> zzLE;
    public static zza<Long> zzLF;
    public static zza<String> zzLG;
    public static zza<Integer> zzLH;
    public static zza<Boolean> zzLI;
    public static zza<Long> zzLJ;
    public static zza<Long> zzLK;
    public static zza<Long> zzLL;
    public static zza<Long> zzLM;
    public static zza<Long> zzLN;
    public static zza<Long> zzLO;
    public static zza<Long> zzLP;
    public static zza<Boolean> zzLa;
    public static zza<String> zzLb;
    public static zza<Long> zzLc;
    public static zza<Float> zzLd;
    public static zza<Integer> zzLe;
    public static zza<Integer> zzLf;
    public static zza<Integer> zzLg;
    public static zza<Long> zzLh;
    public static zza<Long> zzLi;
    public static zza<Long> zzLj;
    public static zza<Long> zzLk;
    public static zza<Long> zzLl;
    public static zza<Long> zzLm;
    public static zza<Integer> zzLn;
    public static zza<Integer> zzLo;
    public static zza<String> zzLp;
    public static zza<String> zzLq;
    public static zza<String> zzLr;
    public static zza<String> zzLs;
    public static zza<Integer> zzLt;
    public static zza<String> zzLu;
    public static zza<String> zzLv;
    public static zza<Integer> zzLw;
    public static zza<Integer> zzLx;
    public static zza<Integer> zzLy;
    public static zza<Integer> zzLz;

    public static final class zza<V> {
        private final V zzLQ;
        private final zzkf<V> zzLR;
        private V zzLS;

        private zza(zzkf<V> com_google_android_gms_internal_zzkf_V, V v) {
            zzu.zzu(com_google_android_gms_internal_zzkf_V);
            this.zzLR = com_google_android_gms_internal_zzkf_V;
            this.zzLQ = v;
        }

        static zza<Float> zza(String str, float f) {
            return zza(str, f, f);
        }

        static zza<Float> zza(String str, float f, float f2) {
            return new zza(zzkf.zza(str, Float.valueOf(f2)), Float.valueOf(f));
        }

        static zza<Integer> zza(String str, int i, int i2) {
            return new zza(zzkf.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
        }

        static zza<Long> zza(String str, long j, long j2) {
            return new zza(zzkf.zza(str, Long.valueOf(j2)), Long.valueOf(j));
        }

        static zza<Boolean> zza(String str, boolean z, boolean z2) {
            return new zza(zzkf.zzg(str, z2), Boolean.valueOf(z));
        }

        static zza<Long> zzc(String str, long j) {
            return zza(str, j, j);
        }

        static zza<String> zzd(String str, String str2, String str3) {
            return new zza(zzkf.zzs(str, str3), str2);
        }

        static zza<Boolean> zzd(String str, boolean z) {
            return zza(str, z, z);
        }

        static zza<Integer> zze(String str, int i) {
            return zza(str, i, i);
        }

        static zza<String> zzm(String str, String str2) {
            return zzd(str, str2, str2);
        }

        public V get() {
            return this.zzLS != null ? this.zzLS : (zzd.zzZR && zzkf.isInitialized()) ? this.zzLR.zzmZ() : this.zzLQ;
        }
    }

    static {
        zzKZ = zza.zzd("analytics.service_enabled", false);
        zzLa = zza.zzd("analytics.service_client_enabled", true);
        zzLb = zza.zzd("analytics.log_tag", "GAv4", "GAv4-SVC");
        zzLc = zza.zzc("analytics.max_tokens", 60);
        zzLd = zza.zza("analytics.tokens_per_sec", 0.5f);
        zzLe = zza.zza("analytics.max_stored_hits", (int) GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS, 20000);
        zzLf = zza.zze("analytics.max_stored_hits_per_app", GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS);
        zzLg = zza.zze("analytics.max_stored_properties_per_app", 100);
        zzLh = zza.zza("analytics.local_dispatch_millis", 1800000, 120000);
        zzLi = zza.zza("analytics.initial_local_dispatch_millis", 5000, 5000);
        zzLj = zza.zzc("analytics.min_local_dispatch_millis", 120000);
        zzLk = zza.zzc("analytics.max_local_dispatch_millis", 7200000);
        zzLl = zza.zzc("analytics.dispatch_alarm_millis", 7200000);
        zzLm = zza.zzc("analytics.max_dispatch_alarm_millis", 32400000);
        zzLn = zza.zze("analytics.max_hits_per_dispatch", 20);
        zzLo = zza.zze("analytics.max_hits_per_batch", 20);
        zzLp = zza.zzm("analytics.insecure_host", "http://www.google-analytics.com");
        zzLq = zza.zzm("analytics.secure_host", "https://ssl.google-analytics.com");
        zzLr = zza.zzm("analytics.simple_endpoint", "/collect");
        zzLs = zza.zzm("analytics.batching_endpoint", "/batch");
        zzLt = zza.zze("analytics.max_get_length", 2036);
        zzLu = zza.zzd("analytics.batching_strategy.k", zzm.BATCH_BY_COUNT.name(), zzm.BATCH_BY_COUNT.name());
        zzLv = zza.zzm("analytics.compression_strategy.k", zzo.GZIP.name());
        zzLw = zza.zze("analytics.max_hits_per_request.k", 20);
        zzLx = zza.zze("analytics.max_hit_length.k", AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
        zzLy = zza.zze("analytics.max_post_length.k", AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
        zzLz = zza.zze("analytics.max_batch_post_length", AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
        zzLA = zza.zzm("analytics.fallback_responses.k", "404,502");
        zzLB = zza.zze("analytics.batch_retry_interval.seconds.k", 3600);
        zzLC = zza.zzc("analytics.service_monitor_interval", 86400000);
        zzLD = zza.zze("analytics.http_connection.connect_timeout_millis", 60000);
        zzLE = zza.zze("analytics.http_connection.read_timeout_millis", 61000);
        zzLF = zza.zzc("analytics.campaigns.time_limit", 86400000);
        zzLG = zza.zzm("analytics.first_party_experiment_id", "");
        zzLH = zza.zze("analytics.first_party_experiment_variant", 0);
        zzLI = zza.zzd("analytics.test.disable_receiver", false);
        zzLJ = zza.zza("analytics.service_client.idle_disconnect_millis", 10000, 10000);
        zzLK = zza.zzc("analytics.service_client.connect_timeout_millis", 5000);
        zzLL = zza.zzc("analytics.service_client.second_connect_delay_millis", 5000);
        zzLM = zza.zzc("analytics.service_client.unexpected_reconnect_millis", 60000);
        zzLN = zza.zzc("analytics.service_client.reconnect_throttle_millis", 1800000);
        zzLO = zza.zzc("analytics.monitoring.sample_period_millis", 86400000);
        zzLP = zza.zzc("analytics.initialization_warning_threshold", 5000);
    }
}
