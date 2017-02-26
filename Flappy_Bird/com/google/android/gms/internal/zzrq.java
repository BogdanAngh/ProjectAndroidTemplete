package com.google.android.gms.internal;

import java.io.IOException;

public final class zzrq {
    public static final int[] zzaWh;
    public static final long[] zzaWi;
    public static final float[] zzaWj;
    public static final double[] zzaWk;
    public static final boolean[] zzaWl;
    public static final String[] zzaWm;
    public static final byte[][] zzaWn;
    public static final byte[] zzaWo;

    static {
        zzaWh = new int[0];
        zzaWi = new long[0];
        zzaWj = new float[0];
        zzaWk = new double[0];
        zzaWl = new boolean[0];
        zzaWm = new String[0];
        zzaWn = new byte[0][];
        zzaWo = new byte[0];
    }

    static int zzD(int i, int i2) {
        return (i << 3) | i2;
    }

    public static final int zzb(zzrf com_google_android_gms_internal_zzrf, int i) throws IOException {
        int i2 = 1;
        int position = com_google_android_gms_internal_zzrf.getPosition();
        com_google_android_gms_internal_zzrf.zzkA(i);
        while (com_google_android_gms_internal_zzrf.zzBr() == i) {
            com_google_android_gms_internal_zzrf.zzkA(i);
            i2++;
        }
        com_google_android_gms_internal_zzrf.zzkE(position);
        return i2;
    }

    static int zzkU(int i) {
        return i & 7;
    }

    public static int zzkV(int i) {
        return i >>> 3;
    }
}
