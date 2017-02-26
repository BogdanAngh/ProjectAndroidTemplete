package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import java.util.concurrent.Future;

@zzgd
public final class zzhj {

    public interface zzb {
        void zzc(Bundle bundle);
    }

    private static abstract class zza extends zzhh {
        private zza() {
        }

        public void onStop() {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhj.1 */
    static class C08041 extends zza {
        final /* synthetic */ boolean zzGb;
        final /* synthetic */ Context zzqV;

        C08041(Context context, boolean z) {
            this.zzqV = context;
            this.zzGb = z;
            super();
        }

        public void zzdP() {
            Editor edit = zzhj.zzv(this.zzqV).edit();
            edit.putBoolean("use_https", this.zzGb);
            edit.commit();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhj.2 */
    static class C08052 extends zza {
        final /* synthetic */ zzb zzGc;
        final /* synthetic */ Context zzqV;

        C08052(Context context, zzb com_google_android_gms_internal_zzhj_zzb) {
            this.zzqV = context;
            this.zzGc = com_google_android_gms_internal_zzhj_zzb;
            super();
        }

        public void zzdP() {
            SharedPreferences zzF = zzhj.zzv(this.zzqV);
            Bundle bundle = new Bundle();
            bundle.putBoolean("use_https", zzF.getBoolean("use_https", true));
            if (this.zzGc != null) {
                this.zzGc.zzc(bundle);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhj.3 */
    static class C08063 extends zza {
        final /* synthetic */ int zzGd;
        final /* synthetic */ Context zzqV;

        C08063(Context context, int i) {
            this.zzqV = context;
            this.zzGd = i;
            super();
        }

        public void zzdP() {
            Editor edit = zzhj.zzv(this.zzqV).edit();
            edit.putInt("webview_cache_version", this.zzGd);
            edit.commit();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhj.4 */
    static class C08074 extends zza {
        final /* synthetic */ zzb zzGc;
        final /* synthetic */ Context zzqV;

        C08074(Context context, zzb com_google_android_gms_internal_zzhj_zzb) {
            this.zzqV = context;
            this.zzGc = com_google_android_gms_internal_zzhj_zzb;
            super();
        }

        public void zzdP() {
            SharedPreferences zzF = zzhj.zzv(this.zzqV);
            Bundle bundle = new Bundle();
            bundle.putInt("webview_cache_version", zzF.getInt("webview_cache_version", 0));
            if (this.zzGc != null) {
                this.zzGc.zzc(bundle);
            }
        }
    }

    public static Future zza(Context context, int i) {
        return new C08063(context, i).zzgi();
    }

    public static Future zza(Context context, zzb com_google_android_gms_internal_zzhj_zzb) {
        return new C08052(context, com_google_android_gms_internal_zzhj_zzb).zzgi();
    }

    public static Future zza(Context context, boolean z) {
        return new C08041(context, z).zzgi();
    }

    public static Future zzb(Context context, zzb com_google_android_gms_internal_zzhj_zzb) {
        return new C08074(context, com_google_android_gms_internal_zzhj_zzb).zzgi();
    }

    private static SharedPreferences zzv(Context context) {
        return context.getSharedPreferences("admob", 0);
    }
}
