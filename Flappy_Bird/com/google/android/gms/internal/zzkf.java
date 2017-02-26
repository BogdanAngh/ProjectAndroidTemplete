package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzkf<T> {
    private static zza zzYj;
    private static int zzYk;
    private static String zzYl;
    private static final Object zzoW;
    private T zzLS;
    protected final String zztw;
    protected final T zztx;

    private interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zzb(String str, Boolean bool);

        Float zzb(String str, Float f);

        Integer zzb(String str, Integer num);
    }

    /* renamed from: com.google.android.gms.internal.zzkf.1 */
    static class C04821 extends zzkf<Boolean> {
        C04821(String str, Boolean bool) {
            super(str, bool);
        }

        protected /* synthetic */ Object zzbP(String str) {
            return zzbQ(str);
        }

        protected Boolean zzbQ(String str) {
            return zzkf.zzYj.zzb(this.zztw, (Boolean) this.zztx);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkf.2 */
    static class C04832 extends zzkf<Long> {
        C04832(String str, Long l) {
            super(str, l);
        }

        protected /* synthetic */ Object zzbP(String str) {
            return zzbR(str);
        }

        protected Long zzbR(String str) {
            return zzkf.zzYj.getLong(this.zztw, (Long) this.zztx);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkf.3 */
    static class C04843 extends zzkf<Integer> {
        C04843(String str, Integer num) {
            super(str, num);
        }

        protected /* synthetic */ Object zzbP(String str) {
            return zzbS(str);
        }

        protected Integer zzbS(String str) {
            return zzkf.zzYj.zzb(this.zztw, (Integer) this.zztx);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkf.4 */
    static class C04854 extends zzkf<Float> {
        C04854(String str, Float f) {
            super(str, f);
        }

        protected /* synthetic */ Object zzbP(String str) {
            return zzbT(str);
        }

        protected Float zzbT(String str) {
            return zzkf.zzYj.zzb(this.zztw, (Float) this.zztx);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkf.5 */
    static class C04865 extends zzkf<String> {
        C04865(String str, String str2) {
            super(str, str2);
        }

        protected /* synthetic */ Object zzbP(String str) {
            return zzbU(str);
        }

        protected String zzbU(String str) {
            return zzkf.zzYj.getString(this.zztw, (String) this.zztx);
        }
    }

    static {
        zzoW = new Object();
        zzYj = null;
        zzYk = 0;
        zzYl = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    }

    protected zzkf(String str, T t) {
        this.zzLS = null;
        this.zztw = str;
        this.zztx = t;
    }

    public static boolean isInitialized() {
        return zzYj != null;
    }

    public static zzkf<Float> zza(String str, Float f) {
        return new C04854(str, f);
    }

    public static zzkf<Integer> zza(String str, Integer num) {
        return new C04843(str, num);
    }

    public static zzkf<Long> zza(String str, Long l) {
        return new C04832(str, l);
    }

    public static zzkf<Boolean> zzg(String str, boolean z) {
        return new C04821(str, Boolean.valueOf(z));
    }

    public static int zzmY() {
        return zzYk;
    }

    public static zzkf<String> zzs(String str, String str2) {
        return new C04865(str, str2);
    }

    public final T get() {
        return this.zzLS != null ? this.zzLS : zzbP(this.zztw);
    }

    protected abstract T zzbP(String str);

    public final T zzmZ() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            T t = get();
            return t;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
