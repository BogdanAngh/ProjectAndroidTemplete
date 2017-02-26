package com.google.android.gms.analytics.internal;

public class zzz extends zzq<zzaa> {

    private static class zza implements com.google.android.gms.analytics.internal.zzq.zza<zzaa> {
        private final zzf cQ;
        private final zzaa fe;

        public zza(zzf com_google_android_gms_analytics_internal_zzf) {
            this.cQ = com_google_android_gms_analytics_internal_zzf;
            this.fe = new zzaa();
        }

        public /* synthetic */ zzp zzaee() {
            return zzafp();
        }

        public zzaa zzafp() {
            return this.fe;
        }

        public void zzd(String str, int i) {
            if ("ga_dispatchPeriod".equals(str)) {
                this.fe.fg = i;
            } else {
                this.cQ.zzaca().zzd("Int xml configuration name not recognized", str);
            }
        }

        public void zze(String str, boolean z) {
            if ("ga_dryRun".equals(str)) {
                this.fe.fh = z ? 1 : 0;
                return;
            }
            this.cQ.zzaca().zzd("Bool xml configuration name not recognized", str);
        }

        public void zzo(String str, String str2) {
        }

        public void zzp(String str, String str2) {
            if ("ga_appName".equals(str)) {
                this.fe.bN = str2;
            } else if ("ga_appVersion".equals(str)) {
                this.fe.bO = str2;
            } else if ("ga_logLevel".equals(str)) {
                this.fe.ff = str2;
            } else {
                this.cQ.zzaca().zzd("String xml configuration name not recognized", str);
            }
        }
    }

    public zzz(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf, new zza(com_google_android_gms_analytics_internal_zzf));
    }
}
