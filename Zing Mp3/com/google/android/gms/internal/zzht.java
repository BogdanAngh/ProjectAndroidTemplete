package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import org.json.JSONObject;

@zzji
public class zzht {
    private final boolean zzbyw;
    private final boolean zzbyx;
    private final boolean zzbyy;
    private final boolean zzbyz;
    private final boolean zzbza;

    public static final class zza {
        private boolean zzbyw;
        private boolean zzbyx;
        private boolean zzbyy;
        private boolean zzbyz;
        private boolean zzbza;

        public zzht zzow() {
            return new zzht();
        }

        public zza zzu(boolean z) {
            this.zzbyw = z;
            return this;
        }

        public zza zzv(boolean z) {
            this.zzbyx = z;
            return this;
        }

        public zza zzw(boolean z) {
            this.zzbyy = z;
            return this;
        }

        public zza zzx(boolean z) {
            this.zzbyz = z;
            return this;
        }

        public zza zzy(boolean z) {
            this.zzbza = z;
            return this;
        }
    }

    private zzht(zza com_google_android_gms_internal_zzht_zza) {
        this.zzbyw = com_google_android_gms_internal_zzht_zza.zzbyw;
        this.zzbyx = com_google_android_gms_internal_zzht_zza.zzbyx;
        this.zzbyy = com_google_android_gms_internal_zzht_zza.zzbyy;
        this.zzbyz = com_google_android_gms_internal_zzht_zza.zzbyz;
        this.zzbza = com_google_android_gms_internal_zzht_zza.zzbza;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject().put("sms", this.zzbyw).put("tel", this.zzbyx).put("calendar", this.zzbyy).put("storePicture", this.zzbyz).put("inlineVideo", this.zzbza);
        } catch (Throwable e) {
            zzb.zzb("Error occured while obtaining the MRAID capabilities.", e);
            return null;
        }
    }
}
