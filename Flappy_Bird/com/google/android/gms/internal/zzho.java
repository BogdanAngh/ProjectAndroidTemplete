package com.google.android.gms.internal;

import android.content.Context;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

@zzgd
public class zzho {
    private static zzl zzGu;
    public static final zza<Void> zzGv;
    private static final Object zzoW;

    public interface zza<T> {
        T zzft();

        T zzh(InputStream inputStream);
    }

    /* renamed from: com.google.android.gms.internal.zzho.1 */
    static class C04751 implements zza {
        C04751() {
        }

        public /* synthetic */ Object zzft() {
            return zzgu();
        }

        public Void zzgu() {
            return null;
        }

        public /* synthetic */ Object zzh(InputStream inputStream) {
            return zzi(inputStream);
        }

        public Void zzi(InputStream inputStream) {
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzho.2 */
    class C04762 implements com.google.android.gms.internal.zzm.zza {
        final /* synthetic */ zzc zzGw;
        final /* synthetic */ zzho zzGx;
        final /* synthetic */ String zzwJ;

        C04762(zzho com_google_android_gms_internal_zzho, String str, zzc com_google_android_gms_internal_zzho_zzc) {
            this.zzGx = com_google_android_gms_internal_zzho;
            this.zzwJ = str;
            this.zzGw = com_google_android_gms_internal_zzho_zzc;
        }

        public void zze(zzr com_google_android_gms_internal_zzr) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Failed to load URL: " + this.zzwJ + "\n" + com_google_android_gms_internal_zzr.toString());
            this.zzGw.zzb(null);
        }
    }

    private static class zzb<T> extends zzk<InputStream> {
        private final zza<T> zzGz;
        private final com.google.android.gms.internal.zzm.zzb<T> zzaH;

        /* renamed from: com.google.android.gms.internal.zzho.zzb.1 */
        class C04771 implements com.google.android.gms.internal.zzm.zza {
            final /* synthetic */ com.google.android.gms.internal.zzm.zzb zzGA;
            final /* synthetic */ zza zzGB;

            C04771(com.google.android.gms.internal.zzm.zzb com_google_android_gms_internal_zzm_zzb, zza com_google_android_gms_internal_zzho_zza) {
                this.zzGA = com_google_android_gms_internal_zzm_zzb;
                this.zzGB = com_google_android_gms_internal_zzho_zza;
            }

            public void zze(zzr com_google_android_gms_internal_zzr) {
                this.zzGA.zzb(this.zzGB.zzft());
            }
        }

        public zzb(String str, zza<T> com_google_android_gms_internal_zzho_zza_T, com.google.android.gms.internal.zzm.zzb<T> com_google_android_gms_internal_zzm_zzb_T) {
            super(0, str, new C04771(com_google_android_gms_internal_zzm_zzb_T, com_google_android_gms_internal_zzho_zza_T));
            this.zzGz = com_google_android_gms_internal_zzho_zza_T;
            this.zzaH = com_google_android_gms_internal_zzm_zzb_T;
        }

        protected zzm<InputStream> zza(zzi com_google_android_gms_internal_zzi) {
            return zzm.zza(new ByteArrayInputStream(com_google_android_gms_internal_zzi.data), zzx.zzb(com_google_android_gms_internal_zzi));
        }

        protected /* synthetic */ void zza(Object obj) {
            zzj((InputStream) obj);
        }

        protected void zzj(InputStream inputStream) {
            this.zzaH.zzb(this.zzGz.zzh(inputStream));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzho.3 */
    class C08083 extends zzab {
        final /* synthetic */ zzho zzGx;
        final /* synthetic */ Map zzGy;

        C08083(zzho com_google_android_gms_internal_zzho, String str, com.google.android.gms.internal.zzm.zzb com_google_android_gms_internal_zzm_zzb, com.google.android.gms.internal.zzm.zza com_google_android_gms_internal_zzm_zza, Map map) {
            this.zzGx = com_google_android_gms_internal_zzho;
            this.zzGy = map;
            super(str, com_google_android_gms_internal_zzm_zzb, com_google_android_gms_internal_zzm_zza);
        }

        public Map<String, String> getHeaders() throws zza {
            return this.zzGy == null ? super.getHeaders() : this.zzGy;
        }
    }

    private class zzc<T> extends zzhs<T> implements com.google.android.gms.internal.zzm.zzb<T> {
        final /* synthetic */ zzho zzGx;

        private zzc(zzho com_google_android_gms_internal_zzho) {
            this.zzGx = com_google_android_gms_internal_zzho;
        }

        public void zzb(T t) {
            super.zzf(t);
        }
    }

    static {
        zzoW = new Object();
        zzGv = new C04751();
    }

    public zzho(Context context) {
        zzGu = zzN(context);
    }

    private static zzl zzN(Context context) {
        zzl com_google_android_gms_internal_zzl;
        synchronized (zzoW) {
            if (zzGu == null) {
                zzGu = zzac.zza(context.getApplicationContext());
            }
            com_google_android_gms_internal_zzl = zzGu;
        }
        return com_google_android_gms_internal_zzl;
    }

    public <T> zzhv<T> zza(String str, zza<T> com_google_android_gms_internal_zzho_zza_T) {
        Object com_google_android_gms_internal_zzho_zzc = new zzc();
        zzGu.zze(new zzb(str, com_google_android_gms_internal_zzho_zza_T, com_google_android_gms_internal_zzho_zzc));
        return com_google_android_gms_internal_zzho_zzc;
    }

    public zzhv<String> zzb(String str, Map<String, String> map) {
        Object com_google_android_gms_internal_zzho_zzc = new zzc();
        zzGu.zze(new C08083(this, str, com_google_android_gms_internal_zzho_zzc, new C04762(this, str, com_google_android_gms_internal_zzho_zzc), map));
        return com_google_android_gms_internal_zzho_zzc;
    }
}
