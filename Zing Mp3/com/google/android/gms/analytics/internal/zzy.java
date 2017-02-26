package com.google.android.gms.analytics.internal;

import com.facebook.internal.Utility;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.upstream.UdpDataSource;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzsi;
import com.mp3download.zingmp3.BuildConfig;

public final class zzy {
    public static zza<Integer> eA;
    public static zza<String> eB;
    public static zza<String> eC;
    public static zza<String> eD;
    public static zza<String> eE;
    public static zza<Integer> eF;
    public static zza<String> eG;
    public static zza<String> eH;
    public static zza<Integer> eI;
    public static zza<Integer> eJ;
    public static zza<Integer> eK;
    public static zza<Integer> eL;
    public static zza<String> eM;
    public static zza<Integer> eN;
    public static zza<Long> eO;
    public static zza<Integer> eP;
    public static zza<Integer> eQ;
    public static zza<Long> eR;
    public static zza<String> eS;
    public static zza<Integer> eT;
    public static zza<Boolean> eU;
    public static zza<Long> eV;
    public static zza<Long> eW;
    public static zza<Long> eX;
    public static zza<Long> eY;
    public static zza<Long> eZ;
    public static zza<Boolean> el;
    public static zza<Boolean> em;
    public static zza<String> en;
    public static zza<Long> eo;
    public static zza<Float> ep;
    public static zza<Integer> eq;
    public static zza<Integer> er;
    public static zza<Integer> es;
    public static zza<Long> et;
    public static zza<Long> eu;
    public static zza<Long> ev;
    public static zza<Long> ew;
    public static zza<Long> ex;
    public static zza<Long> ey;
    public static zza<Integer> ez;
    public static zza<Long> fa;
    public static zza<Long> fb;

    public static final class zza<V> {
        private final V fc;
        private final zzsi<V> fd;

        private zza(zzsi<V> com_google_android_gms_internal_zzsi_V, V v) {
            zzaa.zzy(com_google_android_gms_internal_zzsi_V);
            this.fd = com_google_android_gms_internal_zzsi_V;
            this.fc = v;
        }

        static zza<Float> zza(String str, float f) {
            return zza(str, f, f);
        }

        static zza<Float> zza(String str, float f, float f2) {
            return new zza(zzsi.zza(str, Float.valueOf(f2)), Float.valueOf(f));
        }

        static zza<Integer> zza(String str, int i, int i2) {
            return new zza(zzsi.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
        }

        static zza<Long> zza(String str, long j, long j2) {
            return new zza(zzsi.zza(str, Long.valueOf(j2)), Long.valueOf(j));
        }

        static zza<Boolean> zza(String str, boolean z, boolean z2) {
            return new zza(zzsi.zzk(str, z2), Boolean.valueOf(z));
        }

        static zza<Long> zzb(String str, long j) {
            return zza(str, j, j);
        }

        static zza<String> zzd(String str, String str2, String str3) {
            return new zza(zzsi.zzaa(str, str3), str2);
        }

        static zza<Integer> zze(String str, int i) {
            return zza(str, i, i);
        }

        static zza<Boolean> zzf(String str, boolean z) {
            return zza(str, z, z);
        }

        static zza<String> zzq(String str, String str2) {
            return zzd(str, str2, str2);
        }

        public V get() {
            return this.fc;
        }
    }

    static {
        el = zza.zzf("analytics.service_enabled", false);
        em = zza.zzf("analytics.service_client_enabled", true);
        en = zza.zzd("analytics.log_tag", "GAv4", "GAv4-SVC");
        eo = zza.zzb("analytics.max_tokens", 60);
        ep = zza.zza("analytics.tokens_per_sec", 0.5f);
        eq = zza.zza("analytics.max_stored_hits", (int) UdpDataSource.DEFAULT_MAX_PACKET_SIZE, 20000);
        er = zza.zze("analytics.max_stored_hits_per_app", UdpDataSource.DEFAULT_MAX_PACKET_SIZE);
        es = zza.zze("analytics.max_stored_properties_per_app", 100);
        et = zza.zza("analytics.local_dispatch_millis", 1800000, 120000);
        eu = zza.zza("analytics.initial_local_dispatch_millis", (long) HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS, (long) HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS);
        ev = zza.zzb("analytics.min_local_dispatch_millis", 120000);
        ew = zza.zzb("analytics.max_local_dispatch_millis", 7200000);
        ex = zza.zzb("analytics.dispatch_alarm_millis", 7200000);
        ey = zza.zzb("analytics.max_dispatch_alarm_millis", 32400000);
        ez = zza.zze("analytics.max_hits_per_dispatch", 20);
        eA = zza.zze("analytics.max_hits_per_batch", 20);
        eB = zza.zzq("analytics.insecure_host", "http://www.google-analytics.com");
        eC = zza.zzq("analytics.secure_host", "https://ssl.google-analytics.com");
        eD = zza.zzq("analytics.simple_endpoint", "/collect");
        eE = zza.zzq("analytics.batching_endpoint", "/batch");
        eF = zza.zze("analytics.max_get_length", 2036);
        eG = zza.zzd("analytics.batching_strategy.k", zzm.BATCH_BY_COUNT.name(), zzm.BATCH_BY_COUNT.name());
        eH = zza.zzq("analytics.compression_strategy.k", zzo.GZIP.name());
        eI = zza.zze("analytics.max_hits_per_request.k", 20);
        eJ = zza.zze("analytics.max_hit_length.k", Utility.DEFAULT_STREAM_BUFFER_SIZE);
        eK = zza.zze("analytics.max_post_length.k", Utility.DEFAULT_STREAM_BUFFER_SIZE);
        eL = zza.zze("analytics.max_batch_post_length", Utility.DEFAULT_STREAM_BUFFER_SIZE);
        eM = zza.zzq("analytics.fallback_responses.k", "404,502");
        eN = zza.zze("analytics.batch_retry_interval.seconds.k", 3600);
        eO = zza.zzb("analytics.service_monitor_interval", 86400000);
        eP = zza.zze("analytics.http_connection.connect_timeout_millis", 60000);
        eQ = zza.zze("analytics.http_connection.read_timeout_millis", 61000);
        eR = zza.zzb("analytics.campaigns.time_limit", 86400000);
        eS = zza.zzq("analytics.first_party_experiment_id", BuildConfig.FLAVOR);
        eT = zza.zze("analytics.first_party_experiment_variant", 0);
        eU = zza.zzf("analytics.test.disable_receiver", false);
        eV = zza.zza("analytics.service_client.idle_disconnect_millis", 10000, 10000);
        eW = zza.zzb("analytics.service_client.connect_timeout_millis", HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS);
        eX = zza.zzb("analytics.service_client.second_connect_delay_millis", HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS);
        eY = zza.zzb("analytics.service_client.unexpected_reconnect_millis", HlsChunkSource.DEFAULT_PLAYLIST_BLACKLIST_MS);
        eZ = zza.zzb("analytics.service_client.reconnect_throttle_millis", 1800000);
        fa = zza.zzb("analytics.monitoring.sample_period_millis", 86400000);
        fb = zza.zzb("analytics.initialization_warning_threshold", HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS);
    }
}
