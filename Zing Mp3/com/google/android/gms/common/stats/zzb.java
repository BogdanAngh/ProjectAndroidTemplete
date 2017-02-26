package com.google.android.gms.common.stats;

import com.google.android.gms.internal.zzsi;
import com.mp3download.zingmp3.BuildConfig;

public final class zzb {
    public static zzsi<Integer> FH;
    public static zzsi<Integer> FI;

    public static final class zza {
        public static zzsi<Integer> FJ;
        public static zzsi<String> FK;
        public static zzsi<String> FL;
        public static zzsi<String> FM;
        public static zzsi<String> FN;
        public static zzsi<Long> FO;

        static {
            FJ = zzsi.zza("gms:common:stats:connections:level", Integer.valueOf(zzc.LOG_LEVEL_OFF));
            FK = zzsi.zzaa("gms:common:stats:connections:ignored_calling_processes", BuildConfig.FLAVOR);
            FL = zzsi.zzaa("gms:common:stats:connections:ignored_calling_services", BuildConfig.FLAVOR);
            FM = zzsi.zzaa("gms:common:stats:connections:ignored_target_processes", BuildConfig.FLAVOR);
            FN = zzsi.zzaa("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
            FO = zzsi.zza("gms:common:stats:connections:time_out_duration", Long.valueOf(600000));
        }
    }

    public static final class zzb {
        public static zzsi<Integer> FJ;
        public static zzsi<Long> FO;

        static {
            FJ = zzsi.zza("gms:common:stats:wakeLocks:level", Integer.valueOf(zzc.LOG_LEVEL_OFF));
            FO = zzsi.zza("gms:common:stats:wakelocks:time_out_duration", Long.valueOf(600000));
        }
    }

    static {
        FH = zzsi.zza("gms:common:stats:max_num_of_events", Integer.valueOf(100));
        FI = zzsi.zza("gms:common:stats:max_chunk_size", Integer.valueOf(100));
    }
}
