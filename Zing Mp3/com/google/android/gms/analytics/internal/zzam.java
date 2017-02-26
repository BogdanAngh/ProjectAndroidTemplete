package com.google.android.gms.analytics.internal;

public class zzam extends zzq<zzan> {

    private static class zza extends zzc implements com.google.android.gms.analytics.internal.zzq.zza<zzan> {
        private final zzan fP;

        public zza(zzf com_google_android_gms_analytics_internal_zzf) {
            super(com_google_android_gms_analytics_internal_zzf);
            this.fP = new zzan();
        }

        public /* synthetic */ zzp zzaee() {
            return zzahb();
        }

        public zzan zzahb() {
            return this.fP;
        }

        public void zzd(String str, int i) {
            if ("ga_sessionTimeout".equals(str)) {
                this.fP.fR = i;
            } else {
                zzd("int configuration name not recognized", str);
            }
        }

        public void zze(String str, boolean z) {
            int i = 1;
            zzan com_google_android_gms_analytics_internal_zzan;
            if ("ga_autoActivityTracking".equals(str)) {
                com_google_android_gms_analytics_internal_zzan = this.fP;
                if (!z) {
                    i = 0;
                }
                com_google_android_gms_analytics_internal_zzan.fS = i;
            } else if ("ga_anonymizeIp".equals(str)) {
                com_google_android_gms_analytics_internal_zzan = this.fP;
                if (!z) {
                    i = 0;
                }
                com_google_android_gms_analytics_internal_zzan.fT = i;
            } else if ("ga_reportUncaughtExceptions".equals(str)) {
                com_google_android_gms_analytics_internal_zzan = this.fP;
                if (!z) {
                    i = 0;
                }
                com_google_android_gms_analytics_internal_zzan.fU = i;
            } else {
                zzd("bool configuration name not recognized", str);
            }
        }

        public void zzo(String str, String str2) {
            this.fP.fV.put(str, str2);
        }

        public void zzp(String str, String str2) {
            if ("ga_trackingId".equals(str)) {
                this.fP.at = str2;
            } else if ("ga_sampleFrequency".equals(str)) {
                try {
                    this.fP.fQ = Double.parseDouble(str2);
                } catch (NumberFormatException e) {
                    zzc("Error parsing ga_sampleFrequency value", str2, e);
                }
            } else {
                zzd("string configuration name not recognized", str);
            }
        }
    }

    public zzam(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf, new zza(com_google_android_gms_analytics_internal_zzf));
    }
}
