package com.google.android.gms.internal;

import android.content.SharedPreferences;
import com.google.android.gms.ads.internal.zzu;

@zzji
public abstract class zzdn<T> {
    private final int zzbcm;
    private final String zzbcn;
    private final T zzbco;

    /* renamed from: com.google.android.gms.internal.zzdn.1 */
    class C12841 extends zzdn<Boolean> {
        C12841(int i, String str, Boolean bool) {
            super(str, bool, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzb(sharedPreferences);
        }

        public Boolean zzb(SharedPreferences sharedPreferences) {
            return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), ((Boolean) zzlp()).booleanValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdn.2 */
    class C12852 extends zzdn<Integer> {
        C12852(int i, String str, Integer num) {
            super(str, num, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzc(sharedPreferences);
        }

        public Integer zzc(SharedPreferences sharedPreferences) {
            return Integer.valueOf(sharedPreferences.getInt(getKey(), ((Integer) zzlp()).intValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdn.3 */
    class C12863 extends zzdn<Long> {
        C12863(int i, String str, Long l) {
            super(str, l, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzd(sharedPreferences);
        }

        public Long zzd(SharedPreferences sharedPreferences) {
            return Long.valueOf(sharedPreferences.getLong(getKey(), ((Long) zzlp()).longValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdn.4 */
    class C12874 extends zzdn<Float> {
        C12874(int i, String str, Float f) {
            super(str, f, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zze(sharedPreferences);
        }

        public Float zze(SharedPreferences sharedPreferences) {
            return Float.valueOf(sharedPreferences.getFloat(getKey(), ((Float) zzlp()).floatValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdn.5 */
    class C12885 extends zzdn<String> {
        C12885(int i, String str, String str2) {
            super(str, str2, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzf(sharedPreferences);
        }

        public String zzf(SharedPreferences sharedPreferences) {
            return sharedPreferences.getString(getKey(), (String) zzlp());
        }
    }

    private zzdn(int i, String str, T t) {
        this.zzbcm = i;
        this.zzbcn = str;
        this.zzbco = t;
        zzu.zzgx().zza(this);
    }

    public static zzdn<String> zza(int i, String str) {
        zzdn<String> zza = zza(i, str, null);
        zzu.zzgx().zzb(zza);
        return zza;
    }

    public static zzdn<Float> zza(int i, String str, float f) {
        return new C12874(i, str, Float.valueOf(f));
    }

    public static zzdn<Integer> zza(int i, String str, int i2) {
        return new C12852(i, str, Integer.valueOf(i2));
    }

    public static zzdn<Long> zza(int i, String str, long j) {
        return new C12863(i, str, Long.valueOf(j));
    }

    public static zzdn<Boolean> zza(int i, String str, Boolean bool) {
        return new C12841(i, str, bool);
    }

    public static zzdn<String> zza(int i, String str, String str2) {
        return new C12885(i, str, str2);
    }

    public static zzdn<String> zzb(int i, String str) {
        zzdn<String> zza = zza(i, str, null);
        zzu.zzgx().zzc(zza);
        return zza;
    }

    public T get() {
        return zzu.zzgy().zzd(this);
    }

    public String getKey() {
        return this.zzbcn;
    }

    protected abstract T zza(SharedPreferences sharedPreferences);

    public T zzlp() {
        return this.zzbco;
    }
}
