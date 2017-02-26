package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzapt implements zzapl, Cloneable {
    public static final zzapt boW;
    private double boX;
    private int boY;
    private boolean boZ;
    private List<zzaoo> bpa;
    private List<zzaoo> bpb;

    /* renamed from: com.google.android.gms.internal.zzapt.1 */
    class C12251 extends zzapk<T> {
        private zzapk<T> bol;
        final /* synthetic */ boolean bpc;
        final /* synthetic */ boolean bpd;
        final /* synthetic */ zzaos bpe;
        final /* synthetic */ zzaqo bpf;
        final /* synthetic */ zzapt bpg;

        C12251(zzapt com_google_android_gms_internal_zzapt, boolean z, boolean z2, zzaos com_google_android_gms_internal_zzaos, zzaqo com_google_android_gms_internal_zzaqo) {
            this.bpg = com_google_android_gms_internal_zzapt;
            this.bpc = z;
            this.bpd = z2;
            this.bpe = com_google_android_gms_internal_zzaos;
            this.bpf = com_google_android_gms_internal_zzaqo;
        }

        private zzapk<T> bg() {
            zzapk<T> com_google_android_gms_internal_zzapk_T = this.bol;
            if (com_google_android_gms_internal_zzapk_T != null) {
                return com_google_android_gms_internal_zzapk_T;
            }
            com_google_android_gms_internal_zzapk_T = this.bpe.zza(this.bpg, this.bpf);
            this.bol = com_google_android_gms_internal_zzapk_T;
            return com_google_android_gms_internal_zzapk_T;
        }

        public void zza(zzaqr com_google_android_gms_internal_zzaqr, T t) throws IOException {
            if (this.bpd) {
                com_google_android_gms_internal_zzaqr.bA();
            } else {
                bg().zza(com_google_android_gms_internal_zzaqr, t);
            }
        }

        public T zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
            if (!this.bpc) {
                return bg().zzb(com_google_android_gms_internal_zzaqp);
            }
            com_google_android_gms_internal_zzaqp.skipValue();
            return null;
        }
    }

    static {
        boW = new zzapt();
    }

    public zzapt() {
        this.boX = -1.0d;
        this.boY = 136;
        this.boZ = true;
        this.bpa = Collections.emptyList();
        this.bpb = Collections.emptyList();
    }

    private boolean zza(zzapo com_google_android_gms_internal_zzapo) {
        return com_google_android_gms_internal_zzapo == null || com_google_android_gms_internal_zzapo.bi() <= this.boX;
    }

    private boolean zza(zzapo com_google_android_gms_internal_zzapo, zzapp com_google_android_gms_internal_zzapp) {
        return zza(com_google_android_gms_internal_zzapo) && zza(com_google_android_gms_internal_zzapp);
    }

    private boolean zza(zzapp com_google_android_gms_internal_zzapp) {
        return com_google_android_gms_internal_zzapp == null || com_google_android_gms_internal_zzapp.bi() > this.boX;
    }

    private boolean zzm(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean zzn(Class<?> cls) {
        return cls.isMemberClass() && !zzo(cls);
    }

    private boolean zzo(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    protected zzapt bk() {
        try {
            return (zzapt) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    protected /* synthetic */ Object clone() throws CloneNotSupportedException {
        return bk();
    }

    public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
        Class bB = com_google_android_gms_internal_zzaqo_T.bB();
        boolean zza = zza(bB, true);
        boolean zza2 = zza(bB, false);
        return (zza || zza2) ? new C12251(this, zza2, zza, com_google_android_gms_internal_zzaos, com_google_android_gms_internal_zzaqo_T) : null;
    }

    public zzapt zza(zzaoo com_google_android_gms_internal_zzaoo, boolean z, boolean z2) {
        zzapt bk = bk();
        if (z) {
            bk.bpa = new ArrayList(this.bpa);
            bk.bpa.add(com_google_android_gms_internal_zzaoo);
        }
        if (z2) {
            bk.bpb = new ArrayList(this.bpb);
            bk.bpb.add(com_google_android_gms_internal_zzaoo);
        }
        return bk;
    }

    public boolean zza(Class<?> cls, boolean z) {
        if (this.boX != -1.0d && !zza((zzapo) cls.getAnnotation(zzapo.class), (zzapp) cls.getAnnotation(zzapp.class))) {
            return true;
        }
        if (!this.boZ && zzn(cls)) {
            return true;
        }
        if (zzm(cls)) {
            return true;
        }
        for (zzaoo zzh : z ? this.bpa : this.bpb) {
            if (zzh.zzh(cls)) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(Field field, boolean z) {
        if ((this.boY & field.getModifiers()) != 0) {
            return true;
        }
        if (this.boX != -1.0d && !zza((zzapo) field.getAnnotation(zzapo.class), (zzapp) field.getAnnotation(zzapp.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        if (!this.boZ && zzn(field.getType())) {
            return true;
        }
        if (zzm(field.getType())) {
            return true;
        }
        List<zzaoo> list = z ? this.bpa : this.bpb;
        if (!list.isEmpty()) {
            zzaop com_google_android_gms_internal_zzaop = new zzaop(field);
            for (zzaoo zza : list) {
                if (zza.zza(com_google_android_gms_internal_zzaop)) {
                    return true;
                }
            }
        }
        return false;
    }

    public zzapt zzg(int... iArr) {
        int i = 0;
        zzapt bk = bk();
        bk.boY = 0;
        int length = iArr.length;
        while (i < length) {
            bk.boY = iArr[i] | bk.boY;
            i++;
        }
        return bk;
    }
}
