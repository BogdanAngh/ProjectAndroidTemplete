package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzsi<T> {
    private static zza BL;
    private static int BM;
    private static String READ_PERMISSION;
    private static final Object zzaox;
    private T BN;
    protected final String zzbcn;
    protected final T zzbco;

    /* renamed from: com.google.android.gms.internal.zzsi.1 */
    class C14971 extends zzsi<Boolean> {
        C14971(String str, Boolean bool) {
            super(str, bool);
        }

        protected /* synthetic */ Object zzhi(String str) {
            return zzhj(str);
        }

        protected Boolean zzhj(String str) {
            return null.zza(this.zzbcn, (Boolean) this.zzbco);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzsi.2 */
    class C14982 extends zzsi<Long> {
        C14982(String str, Long l) {
            super(str, l);
        }

        protected /* synthetic */ Object zzhi(String str) {
            return zzhk(str);
        }

        protected Long zzhk(String str) {
            return null.getLong(this.zzbcn, (Long) this.zzbco);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzsi.3 */
    class C14993 extends zzsi<Integer> {
        C14993(String str, Integer num) {
            super(str, num);
        }

        protected /* synthetic */ Object zzhi(String str) {
            return zzhl(str);
        }

        protected Integer zzhl(String str) {
            return null.zzb(this.zzbcn, (Integer) this.zzbco);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzsi.4 */
    class C15004 extends zzsi<Float> {
        C15004(String str, Float f) {
            super(str, f);
        }

        protected /* synthetic */ Object zzhi(String str) {
            return zzhm(str);
        }

        protected Float zzhm(String str) {
            return null.zzb(this.zzbcn, (Float) this.zzbco);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzsi.5 */
    class C15015 extends zzsi<String> {
        C15015(String str, String str2) {
            super(str, str2);
        }

        protected /* synthetic */ Object zzhi(String str) {
            return zzhn(str);
        }

        protected String zzhn(String str) {
            return null.getString(this.zzbcn, (String) this.zzbco);
        }
    }

    private interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zza(String str, Boolean bool);

        Float zzb(String str, Float f);

        Integer zzb(String str, Integer num);
    }

    static {
        zzaox = new Object();
        BL = null;
        BM = 0;
        READ_PERMISSION = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    }

    protected zzsi(String str, T t) {
        this.BN = null;
        this.zzbcn = str;
        this.zzbco = t;
    }

    public static zzsi<Float> zza(String str, Float f) {
        return new C15004(str, f);
    }

    public static zzsi<Integer> zza(String str, Integer num) {
        return new C14993(str, num);
    }

    public static zzsi<Long> zza(String str, Long l) {
        return new C14982(str, l);
    }

    public static zzsi<String> zzaa(String str, String str2) {
        return new C15015(str, str2);
    }

    public static zzsi<Boolean> zzk(String str, boolean z) {
        return new C14971(str, Boolean.valueOf(z));
    }

    public final T get() {
        T zzhi;
        long clearCallingIdentity;
        try {
            zzhi = zzhi(this.zzbcn);
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            zzhi = zzhi(this.zzbcn);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return zzhi;
    }

    protected abstract T zzhi(String str);
}
