package com.google.android.gms.tagmanager;

class zzdl extends Number implements Comparable<zzdl> {
    private double aHO;
    private long aHP;
    private boolean aHQ;

    private zzdl(double d) {
        this.aHO = d;
        this.aHQ = false;
    }

    private zzdl(long j) {
        this.aHP = j;
        this.aHQ = true;
    }

    public static zzdl zza(Double d) {
        return new zzdl(d.doubleValue());
    }

    public static zzdl zzbv(long j) {
        return new zzdl(j);
    }

    public static zzdl zzpw(String str) throws NumberFormatException {
        try {
            return new zzdl(Long.parseLong(str));
        } catch (NumberFormatException e) {
            try {
                return new zzdl(Double.parseDouble(str));
            } catch (NumberFormatException e2) {
                throw new NumberFormatException(String.valueOf(str).concat(" is not a valid TypedNumber"));
            }
        }
    }

    public byte byteValue() {
        return (byte) ((int) longValue());
    }

    public /* synthetic */ int compareTo(Object obj) {
        return zza((zzdl) obj);
    }

    public double doubleValue() {
        return zzchc() ? (double) this.aHP : this.aHO;
    }

    public boolean equals(Object obj) {
        return (obj instanceof zzdl) && zza((zzdl) obj) == 0;
    }

    public float floatValue() {
        return (float) doubleValue();
    }

    public int hashCode() {
        return new Long(longValue()).hashCode();
    }

    public int intValue() {
        return zzche();
    }

    public long longValue() {
        return zzchd();
    }

    public short shortValue() {
        return zzchf();
    }

    public String toString() {
        return zzchc() ? Long.toString(this.aHP) : Double.toString(this.aHO);
    }

    public int zza(zzdl com_google_android_gms_tagmanager_zzdl) {
        return (zzchc() && com_google_android_gms_tagmanager_zzdl.zzchc()) ? new Long(this.aHP).compareTo(Long.valueOf(com_google_android_gms_tagmanager_zzdl.aHP)) : Double.compare(doubleValue(), com_google_android_gms_tagmanager_zzdl.doubleValue());
    }

    public boolean zzchb() {
        return !zzchc();
    }

    public boolean zzchc() {
        return this.aHQ;
    }

    public long zzchd() {
        return zzchc() ? this.aHP : (long) this.aHO;
    }

    public int zzche() {
        return (int) longValue();
    }

    public short zzchf() {
        return (short) ((int) longValue());
    }
}
