package com.google.android.gms.internal;

import android.content.SharedPreferences;
import com.google.android.gms.ads.internal.zzo;

@zzgd
public abstract class zzbv<T> implements zzbs {
    private final String zztw;
    private final T zztx;

    /* renamed from: com.google.android.gms.internal.zzbv.1 */
    static class C08001 extends zzbv<Boolean> {
        C08001(String str, Boolean bool) {
            super(bool, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzb(sharedPreferences);
        }

        public Boolean zzb(SharedPreferences sharedPreferences) {
            return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), ((Boolean) zzcY()).booleanValue()));
        }

        public zzkf<Boolean> zzcZ() {
            return zzkf.zzg(getKey(), ((Boolean) zzcY()).booleanValue());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbv.2 */
    static class C08012 extends zzbv<Integer> {
        C08012(String str, Integer num) {
            super(num, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzc(sharedPreferences);
        }

        public Integer zzc(SharedPreferences sharedPreferences) {
            return Integer.valueOf(sharedPreferences.getInt(getKey(), ((Integer) zzcY()).intValue()));
        }

        public zzkf<Integer> zzcZ() {
            return zzkf.zza(getKey(), (Integer) zzcY());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbv.3 */
    static class C08023 extends zzbv<Long> {
        C08023(String str, Long l) {
            super(l, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zzd(sharedPreferences);
        }

        public zzkf<Long> zzcZ() {
            return zzkf.zza(getKey(), (Long) zzcY());
        }

        public Long zzd(SharedPreferences sharedPreferences) {
            return Long.valueOf(sharedPreferences.getLong(getKey(), ((Long) zzcY()).longValue()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbv.4 */
    static class C08034 extends zzbv<String> {
        C08034(String str, String str2) {
            super(str2, null);
        }

        public /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
            return zze(sharedPreferences);
        }

        public zzkf<String> zzcZ() {
            return zzkf.zzs(getKey(), (String) zzcY());
        }

        public String zze(SharedPreferences sharedPreferences) {
            return sharedPreferences.getString(getKey(), (String) zzcY());
        }
    }

    private zzbv(String str, T t) {
        this.zztw = str;
        this.zztx = t;
        zzo.zzbD().zza((zzbs) this);
    }

    public static zzbv<String> zzO(String str) {
        zzbv zzc = zzc(str, (String) null);
        zzo.zzbD().zza(zzc);
        return zzc;
    }

    public static zzbv<String> zzP(String str) {
        zzbv<String> zzc = zzc(str, (String) null);
        zzo.zzbD().zzb(zzc);
        return zzc;
    }

    public static zzbv<Integer> zza(String str, int i) {
        return new C08012(str, Integer.valueOf(i));
    }

    public static zzbv<Boolean> zza(String str, Boolean bool) {
        return new C08001(str, bool);
    }

    public static zzbv<Long> zzb(String str, long j) {
        return new C08023(str, Long.valueOf(j));
    }

    public static zzbv<String> zzc(String str, String str2) {
        return new C08034(str, str2);
    }

    public T get() {
        return zzo.zzbE().zzc(this);
    }

    public String getKey() {
        return this.zztw;
    }

    protected abstract T zza(SharedPreferences sharedPreferences);

    public T zzcY() {
        return this.zztx;
    }

    public abstract zzkf<T> zzcZ();
}
