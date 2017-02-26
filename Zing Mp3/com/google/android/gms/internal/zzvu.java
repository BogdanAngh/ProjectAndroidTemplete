package com.google.android.gms.internal;

public final class zzvu {
    private static zzvu WD;
    private final zzvr WE;
    private final zzvs WF;

    static {
        zza(new zzvu());
    }

    private zzvu() {
        this.WE = new zzvr();
        this.WF = new zzvs();
    }

    protected static void zza(zzvu com_google_android_gms_internal_zzvu) {
        synchronized (zzvu.class) {
            WD = com_google_android_gms_internal_zzvu;
        }
    }

    private static zzvu zzbhd() {
        zzvu com_google_android_gms_internal_zzvu;
        synchronized (zzvu.class) {
            com_google_android_gms_internal_zzvu = WD;
        }
        return com_google_android_gms_internal_zzvu;
    }

    public static zzvr zzbhe() {
        return zzbhd().WE;
    }

    public static zzvs zzbhf() {
        return zzbhd().WF;
    }
}
