package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.mp3download.zingmp3.BuildConfig;
import java.util.concurrent.Future;

@zzji
public final class zzkz {

    public interface zzb {
        void zzh(Bundle bundle);
    }

    private static abstract class zza extends zzkw {
        private zza() {
        }

        public void onStop() {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.10 */
    class AnonymousClass10 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzb zzcux;

        AnonymousClass10(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
            this.zzang = context;
            this.zzcux = com_google_android_gms_internal_zzkz_zzb;
            super();
        }

        public void zzfp() {
            SharedPreferences zzm = zzkz.zzm(this.zzang);
            Bundle bundle = new Bundle();
            bundle.putBoolean("use_https", zzm.getBoolean("use_https", true));
            if (this.zzcux != null) {
                this.zzcux.zzh(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.11 */
    class AnonymousClass11 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzb zzcux;

        AnonymousClass11(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
            this.zzang = context;
            this.zzcux = com_google_android_gms_internal_zzkz_zzb;
            super();
        }

        public void zzfp() {
            SharedPreferences zzm = zzkz.zzm(this.zzang);
            Bundle bundle = new Bundle();
            bundle.putInt("webview_cache_version", zzm.getInt("webview_cache_version", 0));
            if (this.zzcux != null) {
                this.zzcux.zzh(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.12 */
    class AnonymousClass12 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ boolean zzcvc;

        AnonymousClass12(Context context, boolean z) {
            this.zzang = context;
            this.zzcvc = z;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putBoolean("content_url_opted_out", this.zzcvc);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.13 */
    class AnonymousClass13 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzb zzcux;

        AnonymousClass13(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
            this.zzang = context;
            this.zzcux = com_google_android_gms_internal_zzkz_zzb;
            super();
        }

        public void zzfp() {
            SharedPreferences zzm = zzkz.zzm(this.zzang);
            Bundle bundle = new Bundle();
            bundle.putBoolean("content_url_opted_out", zzm.getBoolean("content_url_opted_out", true));
            if (this.zzcux != null) {
                this.zzcux.zzh(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.14 */
    class AnonymousClass14 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ String zzcuv;

        AnonymousClass14(Context context, String str) {
            this.zzang = context;
            this.zzcuv = str;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putString("content_url_hashes", this.zzcuv);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.15 */
    class AnonymousClass15 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzb zzcux;

        AnonymousClass15(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
            this.zzang = context;
            this.zzcux = com_google_android_gms_internal_zzkz_zzb;
            super();
        }

        public void zzfp() {
            SharedPreferences zzm = zzkz.zzm(this.zzang);
            Bundle bundle = new Bundle();
            bundle.putString("content_url_hashes", zzm.getString("content_url_hashes", BuildConfig.FLAVOR));
            if (this.zzcux != null) {
                this.zzcux.zzh(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.1 */
    class C14281 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ boolean zzcuu;

        C14281(Context context, boolean z) {
            this.zzang = context;
            this.zzcuu = z;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putBoolean("use_https", this.zzcuu);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.2 */
    class C14292 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ String zzcuv;

        C14292(Context context, String str) {
            this.zzang = context;
            this.zzcuv = str;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putString("content_vertical_hashes", this.zzcuv);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.3 */
    class C14303 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ boolean zzcuw;

        C14303(Context context, boolean z) {
            this.zzang = context;
            this.zzcuw = z;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putBoolean("auto_collect_location", this.zzcuw);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.4 */
    class C14314 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzb zzcux;

        C14314(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
            this.zzang = context;
            this.zzcux = com_google_android_gms_internal_zzkz_zzb;
            super();
        }

        public void zzfp() {
            SharedPreferences zzm = zzkz.zzm(this.zzang);
            Bundle bundle = new Bundle();
            bundle.putBoolean("auto_collect_location", zzm.getBoolean("auto_collect_location", false));
            if (this.zzcux != null) {
                this.zzcux.zzh(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.5 */
    class C14325 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ String zzcuy;
        final /* synthetic */ long zzcuz;

        C14325(Context context, String str, long j) {
            this.zzang = context;
            this.zzcuy = str;
            this.zzcuz = j;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putString("app_settings_json", this.zzcuy);
            edit.putLong("app_settings_last_update_ms", this.zzcuz);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.6 */
    class C14336 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzb zzcux;

        C14336(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
            this.zzang = context;
            this.zzcux = com_google_android_gms_internal_zzkz_zzb;
            super();
        }

        public void zzfp() {
            SharedPreferences zzm = zzkz.zzm(this.zzang);
            Bundle bundle = new Bundle();
            bundle.putString("app_settings_json", zzm.getString("app_settings_json", BuildConfig.FLAVOR));
            bundle.putLong("app_settings_last_update_ms", zzm.getLong("app_settings_last_update_ms", 0));
            if (this.zzcux != null) {
                this.zzcux.zzh(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.7 */
    class C14347 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ long zzcva;

        C14347(Context context, long j) {
            this.zzang = context;
            this.zzcva = j;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putLong("app_last_background_time_ms", this.zzcva);
            edit.apply();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.8 */
    class C14358 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzb zzcux;

        C14358(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
            this.zzang = context;
            this.zzcux = com_google_android_gms_internal_zzkz_zzb;
            super();
        }

        public void zzfp() {
            SharedPreferences zzm = zzkz.zzm(this.zzang);
            Bundle bundle = new Bundle();
            bundle.putLong("app_last_background_time_ms", zzm.getLong("app_last_background_time_ms", 0));
            if (this.zzcux != null) {
                this.zzcux.zzh(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkz.9 */
    class C14369 extends zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ int zzcvb;

        C14369(Context context, int i) {
            this.zzang = context;
            this.zzcvb = i;
            super();
        }

        public void zzfp() {
            Editor edit = zzkz.zzm(this.zzang).edit();
            edit.putInt("request_in_session_count", this.zzcvb);
            edit.apply();
        }
    }

    public static Future zza(Context context, int i) {
        return (Future) new C14369(context, i).zzrz();
    }

    public static Future zza(Context context, long j) {
        return (Future) new C14347(context, j).zzrz();
    }

    public static Future zza(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
        return (Future) new AnonymousClass10(context, com_google_android_gms_internal_zzkz_zzb).zzrz();
    }

    public static Future zza(Context context, String str, long j) {
        return (Future) new C14325(context, str, j).zzrz();
    }

    public static Future zzb(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
        return (Future) new AnonymousClass11(context, com_google_android_gms_internal_zzkz_zzb).zzrz();
    }

    public static Future zzc(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
        return (Future) new AnonymousClass13(context, com_google_android_gms_internal_zzkz_zzb).zzrz();
    }

    public static Future zzc(Context context, boolean z) {
        return (Future) new C14281(context, z).zzrz();
    }

    public static Future zzd(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
        return (Future) new AnonymousClass15(context, com_google_android_gms_internal_zzkz_zzb).zzrz();
    }

    public static Future zze(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
        return (Future) new C14314(context, com_google_android_gms_internal_zzkz_zzb).zzrz();
    }

    public static Future zze(Context context, boolean z) {
        return (Future) new AnonymousClass12(context, z).zzrz();
    }

    public static Future zzf(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
        return (Future) new C14336(context, com_google_android_gms_internal_zzkz_zzb).zzrz();
    }

    public static Future zzf(Context context, String str) {
        return (Future) new AnonymousClass14(context, str).zzrz();
    }

    public static Future zzf(Context context, boolean z) {
        return (Future) new C14303(context, z).zzrz();
    }

    public static Future zzg(Context context, zzb com_google_android_gms_internal_zzkz_zzb) {
        return (Future) new C14358(context, com_google_android_gms_internal_zzkz_zzb).zzrz();
    }

    public static Future zzg(Context context, String str) {
        return (Future) new C14292(context, str).zzrz();
    }

    public static SharedPreferences zzm(Context context) {
        return context.getSharedPreferences("admob", 0);
    }
}
