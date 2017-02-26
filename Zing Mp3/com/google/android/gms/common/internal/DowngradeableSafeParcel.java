package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    private static final Object DQ;
    private static ClassLoader DR;
    private static Integer DS;
    private boolean DT;

    static {
        DQ = new Object();
        DR = null;
        DS = null;
    }

    public DowngradeableSafeParcel() {
        this.DT = false;
    }

    protected static ClassLoader zzavy() {
        synchronized (DQ) {
        }
        return null;
    }

    protected static Integer zzavz() {
        synchronized (DQ) {
        }
        return null;
    }

    private static boolean zzd(Class<?> cls) {
        boolean z = false;
        try {
            z = SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e2) {
        }
        return z;
    }

    protected static boolean zzhu(String str) {
        ClassLoader zzavy = zzavy();
        if (zzavy == null) {
            return true;
        }
        try {
            return zzd(zzavy.loadClass(str));
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean zzawa() {
        return false;
    }
}
