package com.google.android.gms.internal;

import com.google.android.gms.internal.zzad.zza;
import com.mp3download.zingmp3.C1569R;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

public class zzbi extends zzby {
    private static final Object zzaix;
    private static volatile zzam zzaiy;
    private boolean zzaiz;
    private zza zzaja;

    static {
        zzaiy = null;
        zzaix = new Object();
    }

    public zzbi(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zzaf.zza com_google_android_gms_internal_zzaf_zza, int i, int i2, boolean z, zza com_google_android_gms_internal_zzad_zza) {
        super(com_google_android_gms_internal_zzbc, str, str2, com_google_android_gms_internal_zzaf_zza, i, i2);
        this.zzaiz = false;
        this.zzaja = null;
        this.zzaiz = z;
        this.zzaja = com_google_android_gms_internal_zzad_zza;
    }

    public static Boolean zza(zza com_google_android_gms_internal_zzad_zza) {
        boolean z = false;
        if (!zzbe.zzs(zzb(com_google_android_gms_internal_zzad_zza))) {
            return Boolean.valueOf(false);
        }
        if (!(com_google_android_gms_internal_zzad_zza == null || com_google_android_gms_internal_zzad_zza.zzck == null || com_google_android_gms_internal_zzad_zza.zzck.zzcm.intValue() != 3)) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    public static String zzb(zza com_google_android_gms_internal_zzad_zza) {
        return (com_google_android_gms_internal_zzad_zza == null || com_google_android_gms_internal_zzad_zza.zzcl == null || zzbe.zzs(com_google_android_gms_internal_zzad_zza.zzcl.zzcn)) ? null : com_google_android_gms_internal_zzad_zza.zzcl.zzcn;
    }

    private boolean zzdi() {
        return zzaiy == null || zzbe.zzs(zzaiy.zzcn) || zzaiy.zzcn.equals("E");
    }

    private int zzdj() {
        return !zzbe.zzs(zzb(this.zzaja)) ? 4 : (zza(this.zzaja).booleanValue() && zzdk()) ? 3 : 2;
    }

    private boolean zzdk() {
        return this.zzagd.zzcu() && ((Boolean) zzdr.zzbil.get()).booleanValue() && ((Boolean) zzdr.zzbim.get()).booleanValue() && ((Boolean) zzdr.zzbij.get()).booleanValue();
    }

    private String zzdl() {
        try {
            if (this.zzagd.zzcw() != null) {
                this.zzagd.zzcw().get();
            }
            zzaf.zza zzcv = this.zzagd.zzcv();
            if (!(zzcv == null || zzcv.zzcn == null)) {
                return zzcv.zzcn;
            }
        } catch (InterruptedException e) {
        } catch (ExecutionException e2) {
        }
        return null;
    }

    private void zze(int i) throws IllegalAccessException, InvocationTargetException {
        boolean z = true;
        Method method = this.zzajk;
        Object[] objArr = new Object[3];
        objArr[0] = this.zzagd.getContext();
        objArr[1] = Boolean.valueOf(this.zzaiz);
        if (i != 2) {
            z = false;
        }
        objArr[2] = Boolean.valueOf(z);
        zzaiy = new zzam((String) method.invoke(null, objArr));
        if (zzbe.zzs(zzaiy.zzcn) || zzaiy.zzcn.equals("E")) {
            switch (i) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    String zzdl = zzdl();
                    if (!zzbe.zzs(zzdl)) {
                        zzaiy.zzcn = zzdl;
                    }
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    zzaiy.zzcn = this.zzaja.zzcl.zzcn;
                default:
            }
        }
    }

    protected void zzdh() throws IllegalAccessException, InvocationTargetException {
        if (zzdi()) {
            synchronized (zzaix) {
                int zzdj = zzdj();
                if (zzdj == 3) {
                    this.zzagd.zzcz();
                }
                zze(zzdj);
            }
        }
        synchronized (this.zzajb) {
            if (zzaiy != null) {
                this.zzajb.zzcn = zzaiy.zzcn;
                this.zzajb.zzea = Long.valueOf(zzaiy.zzyi);
                this.zzajb.zzcp = zzaiy.zzcp;
                if (this.zzaiz) {
                    this.zzajb.zzcq = zzaiy.zzcq;
                    this.zzajb.zzcr = zzaiy.zzcr;
                }
            }
        }
    }
}
