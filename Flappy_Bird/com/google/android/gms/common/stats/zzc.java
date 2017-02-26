package com.google.android.gms.common.stats;

import com.google.android.gms.internal.zzkf;

public final class zzc {
    public static zzkf<Boolean> zzacr;
    public static zzkf<Integer> zzacs;

    public static final class zza {
        public static zzkf<Integer> zzact;
        public static zzkf<String> zzacu;
        public static zzkf<String> zzacv;
        public static zzkf<String> zzacw;
        public static zzkf<String> zzacx;
        public static zzkf<Long> zzacy;

        static {
            zzact = zzkf.zza("gms:common:stats:connections:level", Integer.valueOf(zzd.zzacz));
            zzacu = zzkf.zzs("gms:common:stats:connections:ignored_calling_processes", "");
            zzacv = zzkf.zzs("gms:common:stats:connections:ignored_calling_services", "");
            zzacw = zzkf.zzs("gms:common:stats:connections:ignored_target_processes", "");
            zzacx = zzkf.zzs("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
            zzacy = zzkf.zza("gms:common:stats:connections:time_out_duration", Long.valueOf(600000));
        }
    }

    static {
        zzacr = zzkf.zzg("gms:common:stats:debug", false);
        zzacs = zzkf.zza("gms:common:stats:max_num_of_events", Integer.valueOf(100));
    }
}
