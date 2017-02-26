package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzn extends zzam {
    private static final String ID;
    private static final String VALUE;

    static {
        ID = zzag.CONSTANT.toString();
        VALUE = zzah.VALUE.toString();
    }

    public zzn() {
        super(ID, VALUE);
    }

    public static String zzcdw() {
        return ID;
    }

    public static String zzcdx() {
        return VALUE;
    }

    public zza zzay(Map<String, zza> map) {
        return (zza) map.get(VALUE);
    }

    public boolean zzcdu() {
        return true;
    }
}
