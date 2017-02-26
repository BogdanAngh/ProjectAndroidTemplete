package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

@zzji
public class zzli {
    private static zzl zzcws;
    private static final Object zzcwt;
    public static final zza<Void> zzcwu;

    public interface zza<T> {
        T zzh(InputStream inputStream);

        T zzsw();
    }

    /* renamed from: com.google.android.gms.internal.zzli.1 */
    class C14541 implements zza<Void> {
        C14541() {
        }

        public /* bridge */ /* synthetic */ Object zzh(InputStream inputStream) {
            return null;
        }

        public /* bridge */ /* synthetic */ Object zzsw() {
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzli.2 */
    class C14552 implements com.google.android.gms.internal.zzm.zza {
        final /* synthetic */ String zzall;
        final /* synthetic */ zzc zzcwv;
        final /* synthetic */ zzli zzcww;

        C14552(zzli com_google_android_gms_internal_zzli, String str, zzc com_google_android_gms_internal_zzli_zzc) {
            this.zzcww = com_google_android_gms_internal_zzli;
            this.zzall = str;
            this.zzcwv = com_google_android_gms_internal_zzli_zzc;
        }

        public void zze(zzr com_google_android_gms_internal_zzr) {
            String str = this.zzall;
            String valueOf = String.valueOf(com_google_android_gms_internal_zzr.toString());
            com.google.android.gms.ads.internal.util.client.zzb.zzdi(new StringBuilder((String.valueOf(str).length() + 21) + String.valueOf(valueOf).length()).append("Failed to load URL: ").append(str).append("\n").append(valueOf).toString());
            this.zzcwv.zzb(null);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzli.3 */
    class C14563 extends zzab {
        final /* synthetic */ zzli zzcww;
        final /* synthetic */ byte[] zzcwx;
        final /* synthetic */ Map zzcwy;

        C14563(zzli com_google_android_gms_internal_zzli, int i, String str, com.google.android.gms.internal.zzm.zzb com_google_android_gms_internal_zzm_zzb, com.google.android.gms.internal.zzm.zza com_google_android_gms_internal_zzm_zza, byte[] bArr, Map map) {
            this.zzcww = com_google_android_gms_internal_zzli;
            this.zzcwx = bArr;
            this.zzcwy = map;
            super(i, str, com_google_android_gms_internal_zzm_zzb, com_google_android_gms_internal_zzm_zza);
        }

        public Map<String, String> getHeaders() throws zza {
            return this.zzcwy == null ? super.getHeaders() : this.zzcwy;
        }

        public byte[] zzo() throws zza {
            return this.zzcwx == null ? super.zzo() : this.zzcwx;
        }
    }

    private static class zzb<T> extends zzk<InputStream> {
        private final com.google.android.gms.internal.zzm.zzb<T> zzcg;
        private final zza<T> zzcwz;

        /* renamed from: com.google.android.gms.internal.zzli.zzb.1 */
        class C14571 implements com.google.android.gms.internal.zzm.zza {
            final /* synthetic */ com.google.android.gms.internal.zzm.zzb zzcxa;
            final /* synthetic */ zza zzcxb;

            C14571(com.google.android.gms.internal.zzm.zzb com_google_android_gms_internal_zzm_zzb, zza com_google_android_gms_internal_zzli_zza) {
                this.zzcxa = com_google_android_gms_internal_zzm_zzb;
                this.zzcxb = com_google_android_gms_internal_zzli_zza;
            }

            public void zze(zzr com_google_android_gms_internal_zzr) {
                this.zzcxa.zzb(this.zzcxb.zzsw());
            }
        }

        public zzb(String str, zza<T> com_google_android_gms_internal_zzli_zza_T, com.google.android.gms.internal.zzm.zzb<T> com_google_android_gms_internal_zzm_zzb_T) {
            super(0, str, new C14571(com_google_android_gms_internal_zzm_zzb_T, com_google_android_gms_internal_zzli_zza_T));
            this.zzcwz = com_google_android_gms_internal_zzli_zza_T;
            this.zzcg = com_google_android_gms_internal_zzm_zzb_T;
        }

        protected zzm<InputStream> zza(zzi com_google_android_gms_internal_zzi) {
            return zzm.zza(new ByteArrayInputStream(com_google_android_gms_internal_zzi.data), zzx.zzb(com_google_android_gms_internal_zzi));
        }

        protected /* synthetic */ void zza(Object obj) {
            zzj((InputStream) obj);
        }

        protected void zzj(InputStream inputStream) {
            this.zzcg.zzb(this.zzcwz.zzh(inputStream));
        }
    }

    private class zzc<T> extends zzlq<T> implements com.google.android.gms.internal.zzm.zzb<T> {
        final /* synthetic */ zzli zzcww;

        private zzc(zzli com_google_android_gms_internal_zzli) {
            this.zzcww = com_google_android_gms_internal_zzli;
        }

        public void zzb(@Nullable T t) {
            super.zzh(t);
        }
    }

    static {
        zzcwt = new Object();
        zzcwu = new C14541();
    }

    public zzli(Context context) {
        zzan(context);
    }

    private static zzl zzan(Context context) {
        zzl com_google_android_gms_internal_zzl;
        synchronized (zzcwt) {
            if (zzcws == null) {
                zzcws = zzac.zza(context.getApplicationContext());
            }
            com_google_android_gms_internal_zzl = zzcws;
        }
        return com_google_android_gms_internal_zzl;
    }

    public zzlt<String> zza(int i, String str, @Nullable Map<String, String> map, @Nullable byte[] bArr) {
        Object com_google_android_gms_internal_zzli_zzc = new zzc();
        zzcws.zze(new C14563(this, i, str, com_google_android_gms_internal_zzli_zzc, new C14552(this, str, com_google_android_gms_internal_zzli_zzc), bArr, map));
        return com_google_android_gms_internal_zzli_zzc;
    }

    public <T> zzlt<T> zza(String str, zza<T> com_google_android_gms_internal_zzli_zza_T) {
        Object com_google_android_gms_internal_zzli_zzc = new zzc();
        zzcws.zze(new zzb(str, com_google_android_gms_internal_zzli_zza_T, com_google_android_gms_internal_zzli_zzc));
        return com_google_android_gms_internal_zzli_zzc;
    }

    public zzlt<String> zzd(String str, Map<String, String> map) {
        return zza(0, str, map, null);
    }
}
