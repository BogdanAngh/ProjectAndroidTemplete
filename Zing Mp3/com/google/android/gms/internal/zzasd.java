package com.google.android.gms.internal;

import java.io.IOException;

public final class zzasd {
    public static final int[] btR;
    public static final long[] btS;
    public static final float[] btT;
    public static final double[] btU;
    public static final boolean[] btV;
    public static final String[] btW;
    public static final byte[][] btX;
    public static final byte[] btY;

    static {
        btR = new int[0];
        btS = new long[0];
        btT = new float[0];
        btU = new double[0];
        btV = new boolean[0];
        btW = new String[0];
        btX = new byte[0][];
        btY = new byte[0];
    }

    static int zzahk(int i) {
        return i & 7;
    }

    public static int zzahl(int i) {
        return i >>> 3;
    }

    public static int zzak(int i, int i2) {
        return (i << 3) | i2;
    }

    public static boolean zzb(zzars com_google_android_gms_internal_zzars, int i) throws IOException {
        return com_google_android_gms_internal_zzars.zzagr(i);
    }

    public static final int zzc(zzars com_google_android_gms_internal_zzars, int i) throws IOException {
        int i2 = 1;
        int position = com_google_android_gms_internal_zzars.getPosition();
        com_google_android_gms_internal_zzars.zzagr(i);
        while (com_google_android_gms_internal_zzars.bU() == i) {
            com_google_android_gms_internal_zzars.zzagr(i);
            i2++;
        }
        com_google_android_gms_internal_zzars.zzagv(position);
        return i2;
    }
}
